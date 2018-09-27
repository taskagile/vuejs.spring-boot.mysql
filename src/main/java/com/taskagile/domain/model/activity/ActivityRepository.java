package com.taskagile.domain.model.activity;

import com.taskagile.domain.model.card.CardId;

import java.util.List;

public interface ActivityRepository {

  /**
   * Save activity
   *
   * @param activity the activity to save
   */
  void save(Activity activity);

  /**
   * Get the activities related to a card
   *
   * @param cardId the id of the card
   * @return a list of card activities
   */
  List<Activity> findCardActivities(CardId cardId);
}
