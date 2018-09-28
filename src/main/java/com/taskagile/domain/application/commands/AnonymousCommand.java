package com.taskagile.domain.application.commands;

import com.taskagile.domain.common.event.TriggeredFrom;
import com.taskagile.utils.IpAddress;
import io.jsonwebtoken.lang.Assert;

import java.util.Objects;

/**
 * A command triggered by not authenticated user
 */
public abstract class AnonymousCommand implements TriggeredFrom {

  private IpAddress ipAddress;

  public void triggeredBy(IpAddress ipAddress) {
    Assert.notNull(ipAddress, "Parameter `ipAddress` must not be null");

    this.ipAddress = ipAddress;
  }

  @Override
  public IpAddress getIpAddress() {
    return ipAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AnonymousCommand)) return false;
    AnonymousCommand that = (AnonymousCommand) o;
    return Objects.equals(ipAddress, that.ipAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress);
  }
}
