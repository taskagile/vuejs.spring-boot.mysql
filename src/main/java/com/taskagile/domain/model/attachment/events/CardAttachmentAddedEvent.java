package com.taskagile.domain.model.attachment.events;

import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.domain.model.attachment.AttachmentId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.events.CardDomainEvent;

public class CardAttachmentAddedEvent extends CardDomainEvent {

  private static final long serialVersionUID = -7962885726212050836L;

  private String cardTitle;
  private AttachmentId attachmentId;
  private String fileName;

  public CardAttachmentAddedEvent(Card card, Attachment attachment, TriggeredBy triggeredBy) {
    super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
    this.cardTitle = card.getTitle();
    this.attachmentId = attachment.getId();
    this.fileName = attachment.getFileName();
  }

  public String getCardTitle() {
    return cardTitle;
  }

  public AttachmentId getAttachmentId() {
    return attachmentId;
  }

  public String getFileName() {
    return fileName;
  }

  @Override
  public String toString() {
    return "CardAttachmentAddedEvent{" +
      "cardId=" + getCardId() +
      ", cardTitle='" + cardTitle + '\'' +
      ", attachmentId=" + attachmentId +
      ", fileName='" + fileName + '\'' +
      '}';
  }
}
