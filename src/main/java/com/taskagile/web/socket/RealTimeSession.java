package com.taskagile.web.socket;

import com.taskagile.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

/**
 * A wrapper over {@link WebSocketSession} to add convenient methods
 */
public class RealTimeSession {

  private static final Logger log = LoggerFactory.getLogger(RealTimeSession.class);

  private WebSocketSession session;

  RealTimeSession(WebSocketSession session) {
    this.session = session;
  }

  void addAttribute(String key, Object value) {
    session.getAttributes().put(key, value);
  }

  @SuppressWarnings("unchecked")
  public <T> T getAttribute(String key) {
    Object value = session.getAttributes().get(key);
    if (value == null) {
      return null;
    }
    return (T) value;
  }

  public String getToken() {
    URI uri = session.getUri();
    UriComponents uriComponents = UriComponentsBuilder.fromUri(uri).build();
    return uriComponents.getQueryParams().getFirst("token");
  }

  public void fail(String failure) {
    sendMessage(WebSocketMessage.failure(failure));
  }

  public void reply(String reply) {
    sendMessage(WebSocketMessage.reply(reply));
  }

  private void sendMessage(Object message) {
    try {
      String textMessage = JsonUtils.toJson(message);
      session.sendMessage(new TextMessage(textMessage));
    } catch (IOException e) {
      log.error("Failed to send message through web socket session", e);
    }
  }
}
