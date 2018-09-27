package com.taskagile.domain.model.activity;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class CardActivity {

  private UserId userId;
  private CardId cardId;
  private BoardId boardId;
  private ActivityType type;
  private Map<String, Object> detail = new HashMap<>();

  public static CardActivity create(UserId userId, Card card, ActivityType type) {
    CardActivity activity = new CardActivity();
    activity.userId = userId;
    activity.cardId = card.getId();
    activity.boardId = card.getBoardId();
    activity.type = type;
    return activity;
  }

  public void addDetail(String key, Object value) {
    detail.put(key, value);
  }

  public UserId getUserId() {
    return userId;
  }

  public CardId getCardId() {
    return cardId;
  }

  public BoardId getBoardId() {
    return boardId;
  }

  public ActivityType getType() {
    return type;
  }

  public String getDetailJson() {
    return JsonUtils.toJson(detail);
  }
}
