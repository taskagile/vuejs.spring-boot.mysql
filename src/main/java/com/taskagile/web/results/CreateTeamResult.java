package com.taskagile.web.results;

import com.taskagile.domain.model.team.Team;
import org.springframework.http.ResponseEntity;

public class CreateTeamResult {

  public static ResponseEntity<ApiResult> build(Team team) {
    ApiResult apiResult = ApiResult.blank()
      .add("id", team.getId().value())
      .add("name", team.getName());
    return Result.ok(apiResult);
  }
}
