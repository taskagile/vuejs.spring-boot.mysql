package com.taskagile.web.socket;

class WebSocketMessage {

  private String type;
  private String message;

  static Object reply(String reply) {
    WebSocketMessage message = new WebSocketMessage();
    message.type = "reply";
    message.message = reply;
    return message;
  }

  static Object failure(String failure) {
    WebSocketMessage message = new WebSocketMessage();
    message.type = "failure";
    message.message = failure;
    return message;
  }

  public String getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }
}
