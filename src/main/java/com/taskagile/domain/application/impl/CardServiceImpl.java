package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.CardService;
import com.taskagile.domain.application.commands.*;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.model.activity.Activity;
import com.taskagile.domain.model.activity.ActivityRepository;
import com.taskagile.domain.model.activity.ActivityType;
import com.taskagile.domain.model.activity.CardActivity;
import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.domain.model.attachment.AttachmentRepository;
import com.taskagile.domain.model.attachment.events.CardAttachmentAddedEvent;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.card.CardRepository;
import com.taskagile.domain.model.card.events.CardAddedEvent;
import com.taskagile.domain.model.card.events.CardDescriptionChangedEvent;
import com.taskagile.domain.model.card.events.CardTitleChangedEvent;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListRepository;
import com.taskagile.domain.model.attachment.AttachmentManagement;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CardServiceImpl implements CardService {

  private CardRepository cardRepository;
  private CardListRepository cardListRepository;
  private ActivityRepository activityRepository;
  private AttachmentManagement attachmentManagement;
  private AttachmentRepository attachmentRepository;
  private DomainEventPublisher domainEventPublisher;

  public CardServiceImpl(CardRepository cardRepository,
                         CardListRepository cardListRepository,
                         ActivityRepository activityRepository,
                         AttachmentRepository attachmentRepository,
                         AttachmentManagement attachmentManagement,
                         DomainEventPublisher domainEventPublisher) {
    this.cardRepository = cardRepository;
    this.cardListRepository = cardListRepository;
    this.activityRepository = activityRepository;
    this.attachmentManagement = attachmentManagement;
    this.attachmentRepository = attachmentRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Override
  public List<Card> findByBoardId(BoardId boardId) {
    return cardRepository.findByBoardId(boardId);
  }

  @Override
  public Card findById(CardId cardId) {
    return cardRepository.findById(cardId);
  }

  @Override
  public List<Activity> findCardActivities(CardId cardId) {
    return activityRepository.findCardActivities(cardId);
  }

  @Override
  public List<Attachment> getAttachments(CardId cardId) {
    return attachmentRepository.findAttachments(cardId);
  }

  @Override
  public Card addCard(AddCardCommand command) {
    CardList cardList = cardListRepository.findById(command.getCardListId());
    Assert.notNull(cardList, "Card list must not be null");

    Card card = Card.create(cardList, command.getUserId(), command.getTitle(), command.getPosition());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardAddedEvent(this, card));
    return card;
  }

  @Override
  public void changePositions(ChangeCardPositionsCommand command) {
    cardRepository.changePositions(command.getCardPositions());
  }

  @Override
  public void changeCardTitle(ChangeCardTitleCommand command) {
    Assert.notNull(command, "Parameter `command` must not be null");

    Card card = findCard(command.getCardId());
    card.changeTitle(command.getTitle());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardTitleChangedEvent(this, card));
  }

  @Override
  public void changeCardDescription(ChangeCardDescriptionCommand command) {
    Assert.notNull(command, "Parameter `command` must not be null");

    Card card = findCard(command.getCardId());
    card.changeDescription(command.getDescription());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardDescriptionChangedEvent(this, card));
  }

  @Override
  public Activity addComment(AddCardCommentCommand command) {
    Assert.notNull(command, "Parameter `command` must not be null");

    Card card = findCard(command.getCardId());
    CardActivity cardActivity = CardActivity.create(command.getUserId(), card, ActivityType.ADD_COMMENT);
    cardActivity.addDetail("comment", command.getComment());

    Activity activity = Activity.from(cardActivity);
    activityRepository.save(activity);
    return activity;
  }

  @Override
  public Attachment addAttachment(AddCardAttachmentCommand command) {
    Assert.notNull(command, "Parameter `command` must not be null");

    Attachment attachment = attachmentManagement.save(
      command.getCardId(), command.getFile(), command.getUserId());
    domainEventPublisher.publish(new CardAttachmentAddedEvent(this, attachment));
    return attachment;
  }

  private Card findCard(CardId cardId) {
    Card card = cardRepository.findById(cardId);
    Assert.notNull(card, "Card of id " + card + " must exist");
    return card;
  }
}
