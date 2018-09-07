package com.taskagile.web.socket;

import com.taskagile.domain.common.security.TokenManager;
import com.taskagile.domain.model.user.UserId;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketRequestDispatcher extends TextWebSocketHandler {

  private static final Logger log = LoggerFactory.getLogger(WebSocketRequestDispatcher.class);

  private TokenManager tokenManager;

  public WebSocketRequestDispatcher(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession webSocketSession) {
    log.debug("WebSocket connection established");
    RealTimeSession session = new RealTimeSession(webSocketSession);
    String token = session.getToken();

    try {
      UserId userId = tokenManager.verifyJwt(token);
      session.addAttribute("userId", userId);
      session.reply("authenticated");
    } catch (JwtException exception) {
      log.debug("Invalid JWT token value: {}", token);
      session.fail("authentication failed");
    }
  }
}
