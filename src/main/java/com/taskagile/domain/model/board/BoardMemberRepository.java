package com.taskagile.domain.model.board;

public interface BoardMemberRepository {

  /**
   * Save board member
   *
   * @param boardMember the board member to save
   */
  void save(BoardMember boardMember);
}
