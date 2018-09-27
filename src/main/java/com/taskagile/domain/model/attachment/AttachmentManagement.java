package com.taskagile.domain.model.attachment;

import com.taskagile.domain.common.file.FileStorage;
import com.taskagile.domain.common.file.FileStorageResolver;
import com.taskagile.domain.common.file.TempFile;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@Component
public class AttachmentManagement {

  private final static Logger log = LoggerFactory.getLogger(AttachmentManagement.class);

  private FileStorageResolver fileStorageResolver;
  private ThumbnailCreator thumbnailCreator;
  private AttachmentRepository attachmentRepository;

  public AttachmentManagement(FileStorageResolver fileStorageResolver,
                              ThumbnailCreator thumbnailCreator,
                              AttachmentRepository attachmentRepository) {
    this.fileStorageResolver = fileStorageResolver;
    this.thumbnailCreator = thumbnailCreator;
    this.attachmentRepository = attachmentRepository;
  }

  public Attachment save(CardId cardId, MultipartFile file, UserId userId) {
    FileStorage fileStorage = fileStorageResolver.resolve();

    String filePath;
    String folder = "attachments";
    boolean thumbnailCreated = false;
    if (ImageUtils.isImage(file.getContentType())) {
      filePath = saveImage(fileStorage, folder, file);
      thumbnailCreated = true;
    } else {
      filePath = fileStorage.saveUploaded(folder, file);
    }

    Attachment attachment = Attachment.create(cardId, userId, file.getOriginalFilename(), filePath, thumbnailCreated);
    attachmentRepository.save(attachment);
    return attachment;
  }

  private String saveImage(FileStorage fileStorage, String folder, MultipartFile file) {
    // Save the file as a local temp file
    TempFile tempImageFile = fileStorage.saveAsTempFile(folder, file);

    // Save the temp image file to its target location
    fileStorage.saveTempFile(tempImageFile);

    // Create a thumbnail of the image file
    thumbnailCreator.create(fileStorage, tempImageFile);

    try {
      Files.delete(tempImageFile.getFile().toPath());
    } catch (IOException e) {
      log.error("Failed to delete temp file `" + tempImageFile.getFile().getAbsolutePath() + "`", e);
    }
    return tempImageFile.getFileRelativePath();
  }
}
