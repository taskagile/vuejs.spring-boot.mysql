package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddBoardMemberCommand;
import com.taskagile.domain.model.board.BoardId;

public class AddBoardMemberPayload {

  private String usernameOrEmailAddress;

  public AddBoardMemberCommand toCommand(BoardId boardId) {
    return new AddBoardMemberCommand(boardId, usernameOrEmailAddress);
  }

  public void setUsernameOrEmailAddress(String usernameOrEmailAddress) {
    this.usernameOrEmailAddress = usernameOrEmailAddress;
  }
}
