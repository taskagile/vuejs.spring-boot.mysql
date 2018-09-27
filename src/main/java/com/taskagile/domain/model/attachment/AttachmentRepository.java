package com.taskagile.domain.model.attachment;

import com.taskagile.domain.model.card.CardId;

import java.util.List;

public interface AttachmentRepository {

  /**
   * Find card attachments
   *
   * @param cardId the id of the card
   * @return a list of attachment, empty list if none found
   */
  List<Attachment> findAttachments(CardId cardId);

  /**
   * Save attachment
   *
   * @param attachment the attachment to save
   */
  void save(Attachment attachment);
}
