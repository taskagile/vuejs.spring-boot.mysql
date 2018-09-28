package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.card.CardId;

public class AddCardCommentCommand extends UserCommand {

  private CardId cardId;
  private String comment;

  public AddCardCommentCommand(CardId cardId, String comment) {
    this.cardId = cardId;
    this.comment = comment;
  }

  public CardId getCardId() {
    return cardId;
  }

  public String getComment() {
    return comment;
  }
}
