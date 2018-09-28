package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.card.CardId;
import org.springframework.web.multipart.MultipartFile;

public class AddCardAttachmentCommand extends UserCommand {

  private CardId cardId;
  private MultipartFile file;

  public AddCardAttachmentCommand(long cardId, MultipartFile file) {
    this.cardId = new CardId(cardId);
    this.file = file;
  }

  public CardId getCardId() {
    return cardId;
  }

  public MultipartFile getFile() {
    return file;
  }
}
