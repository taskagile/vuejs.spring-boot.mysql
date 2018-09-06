package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.CardPosition;

import java.util.List;

public class ChangeCardPositionsPayload {

  private long boardId;
  private List<CardPosition> cardPositions;

  public ChangeCardPositionsCommand toCommand() {
    return new ChangeCardPositionsCommand(new BoardId(boardId), cardPositions);
  }

  public void setBoardId(long boardId) {
    this.boardId = boardId;
  }

  public void setCardPositions(List<CardPosition> cardPositions) {
    this.cardPositions = cardPositions;
  }
}
