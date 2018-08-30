package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;

public class CreateBoardCommand {

  private UserId userId;
  private String name;
  private String description;
  private TeamId teamId;

  public CreateBoardCommand(UserId userId, String name, String description, TeamId teamId) {
    this.userId = userId;
    this.name = name;
    this.description = description;
    this.teamId = teamId;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public TeamId getTeamId() {
    return teamId;
  }
}
