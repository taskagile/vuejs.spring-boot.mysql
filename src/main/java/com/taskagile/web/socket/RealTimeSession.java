package com.taskagile.web.socket;

import com.taskagile.domain.model.user.UserId;
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
  private static final String KEY_USER_ID = "KEY_USER_ID";

  private WebSocketSession session;

  RealTimeSession(WebSocketSession session) {
    this.session = session;
  }

  public String id() {
    return session.getId();
  }

  public WebSocketSession wrapped() {
    return session;
  }

  public void setUserId(UserId userId) {
    addAttribute(KEY_USER_ID, userId);
  }

  public UserId getUserId() {
    return getAttribute(KEY_USER_ID);
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

  public void error(String error) {
    sendMessage(WebSocketMessages.error(error));
  }

  public void fail(String failure) {
    sendMessage(WebSocketMessages.failure(failure));
  }

  public void reply(String reply) {
    sendMessage(WebSocketMessages.reply(reply));
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
