package com.taskagile.domain.common.file;

public class FileStorageException extends RuntimeException {

  private static final long serialVersionUID = -5546874656158296944L;

  public FileStorageException(String message) {
    super(message);
  }

  public FileStorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
