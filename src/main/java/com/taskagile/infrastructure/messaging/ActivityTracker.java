package com.taskagile.infrastructure.messaging;

import com.taskagile.domain.application.ActivityService;
import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.activity.DomainEventToActivityConverter;
import com.taskagile.domain.model.activity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ActivityTracker {

  private final static Logger log = LoggerFactory.getLogger(ActivityTracker.class);

  private ActivityService activityService;
  private DomainEventToActivityConverter domainEventToActivityConverter;

  public ActivityTracker(ActivityService activityService,
                         DomainEventToActivityConverter domainEventToActivityConverter) {
    this.activityService = activityService;
    this.domainEventToActivityConverter = domainEventToActivityConverter;
  }

  @RabbitListener(queues = "#{activityTrackingQueue.name}")
  public void receive(DomainEvent domainEvent) {
    log.debug("Receive domain event: " + domainEvent);

    Activity activity = domainEventToActivityConverter.toActivity(domainEvent);
    // Save the activity only when there is an activity
    // result from the domain event
    if (activity != null) {
      activityService.saveActivity(activity);
    }
  }
}
