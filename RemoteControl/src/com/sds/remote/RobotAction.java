package com.sds.remote;

import java.awt.*;
import java.io.*;

public interface RobotAction extends Serializable {
  Object execute(Robot robot) throws IOException;
}
  
