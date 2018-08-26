package com.taskagile.domain.common.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * The default implementation of DomainEventPublisher that
 * bases on Spring Application Event
 */
@Component
public class DefaultDomainEventPublisher implements DomainEventPublisher {

  private ApplicationEventPublisher actualPublisher;

  public DefaultDomainEventPublisher(ApplicationEventPublisher actualPublisher) {
    this.actualPublisher = actualPublisher;
  }

  @Override
  public void publish(DomainEvent event) {
    actualPublisher.publishEvent(event);
  }

}
