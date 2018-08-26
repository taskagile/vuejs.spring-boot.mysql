package com.taskagile.web.results;

import org.springframework.util.Assert;

import java.util.HashMap;

public class ApiResult extends HashMap<String, Object> {

  private static final long serialVersionUID = 877825499039674411L;

  private static final String MESSAGE_KEY = "message";
  private static final String ERROR_CODE_KEY = "errorReferenceCode";

  public static ApiResult blank() {
    return new ApiResult();
  }

  public static ApiResult message(String message) {
    Assert.hasText(message, "Parameter `message` must not be blank");

    ApiResult apiResult = new ApiResult();
    apiResult.put("message", message);
    return apiResult;
  }

  public static ApiResult error(String message, String errorReferenceCode) {
    Assert.hasText(message, "Parameter `message` must not be blank");
    Assert.hasText(errorReferenceCode, "Parameter `errorReferenceCode` must not be blank");

    ApiResult apiResult = new ApiResult();
    apiResult.put(MESSAGE_KEY, message);
    apiResult.put(ERROR_CODE_KEY, errorReferenceCode);
    return apiResult;
  }

  public ApiResult add(String key, Object value) {
    Assert.hasText(key, "Parameter `key` must not be blank");
    Assert.notNull(value, "Parameter `value` must not be null");

    this.put(key, value);
    return this;
  }
}
