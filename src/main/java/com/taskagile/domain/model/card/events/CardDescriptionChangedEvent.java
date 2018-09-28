package com.taskagile.domain.model.card.events;

import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.card.Card;

public class CardDescriptionChangedEvent extends CardDomainEvent {

  private static final long serialVersionUID = 26551114425630902L;

  private String newDescription;
  private String oldDescription;

  public CardDescriptionChangedEvent(Card card, String oldDescription, TriggeredBy triggeredBy) {
    super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
    this.newDescription = card.getDescription();
    this.oldDescription = oldDescription;
  }

  public String getNewDescription() {
    return newDescription;
  }

  public String getOldDescription() {
    return oldDescription;
  }

  @Override
  public String toString() {
    return "CardDescriptionChangedEvent{" +
      "cardId=" + getCardId() +
      ", newDescription='" + newDescription + '\'' +
      ", oldDescription='" + oldDescription + '\'' +
      '}';
  }
}
