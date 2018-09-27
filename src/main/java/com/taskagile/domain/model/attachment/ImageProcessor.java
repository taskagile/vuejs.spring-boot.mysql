package com.taskagile.domain.model.attachment;

import com.taskagile.utils.Size;
import org.apache.commons.lang3.math.NumberUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;
import org.im4java.process.ArrayListOutputConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

@Component
public class ImageProcessor {

  private String commandSearchPath;

  public ImageProcessor(@Value("${app.image.command-search-path}") String commandSearchPath) {
    this.commandSearchPath = commandSearchPath;
  }

  public void resize(String sourceFilePath, String targetFilePath, Size resizeTo) throws Exception {
    Assert.isTrue(resizeTo.getHeight() > 0, "Resize height must be greater than 0");
    Assert.isTrue(resizeTo.getWidth() > 0, "Resize width must be greater than 0");

    ConvertCmd cmd = new ConvertCmd(true);
    cmd.setSearchPath(commandSearchPath);
    IMOperation op = new IMOperation();
    op.addImage(sourceFilePath);
    op.quality(70d);
    op.resize(resizeTo.getWidth(), resizeTo.getHeight());
    op.addImage(targetFilePath);
    cmd.run(op);
  }

  public Size getSize(String imagePath) throws IOException {
    try {
      ImageCommand cmd = new ImageCommand();
      cmd.setCommand("gm", "identify");
      cmd.setSearchPath(commandSearchPath);

      ArrayListOutputConsumer outputConsumer = new ArrayListOutputConsumer();
      cmd.setOutputConsumer(outputConsumer);

      IMOperation op = new IMOperation();
      op.format("%w,%h");
      op.addImage(imagePath);
      cmd.run(op);

      List<String> cmdOutput = outputConsumer.getOutput();
      String result = cmdOutput.get(0);
      Assert.hasText(result, "Result of command `gm identify` must not be blank");

      String[] dimensions = result.split(",");
      return new Size(NumberUtils.toInt(dimensions[0]), NumberUtils.toInt(dimensions[1]));
    } catch (Exception e) {
      throw new IOException("Failed to get image's height/width", e);
    }
  }
}
