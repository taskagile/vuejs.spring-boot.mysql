package com.taskagile.utils;

import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {

  private RequestUtils() {
  }

  public static IpAddress getIpAddress(HttpServletRequest request) {
    Assert.notNull(request, "Parameter `request` must not be null");

    String remoteAddress = request.getRemoteAddr();
    String x;
    if ((x = request.getHeader("X-FORWARDED-FOR")) != null) {
      remoteAddress = x;
      int idx = remoteAddress.indexOf(',');
      if (idx > -1) {
        remoteAddress = remoteAddress.substring(0, idx);
      }
    }
    return new IpAddress(remoteAddress);
  }
}
