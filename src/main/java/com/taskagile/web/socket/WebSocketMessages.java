package com.taskagile.web.socket;

import com.taskagile.utils.JsonUtils;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;
import java.util.Map;

class WebSocketMessages {

  static TextMessage reply(String reply) {
    Map<String, String> messageObject = new HashMap<>();
    messageObject.put("type", "reply");
    messageObject.put("message", reply);
    return new TextMessage(JsonUtils.toJson(messageObject));
  }

  static TextMessage error(String error) {
    Map<String, String> messageObject = new HashMap<>();
    messageObject.put("error", "error");
    messageObject.put("message", error);
    return new TextMessage(JsonUtils.toJson(messageObject));
  }

  static TextMessage failure(String failure) {
    Map<String, String> messageObject = new HashMap<>();
    messageObject.put("type", "failure");
    messageObject.put("message", failure);
    return new TextMessage(JsonUtils.toJson(messageObject));
  }

  static TextMessage channelMessage(String channel, String payload) {
    Map<String, String> messageObject = new HashMap<>();
    messageObject.put("channel", channel);
    messageObject.put("payload", payload);
    return new TextMessage(JsonUtils.toJson(messageObject));
  }
}
