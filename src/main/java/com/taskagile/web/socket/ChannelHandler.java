package com.taskagile.web.socket;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ChannelHandler {

  /**
   * Channel patter, alias of value()
   */
  String pattern() default "";

  /**
   * The channel pattern that the handler will be mapped to by {@link WebSocketRequestDispatcher}
   * using Spring's {@link org.springframework.util.AntPathMatcher}
   */
  String value() default "";

}
