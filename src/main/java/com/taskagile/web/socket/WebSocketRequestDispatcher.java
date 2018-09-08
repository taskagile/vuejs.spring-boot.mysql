package com.taskagile.web.socket;

import com.taskagile.domain.common.security.TokenManager;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.utils.JsonUtils;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketRequestDispatcher extends TextWebSocketHandler {

  private static final Logger log = LoggerFactory.getLogger(WebSocketRequestDispatcher.class);

  private TokenManager tokenManager;
  private ChannelHandlerResolver channelHandlerResolver;

  public WebSocketRequestDispatcher(TokenManager tokenManager,
                                    ChannelHandlerResolver channelHandlerResolver) {
    this.tokenManager = tokenManager;
    this.channelHandlerResolver = channelHandlerResolver;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession webSocketSession) {
    log.debug("WebSocket connection established");
    RealTimeSession session = new RealTimeSession(webSocketSession);
    String token = session.getToken();

    try {
      UserId userId = tokenManager.verifyJwt(token);
      session.setUserId(userId);
      session.reply("authenticated");
    } catch (JwtException exception) {
      log.debug("Invalid JWT token value: {}", token);
      session.fail("authentication failed");
    }
  }

  @Override
  protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) {
    RealTimeSession session = new RealTimeSession(webSocketSession);
    log.debug("RealTimeSession[{}] Received message `{}`", session.id(), message.getPayload());

    IncomingMessage incomingMessage = JsonUtils.toObject(message.getPayload(), IncomingMessage.class);
    if (incomingMessage == null) {
      session.error("Illegal format of incoming message: " + message.getPayload());
      return;
    }

    ChannelHandlerInvoker invoker = channelHandlerResolver.findInvoker(incomingMessage);
    if (invoker == null) {
      String errorMessage = "No handler found for action `" + incomingMessage.getAction() +
        "` at channel `" + incomingMessage.getChannel() + "`";
      session.error(errorMessage);
      log.error("RealTimeSession[{}] {}", session.id(), errorMessage);
      return;
    }

    invoker.handle(incomingMessage, session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) {
    RealTimeSession session = new RealTimeSession(webSocketSession);
    SubscriptionHub.unsubscribeAll(session);
    log.debug("RealTimeSession[{}] Unsubscribed all channels after disconnecting", session.id());
  }
}
