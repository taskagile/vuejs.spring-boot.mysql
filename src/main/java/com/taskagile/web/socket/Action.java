package com.taskagile.web.socket;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {

  /**
   * The action pattern. It needs to be an exact match.
   * <p>For example, "subscribe"
   */
  String value() default "";
}
