package com.taskagile.domain.model.card;

import com.taskagile.domain.model.cardlist.CardListId;

public class CardPosition {

  private long cardListId;
  private long cardId;
  private int position;

  public CardListId getCardListId() {
    return new CardListId(cardListId);
  }

  public CardId getCardId() {
    return new CardId(cardId);
  }

  public int getPosition() {
    return position;
  }

  public void setCardListId(long cardListId) {
    this.cardListId = cardListId;
  }

  public void setCardId(long cardId) {
    this.cardId = cardId;
  }

  public void setPosition(int position) {
    this.position = position;
  }

}
