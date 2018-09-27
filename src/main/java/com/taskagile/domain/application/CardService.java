package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.*;
import com.taskagile.domain.model.activity.Activity;
import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.CardId;

import java.util.List;

public interface CardService {

  /**
   * Find all the cards of a board
   *
   * @param boardId the id of the board
   * @return a list of card instances or an empty list if none found
   */
  List<Card> findByBoardId(BoardId boardId);

  /**
   * Find card by its id
   *
   * @param cardId the id of the card
   * @return a card instance or null if not found
   */
  Card findById(CardId cardId);

  /**
   * Get the activities related to a card
   *
   * @param cardId the id of the card
   * @return a list of card activities
   */
  List<Activity> findCardActivities(CardId cardId);

  /**
   * Get card attachments
   *
   * @param cardId the id of the card
   * @return a list of card attachments
   */
  List<Attachment> getAttachments(CardId cardId);

  /**
   * Add card
   *
   * @param command the command instance
   * @return the newly added card
   */
  Card addCard(AddCardCommand command);

  /**
   * Change card positions
   *
   * @param command the command instance
   */
  void changePositions(ChangeCardPositionsCommand command);

  /**
   * Change card's title
   *
   * @param command the command instance
   */
  void changeCardTitle(ChangeCardTitleCommand command);

  /**
   * Change card's description
   *
   * @param command the command instance
   */
  void changeCardDescription(ChangeCardDescriptionCommand command);

  /**
   * Add card comment
   *
   * @param command the command instance
   * @return an instance of Activity
   */
  Activity addComment(AddCardCommentCommand command);

  /**
   * Add attachment to a card
   *
   * @param command the command instance
   * @return created attachment
   */
  Attachment addAttachment(AddCardAttachmentCommand command);

}
