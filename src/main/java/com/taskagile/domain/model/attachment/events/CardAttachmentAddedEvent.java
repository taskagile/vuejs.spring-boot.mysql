package com.taskagile.domain.model.attachment.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.attachment.Attachment;

public class CardAttachmentAddedEvent extends DomainEvent {

  private static final long serialVersionUID = -7962885726212050836L;

  private Attachment attachment;

  public CardAttachmentAddedEvent(Object source, Attachment attachment) {
    super(source);
    this.attachment = attachment;
  }

  public Attachment getAttachment() {
    return attachment;
  }
}
