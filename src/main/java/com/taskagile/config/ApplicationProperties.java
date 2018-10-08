package com.taskagile.config;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix="app")
@Validated
public class ApplicationProperties {

  /**
   * Default `from` value of emails sent out by the system
   */
  @Email
  @NotBlank
  private String mailFrom;

  @NotBlank
  @NotEmpty
  private String tokenSecretKey;

  @NotBlank
  @NotEmpty
  private String realTimeServerUrl;

  @NotNull
  private FileStorageSetting fileStorage;

  @NotNull
  private ImageSetting image;

  private CdnSetting cdn;

  public void setMailFrom(String mailFrom) {
    this.mailFrom = mailFrom;
  }

  public String getMailFrom() {
    return mailFrom;
  }

  public String getTokenSecretKey() {
    return tokenSecretKey;
  }

  public void setTokenSecretKey(String tokenSecretKey) {
    this.tokenSecretKey = tokenSecretKey;
  }

  public String getRealTimeServerUrl() {
    return realTimeServerUrl;
  }

  public void setRealTimeServerUrl(String realTimeServerUrl) {
    this.realTimeServerUrl = realTimeServerUrl;
  }

  public FileStorageSetting getFileStorage() {
    return fileStorage;
  }

  public void setFileStorage(FileStorageSetting fileStorage) {
    this.fileStorage = fileStorage;
  }

  public ImageSetting getImage() {
    return image;
  }

  public void setImage(ImageSetting image) {
    this.image = image;
  }

  public CdnSetting getCdn() {
    return cdn;
  }

  public void setCdn(CdnSetting cdn) {
    this.cdn = cdn;
  }

  //---------------------------------------
  // Setting structure classes
  //---------------------------------------

  private static class FileStorageSetting {

    private String localRootFolder;

    @NotBlank
    @NotEmpty
    private String tempFolder;

    @NotBlank
    @NotEmpty
    private String active;

    private String s3AccessKey;
    private String s3SecretKey;
    private String s3BucketName;
    private String s3Region;

    public String getLocalRootFolder() {
      return localRootFolder;
    }

    public void setLocalRootFolder(String localRootFolder) {
      this.localRootFolder = localRootFolder;
    }

    public String getTempFolder() {
      return tempFolder;
    }

    public void setTempFolder(String tempFolder) {
      this.tempFolder = tempFolder;
    }

    public String getActive() {
      return active;
    }

    public void setActive(String active) {
      this.active = active;
    }

    public String getS3AccessKey() {
      return s3AccessKey;
    }

    public void setS3AccessKey(String s3AccessKey) {
      this.s3AccessKey = s3AccessKey;
    }

    public String getS3SecretKey() {
      return s3SecretKey;
    }

    public void setS3SecretKey(String s3SecretKey) {
      this.s3SecretKey = s3SecretKey;
    }

    public String getS3BucketName() {
      return s3BucketName;
    }

    public void setS3BucketName(String s3BucketName) {
      this.s3BucketName = s3BucketName;
    }

    public String getS3Region() {
      return s3Region;
    }

    public void setS3Region(String s3Region) {
      this.s3Region = s3Region;
    }
  }

  private static class ImageSetting {
    @NotBlank
    @NotEmpty
    private String commandSearchPath;

    public String getCommandSearchPath() {
      return commandSearchPath;
    }

    public void setCommandSearchPath(String commandSearchPath) {
      this.commandSearchPath = commandSearchPath;
    }
  }

  private static class CdnSetting {
    private String url = "http://taskagile.local";

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }
}
