package com.taskagile.domain.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUrlCreator {

  private boolean isLocalStorage;
  private String cdnUrl;

  public FileUrlCreator(@Value("${app.file-storage.active}") String fileStorage,
                        @Value("${app.cdn.url}") String cdnUrl) {
    this.isLocalStorage = "localFileStorage".equals(fileStorage);
    this.cdnUrl = cdnUrl;
  }

  public String url(String fileRelativePath) {
    if (fileRelativePath == null) {
      return null;
    }

    // In case file relative path is actually an URL
    if (fileRelativePath.startsWith("https://") || fileRelativePath.startsWith("http://")) {
      return fileRelativePath;
    }

    // Use local file servlet to serve the file for local dev environment
    if (isLocalStorage) {
      return "/local-file/" + fileRelativePath;
    }
    return cdnUrl + "/" + fileRelativePath;
  }

}
