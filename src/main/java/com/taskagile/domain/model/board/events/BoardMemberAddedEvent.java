package com.taskagile.domain.model.board.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.User;

public class BoardMemberAddedEvent extends DomainEvent {

  private static final long serialVersionUID = -8979992986207557039L;

  private BoardId boardId;
  private User user;

  public BoardMemberAddedEvent(Object source, BoardId boardId, User user) {
    super(source);
    this.boardId = boardId;
    this.user = user;
  }

  public BoardId getBoardId() {
    return boardId;
  }

  public User getUser() {
    return user;
  }
}
