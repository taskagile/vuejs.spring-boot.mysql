package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;

public class CreateBoardPayload {

  private String name;
  private String description;
  private long teamId;

  public CreateBoardCommand toCommand(UserId userId) {
    return new CreateBoardCommand(userId, name, description, new TeamId(teamId));
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTeamId(long teamId) {
    this.teamId = teamId;
  }

}
