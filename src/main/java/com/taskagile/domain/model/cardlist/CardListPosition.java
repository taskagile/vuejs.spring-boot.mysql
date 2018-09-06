package com.taskagile.domain.model.cardlist;

public class CardListPosition {

  private long cardListId;
  private int position;

  public CardListId getCardListId() {
    return new CardListId(cardListId);
  }

  public int getPosition() {
    return position;
  }

  public void setCardListId(long cardListId) {
    this.cardListId = cardListId;
  }

  public void setPosition(int position) {
    this.position = position;
  }

}
