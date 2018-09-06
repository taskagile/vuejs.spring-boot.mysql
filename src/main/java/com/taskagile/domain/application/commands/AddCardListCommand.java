package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;

public class AddCardListCommand {

  private UserId userId;
  private String name;
  private BoardId boardId;
  private int position;

  public AddCardListCommand(BoardId boardId, UserId userId, String name, int position) {
    this.boardId = boardId;
    this.userId = userId;
    this.name = name;
    this.position = position;
  }

  public BoardId getBoardId() {
    return boardId;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public int getPosition() {
    return position;
  }
}
