package com.taskagile.domain.model.card.events;

import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.card.Card;

public class CardAddedEvent extends CardDomainEvent {

  private static final long serialVersionUID = 26551114425630902L;

  public CardAddedEvent(Card card, TriggeredBy triggeredBy) {
    super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
  }

  @Override
  public String toString() {
    return "CardAddedEvent{" +
      "cardId=" + getCardId() +
      ", cardTitle='" + getCardTitle() + '\'' +
      '}';
  }
}
