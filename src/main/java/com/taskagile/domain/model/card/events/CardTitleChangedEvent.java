package com.taskagile.domain.model.card.events;

import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.card.Card;

public class CardTitleChangedEvent extends CardDomainEvent {

  private static final long serialVersionUID = 26551114425630902L;

  private String newTitle;
  private String oldTitle;

  public CardTitleChangedEvent(Card card, String oldTitle, TriggeredBy triggeredBy) {
    super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
    this.newTitle = card.getTitle();
    this.oldTitle = oldTitle;
  }

  public String getNewTitle() {
    return newTitle;
  }

  public String getOldTitle() {
    return oldTitle;
  }

  @Override
  public String toString() {
    return "CardTitleChangedEvent{" +
      "cardId=" + getCardId() +
      ", newTitle='" + newTitle + '\'' +
      ", oldTitle='" + oldTitle + '\'' +
      '}';
  }
}
