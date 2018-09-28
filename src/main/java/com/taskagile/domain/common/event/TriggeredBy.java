package com.taskagile.domain.common.event;

import com.taskagile.domain.model.user.UserId;
import com.taskagile.utils.IpAddress;

public interface TriggeredBy {

  /**
   * Get the id of the user who triggered this command
   *
   * @return a user's id
   */
  UserId getUserId();

  /**
   * Get the IP address where the request originated from
   *
   * @return an IP address
   */
  IpAddress getIpAddress();
}
