package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;

public class AddCardCommentCommand {

  private UserId userId;
  private CardId cardId;
  private String comment;

  public AddCardCommentCommand(CardId cardId, String comment, UserId userId) {
    this.userId = userId;
    this.cardId = cardId;
    this.comment = comment;
  }

  public UserId getUserId() {
    return userId;
  }

  public CardId getCardId() {
    return cardId;
  }

  public String getComment() {
    return comment;
  }
}
