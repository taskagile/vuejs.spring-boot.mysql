package com.taskagile.web.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ChannelHandlerResolver {

  private static final Logger log = LoggerFactory.getLogger(ChannelHandlerResolver.class);

  private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
  // The key is the channel ant-like path pattern, value is the corresponding invoker
  private final Map<String, ChannelHandlerInvoker> invokers = new HashMap<>();

  private ApplicationContext applicationContext;

  public ChannelHandlerResolver(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
    this.bootstrap();
  }

  public ChannelHandlerInvoker findInvoker(IncomingMessage incomingMessage) {
    ChannelHandlerInvoker invoker = null;
    Set<String> pathPatterns = invokers.keySet();
    for (String pathPattern : pathPatterns) {
      if (antPathMatcher.match(pathPattern, incomingMessage.getChannel())) {
        invoker = invokers.get(pathPattern);
      }
    }
    if (invoker == null) {
      return null;
    }
    return invoker.supports(incomingMessage.getAction()) ? invoker : null;
  }

  private void bootstrap() {
    log.info("Bootstrapping channel handler resolver");

    Map<String, Object> handlers = applicationContext.getBeansWithAnnotation(ChannelHandler.class);
    for (String handlerName : handlers.keySet()) {
      Object handler = handlers.get(handlerName);
      Class<?> handlerClass = handler.getClass();

      ChannelHandler handlerAnnotation = handlerClass.getAnnotation(ChannelHandler.class);
      String channelPattern = ChannelHandlers.getPattern(handlerAnnotation);
      if (invokers.containsKey(channelPattern)) {
        throw new IllegalStateException("Duplicated handlers found for chanel pattern `" + channelPattern + "`.");
      }
      invokers.put(channelPattern, new ChannelHandlerInvoker(handler));
      log.debug("Mapped channel `{}` to channel handler `{}`", channelPattern, handlerClass.getName());
    }
  }
}
