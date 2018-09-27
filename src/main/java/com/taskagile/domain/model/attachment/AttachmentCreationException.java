package com.taskagile.domain.model.attachment;

public class AttachmentCreationException extends RuntimeException {
  private static final long serialVersionUID = 6001139032304387250L;

  public AttachmentCreationException(String message, Throwable cause) {
    super(message, cause);
  }
}
