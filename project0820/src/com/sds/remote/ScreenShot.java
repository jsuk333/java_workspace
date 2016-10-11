package com.sds.remote;





import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ScreenShot implements RobotAction {
  public Object execute(Robot robot) throws IOException {
    Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
    Rectangle shotArea = new Rectangle(
        defaultToolkit.getScreenSize());
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	ImageIO.write(robot.createScreenCapture(shotArea), "jpg", bout);
    return bout.toByteArray();
  }
  public String toString() {
    return "ScreenShot";
  }
}
