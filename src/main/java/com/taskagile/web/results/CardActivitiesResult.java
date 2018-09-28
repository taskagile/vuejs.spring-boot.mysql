package com.taskagile.web.results;

import com.taskagile.domain.model.activity.Activity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class CardActivitiesResult {

  public static ResponseEntity<ApiResult> build(List<Activity> activities) {
    List<ListableActivity> result = new ArrayList<>();
    for (Activity activity : activities) {
      result.add(new ListableActivity(activity));
    }
    ApiResult apiResult = ApiResult.blank()
      .add("activities", result);
    return Result.ok(apiResult);
  }

  private static class ListableActivity {
    private long id;
    private String type;
    private String detail;
    private long userId;
    private long createdDate;

    ListableActivity(Activity activity) {
      this.id = activity.getId().value();
      this.type = activity.getType().getType();
      this.detail = activity.getDetail();
      this.userId = activity.getUserId().value();
      this.createdDate = activity.getCreatedDate().getTime();
    }

    public long getId() {
      return id;
    }

    public String getType() {
      return type;
    }

    public String getDetail() {
      return detail;
    }

    public long getUserId() {
      return userId;
    }

    public long getCreatedDate() {
      return createdDate;
    }
  }
}
