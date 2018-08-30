package com.taskagile.domain.common.security;

import com.taskagile.domain.model.user.SimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

  private final static Logger log = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException {
    if (log.isDebugEnabled()) {
      log.debug("Access to `" + request.getRequestURI() + "` denied.");
    }

    if (request.getRequestURI().startsWith("/api/")) {
      if (request.getUserPrincipal() instanceof SimpleUser) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
      } else {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      }
    } else {
      response.sendRedirect("/login");
    }
  }
}
