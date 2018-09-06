package com.taskagile.domain.model.cardlist;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "card_list")
public class CardList extends AbstractBaseEntity {

  private static final long serialVersionUID = -6547708151192480923L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "board_id")
  private long boardId;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "position")
  private int position;

  @Column(name = "archived")
  private boolean archived;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  public static CardList create(BoardId boardId, UserId userId, String name, int position) {
    CardList cardList = new CardList();
    cardList.boardId = boardId.value();
    cardList.userId = userId.value();
    cardList.name = name;
    cardList.position = position;
    cardList.archived = false;
    cardList.createdDate = new Date();
    return cardList;
  }

  public CardListId getId() {
    return new CardListId(id);
  }

  public BoardId getBoardId() {
    return new BoardId(boardId);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public String getName() {
    return name;
  }

  public int getPosition() {
    return position;
  }

  public boolean isArchived() {
    return archived;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CardList)) return false;
    CardList cardList = (CardList) o;
    return boardId == cardList.boardId &&
      position == cardList.position &&
      archived == cardList.archived &&
      Objects.equals(name, cardList.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boardId, name, position, archived);
  }

  @Override
  public String toString() {
    return "CardList{" +
      "id=" + id +
      ", boardId=" + boardId +
      ", userId=" + userId +
      ", name='" + name + '\'' +
      ", position=" + position +
      ", archived=" + archived +
      ", createdDate=" + createdDate +
      '}';
  }
}
