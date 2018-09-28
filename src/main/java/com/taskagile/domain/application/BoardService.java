package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.AddBoardMemberCommand;
import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.domain.model.user.UserNotFoundException;

import java.util.List;

public interface BoardService {

  /**
   * Find the boards that a user is a member, including those boards
   * the user created as well as joined.
   *
   * @param userId the id of the user
   * @return a list of boards or an empty list if none found
   */
  List<Board> findBoardsByMembership(UserId userId);

  /**
   * Find board by its id
   *
   * @param boardId the id of the board
   * @return the board instance, null if not found
   */
  Board findById(BoardId boardId);

  /**
   * Find board members
   *
   * @param boardId the id of the board
   * @return a list of members of the board
   */
  List<User> findMembers(BoardId boardId);

  /**
   * Create a new board
   *
   * @param command the command instance
   * @return the new board just created
   */
  Board createBoard(CreateBoardCommand command);

  /**
   * Add board member
   *
   * @param command the command instance
   * @return newly added member user
   * @throws UserNotFoundException user by the usernameOrEmailAddress not found
   */
  User addMember(AddBoardMemberCommand command) throws UserNotFoundException;
}
