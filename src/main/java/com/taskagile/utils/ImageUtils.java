package com.taskagile.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.Assert;

public final class ImageUtils {

  private ImageUtils () {
  }

  /**
   * Get the path/URL of the image's thumbnail version
   *
   * @param imagePath a relative image path or image URL
   * @return the thumbnail version's path/URL
   */
  public static String getThumbnailVersion(String imagePath) {
    Assert.hasText(imagePath, "Parameter `imagePath` must not be blank");

    String ext = FilenameUtils.getExtension(imagePath);
    Assert.hasText(ext, "Image `" + imagePath + "` must have extension");

    return FilenameUtils.removeExtension(imagePath) + ".thumbnail." + ext;
  }

  /**
   * Check if a file is image or not
   *
   * @param contentType file's content type, for example, "image/jpeg"
   * @return true when it is an image, false otherwise
   */
  public static boolean isImage(String contentType) {
    if (contentType == null) {
      return false;
    }
    return contentType.startsWith("image/");
  }
}
