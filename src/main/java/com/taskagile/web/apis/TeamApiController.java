package com.taskagile.web.apis;

import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.web.payload.CreateTeamPayload;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.CreateTeamResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TeamApiController {

  private TeamService teamService;

  public TeamApiController(TeamService teamService) {
    this.teamService = teamService;
  }

  @PostMapping("/api/teams")
  public ResponseEntity<ApiResult> createTeam(@RequestBody CreateTeamPayload payload,
                                              @CurrentUser SimpleUser currentUser) {
    Team team = teamService.createTeam(payload.toCommand(currentUser.getUserId()));
    return CreateTeamResult.build(team);
  }
}
