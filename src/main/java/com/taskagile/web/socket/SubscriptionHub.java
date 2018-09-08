package com.taskagile.web.socket;

import com.taskagile.domain.model.user.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SubscriptionHub {

  private static final Logger log = LoggerFactory.getLogger(SubscriptionHub.class);
  // Key is the channel, value is a set of web socket sessions that have subscribed to it
  private static final Map<String, Set<WebSocketSession>> subscriptions = new HashMap<>();
  // Keep the the channels that a client subscribed
  // The key is the session id, value is a set of subscribed channels
  private static final Map<String, Set<String>> subscribedChannels = new HashMap<>();

  public static void subscribe(RealTimeSession session, String channel) {
    Assert.hasText(channel, "Parameter `channel` must not be null");

    Set<WebSocketSession> subscribers = subscriptions.computeIfAbsent(channel, k -> new HashSet<>());
    subscribers.add(session.wrapped());

    UserId userId = session.getUserId();
    log.debug("RealTimeSession[{}] Subscribed user[id={}] to channel `{}`", session.id(), userId, channel);

    // Add the channel to client's subscribed list
    Set<String> channels = subscribedChannels.computeIfAbsent(session.id(), k -> new HashSet<>());
    channels.add(channel);
  }

  public static void unsubscribe(RealTimeSession session, String channel) {
    Assert.hasText(channel, "Parameter `channel` must not be empty");
    Assert.notNull(session, "Parameter `session` must not be null");

    Set<WebSocketSession> subscribers = subscriptions.get(channel);
    if (subscribers != null) {
      subscribers.remove(session.wrapped());
      UserId userId = session.getUserId();
      log.debug("RealTimeSession[{}] Unsubscribed user[id={}] from channel `{}`", session.id(), userId, channel);
    }

    // Remove the channel from the client's subscribed channels
    Set<String> channels = subscribedChannels.get(session.id());
    if (channels != null) {
      channels.remove(channel);
    }
  }

  public static void unsubscribeAll(RealTimeSession session) {
    Set<String> channels = subscribedChannels.get(session.id());
    if (channels == null) {
      log.debug("RealTimeSession[{}] No channels to unsubscribe.", session.id());
      return;
    }

    for (String channel: channels) {
      unsubscribe(session, channel);
    }

    // Remove the subscribed channels
    subscribedChannels.remove(session.id());
  }

  public static void send(String channel, String update) {
    Assert.hasText(channel, "Parameter `channel` must not be empty");
    Assert.hasText(update, "Parameter `update` must not be null");

    Set<WebSocketSession> subscribers = subscriptions.get(channel);
    if (subscribers == null || subscriptions.isEmpty()) {
      log.debug("No subscribers of channel `{}` found", channel);
      return;
    }

    for (WebSocketSession subscriber: subscribers) {
      sendTo(subscriber, channel, update);
    }
  }

  private static void sendTo(WebSocketSession subscriber, String channel, String update) {
    try {
      subscriber.sendMessage(WebSocketMessages.channelMessage(channel, update));
      log.debug("RealTimeSession[{}] Send message `{}` to subscriber at channel `{}`",
        subscriber.getId(), update, channel);
    } catch (IOException e) {
      log.error("Failed to send message to subscriber `" + subscriber.getId() +
        "` of channel `" + channel + "`. Message: " + update, e);
    }
  }
}
