package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.CreateTeamCommand;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.UserId;

import java.util.List;

public interface TeamService {

  /**
   * Find the teams that created by a user
   *
   * @param userId the id of the user
   * @return a list of teams or an empty list if none found
   */
  List<Team> findTeamsByUserId(UserId userId);

  /**
   * Create a new team
   *
   * @param command the command instance
   * @return the newly created team
   */
  Team createTeam(CreateTeamCommand command);
}
