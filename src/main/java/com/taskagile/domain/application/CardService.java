package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.AddCardCommand;
import com.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;

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
}
