package com.taskagile.domain.model.activity;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "activity")
public class Activity extends AbstractBaseEntity {

  private static final long serialVersionUID = -1759127062966256817L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "card_id")
  private Long cardId;

  @Column(name = "board_id")
  private Long boardId;

  @Column(name = "type")
  @Enumerated(EnumType.ORDINAL)
  private ActivityType type;

  @Column(name = "detail")
  private String detail;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  public static Activity from(CardActivity cardActivity) {
    Activity activity = new Activity();
    activity.userId = cardActivity.getUserId().value();
    activity.cardId = cardActivity.getCardId().value();
    activity.boardId = cardActivity.getBoardId().value();
    activity.type = cardActivity.getType();
    activity.detail = cardActivity.getDetailJson();
    activity.createdDate = new Date();
    return activity;
  }

  public static Activity from(BoardActivity boardActivity) {
    Activity activity = new Activity();
    activity.userId = boardActivity.getUserId().value();
    activity.boardId = boardActivity.getBoardId().value();
    activity.type = boardActivity.getType();
    activity.detail = boardActivity.getDetailJson();
    activity.createdDate = new Date();
    return activity;
  }

  public ActivityId getId() {
    return new ActivityId(id);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public CardId getCardId() {
    return cardId == null ? null : new CardId(cardId);
  }

  public BoardId getBoardId() {
    return boardId == null ? null : new BoardId(boardId);
  }

  public ActivityType getType() {
    return type;
  }

  public String getDetail() {
    return detail;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Activity)) return false;
    Activity activity = (Activity) o;
    return userId == activity.userId &&
      type == activity.type &&
      Objects.equals(cardId, activity.cardId) &&
      Objects.equals(boardId, activity.boardId) &&
      Objects.equals(detail, activity.detail) &&
      Objects.equals(createdDate, activity.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, cardId, boardId, type, detail, createdDate);
  }

  @Override
  public String toString() {
    return "Activity{" +
      "id=" + id +
      ", userId=" + userId +
      ", cardId=" + cardId +
      ", boardId=" + boardId +
      ", type=" + type +
      ", detail='" + detail + '\'' +
      ", createdDate=" + createdDate +
      '}';
  }
}
