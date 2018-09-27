package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddCardCommentCommand;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;

public class AddCardCommentPayload {

  private String comment;

  public AddCardCommentCommand toCommand(CardId cardId, UserId userId) {
    return new AddCardCommentCommand(cardId, comment, userId);
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
