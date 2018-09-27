package com.taskagile.domain.model.attachment;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "attachment")
public class Attachment extends AbstractBaseEntity {

  private static final long serialVersionUID = 4614318546123429009L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "card_id")
  private long cardId;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "file_path")
  private String filePath;

  @Column(name = "file_type")
  private String fileType;

  @Column(name = "thumbnail_created")
  private boolean thumbnailCreated;

  @Column(name = "archived")
  private boolean archived;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  public static Attachment create(CardId cardId, UserId userId, String fileName, String filePath, boolean thumbnailCreated) {
    Attachment attachment = new Attachment();
    attachment.cardId = cardId.value();
    attachment.userId = userId.value();
    attachment.fileName = fileName;
    attachment.fileType = FilenameUtils.getExtension(fileName);
    attachment.filePath = filePath;
    attachment.thumbnailCreated = thumbnailCreated;
    attachment.archived = false;
    attachment.createdDate = new Date();
    return attachment;
  }

  public AttachmentId getId() {
    return new AttachmentId(id);
  }

  public CardId getCardId() {
    return new CardId(cardId);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public String getFileName() {
    return fileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public String getFileType() {
    return fileType;
  }

  public boolean isThumbnailCreated() {
    return thumbnailCreated;
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
    if (!(o instanceof Attachment)) return false;
    Attachment that = (Attachment) o;
    return cardId == that.cardId &&
      userId == that.userId &&
      archived == that.archived &&
      Objects.equals(fileType, that.fileType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardId, userId, fileType, archived);
  }

  @Override
  public String toString() {
    return "Attachment{" +
      "id=" + id +
      ", cardId=" + cardId +
      ", userId=" + userId +
      ", fileName='" + fileName + '\'' +
      ", filePath='" + filePath + '\'' +
      ", fileType='" + fileType + '\'' +
      ", thumbnailCreated=" + thumbnailCreated +
      ", archived=" + archived +
      ", createdDate=" + createdDate +
      '}';
  }
}
