package com.taskagile.domain.common.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

  /**
   * Save a file
   *
   * @param folder the folder the file will be saved into
   * @param file the file to save
   * @return the saved file's path
   */
  TempFile saveAsTempFile(String folder, MultipartFile file);

  /**
   * Save a temp file to its target location
   *
   * @param tempFile a temp file
   */
  void saveTempFile(TempFile tempFile);

  /**
   * Save uploaded file to its target location
   *
   * @param folder the folder the file will be saved into
   * @param file the file to save
   * @return saved file's relative path
   */
  String saveUploaded(String folder, MultipartFile file);
}
