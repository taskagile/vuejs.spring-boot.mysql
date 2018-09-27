package com.taskagile.domain.model.attachment;

public class ThumbnailCreationException extends RuntimeException {
  private static final long serialVersionUID = 6259084841233699937L;

  public ThumbnailCreationException(String message) {
    super(message);
  }

  public ThumbnailCreationException(String message, Throwable cause) {
    super(message, cause);
  }
}
