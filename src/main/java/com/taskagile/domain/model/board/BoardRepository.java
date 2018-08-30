package com.taskagile.domain.model.board;

import com.taskagile.domain.model.user.UserId;

import java.util.List;

public interface BoardRepository {

  /**
   * Find the boards that a user is a member, including those boards
   * the user created as well as joined.
   *
   * @param userId the id of the user
   * @return a list of boards or an empty list if none found
   */
  List<Board> findBoardsByMembership(UserId userId);

  /**
   * Save a board
   *
   * @param board the board to save
   */
  void save(Board board);
}
