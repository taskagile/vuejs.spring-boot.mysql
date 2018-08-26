package com.taskagile.domain.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * Domain event
 */
public abstract class DomainEvent extends ApplicationEvent {

  private static final long serialVersionUID = -444783093811334147L;

  public DomainEvent(Object source) {
    super(source);
  }

  /**
   * Get the timestamp this event occurred
   */
  public long occurredAt() {
    // Return the underlying implementation's timestamp
    return getTimestamp();
  }
}
