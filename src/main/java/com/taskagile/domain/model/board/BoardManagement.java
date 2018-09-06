package com.taskagile.domain.model.board;

import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;
import org.springframework.stereotype.Component;

@Component
public class BoardManagement {

  private BoardRepository boardRepository;
  private BoardMemberRepository boardMemberRepository;

  public BoardManagement(BoardRepository boardRepository,
                         BoardMemberRepository boardMemberRepository) {
    this.boardRepository = boardRepository;
    this.boardMemberRepository = boardMemberRepository;
  }

  /**
   * Create a new board
   *
   * @param creatorId the user id who creates this board
   * @param name the name of the board
   * @param description the description of the board
   * @param teamId the id of the team that the board belongs to. Null if it is a personal board
   * @return the newly created board
   */
  public Board createBoard(UserId creatorId, String name, String description, TeamId teamId) {
    Board board = Board.create(creatorId, name,
      description, teamId);
    boardRepository.save(board);
    // Add the creator to as a board member
    BoardMember boardMember = BoardMember.create(board.getId(), creatorId);
    boardMemberRepository.save(boardMember);
    return board;
  }
}
