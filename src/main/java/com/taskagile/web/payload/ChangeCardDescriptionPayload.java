package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.ChangeCardDescriptionCommand;
import com.taskagile.domain.model.card.CardId;

public class ChangeCardDescriptionPayload {

  private String description;

  public ChangeCardDescriptionCommand toCommand(long cardId) {
    return new ChangeCardDescriptionCommand(new CardId(cardId), description);
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
