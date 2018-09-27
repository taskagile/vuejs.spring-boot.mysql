package com.taskagile.infrastructure.file.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.taskagile.domain.common.file.AbstractBaseFileStorage;
import com.taskagile.domain.common.file.FileStorageException;
import com.taskagile.domain.common.file.TempFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Component("s3FileStorage")
public class S3FileStorage extends AbstractBaseFileStorage {

  private static final Logger log = LoggerFactory.getLogger(S3FileStorage.class);

  private Environment environment;
  private String rootTempPath;
  private AmazonS3 s3;

  public S3FileStorage(Environment environment,
                       @Value("${app.file-storage.temp-folder}") String rootTempPath) {
    this.environment = environment;
    this.rootTempPath = rootTempPath;
    if ("s3FileStorage".equals(environment.getProperty("app.file-storage.active"))) {
      this.s3 = initS3Client();
    }
  }

  @Override
  public TempFile saveAsTempFile(String folder, MultipartFile multipartFile) {
    return saveMultipartFileToLocalTempFolder(rootTempPath, folder, multipartFile);
  }

  @Override
  public void saveTempFile(TempFile tempFile) {
    Assert.notNull(s3, "S3FileStorage must be initialized properly");

    String fileKey = tempFile.getFileRelativePath();
    String bucketName = environment.getProperty("app.file-storage.s3-bucket-name");
    Assert.hasText(bucketName, "Property `app.file-storage.s3-bucket-name` must not be blank");

    try {
      log.debug("Saving file `{}` to s3", tempFile.getFile().getName());
      PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileKey, tempFile.getFile());
      putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
      s3.putObject(putRequest);
      log.debug("File `{}` saved to s3", tempFile.getFile().getName(), fileKey);
    } catch (Exception e) {
      log.error("Failed to save file to s3", e);
      throw new FileStorageException("Failed to save file `" + tempFile.getFile().getName() + "` to s3", e);
    }
  }

  @Override
  public String saveUploaded(String folder, MultipartFile multipartFile) {
    Assert.notNull(s3, "S3FileStorage must be initialized properly");

    String originalFileName = multipartFile.getOriginalFilename();
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(multipartFile.getSize());
    metadata.setContentType(multipartFile.getContentType());
    metadata.addUserMetadata("Original-File-Name", originalFileName);
    String finalFileName = generateFileName(multipartFile);
    String s3ObjectKey = folder + "/" + finalFileName;

    String bucketName = environment.getProperty("app.file-storage.s3-bucket-name");
    Assert.hasText(bucketName, "Property `app.file-storage.s3-bucket-name` must not be blank");

    try {
      log.debug("Saving file `{}` to s3", originalFileName);
      PutObjectRequest putRequest = new PutObjectRequest(
        bucketName, s3ObjectKey, multipartFile.getInputStream(), metadata);
      putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
      s3.putObject(putRequest);
      log.debug("File `{}` saved to s3 as `{}`", originalFileName, s3ObjectKey);
    } catch (Exception e) {
      log.error("Failed to save file to s3", e);
      throw new FileStorageException("Failed to save file `" + multipartFile.getOriginalFilename() + "` to s3", e);
    }

    return s3ObjectKey;
  }

  private AmazonS3 initS3Client() {
    String s3Region = environment.getProperty("app.file-storage.s3-region");
    Assert.hasText(s3Region, "Property `app.file-storage.s3-region` must not be blank");

    if (environment.acceptsProfiles("dev")) {
      log.debug("Initializing dev S3 client with access key and secret key");

      String s3AccessKey = environment.getProperty("app.file-storage.s3-access-key");
      String s3SecretKey = environment.getProperty("app.file-storage.s3-secret-key");

      Assert.hasText(s3AccessKey, "Property `app.file-storage.s3-access-key` must not be blank");
      Assert.hasText(s3SecretKey, "Property `app.file-storage.s3-secret-key` must not be blank");

      BasicAWSCredentials awsCredentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
      AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

      AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
      builder.setRegion(s3Region);
      builder.withCredentials(credentialsProvider);
      return builder.build();
    } else {
      log.debug("Initializing default S3 client using AIM role");
      return AmazonS3ClientBuilder.standard()
        .withCredentials(new InstanceProfileCredentialsProvider(false))
        .withRegion(s3Region)
        .build();
    }
  }
}
