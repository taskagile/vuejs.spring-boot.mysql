package com.taskagile.web.apis;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.application.UserService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.domain.model.user.User;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.MyDataResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MeApiController {

  private TeamService teamService;
  private BoardService boardService;
  private UserService userService;

  public MeApiController(TeamService teamService, BoardService boardService, UserService userService) {
    this.teamService = teamService;
    this.boardService = boardService;
    this.userService = userService;
  }

  @GetMapping("/api/me")
  public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
    User user = userService.findById(currentUser.getUserId());
    List<Team> teams = teamService.findTeamsByUserId(currentUser.getUserId());
    List<Board> boards = boardService.findBoardsByMembership(currentUser.getUserId());
    return MyDataResult.build(user, teams, boards);
  }
}
