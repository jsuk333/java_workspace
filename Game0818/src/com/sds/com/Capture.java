package com.sds.com;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class Capture extends Robot {
	Rectangle rect;
	public Capture() throws AWTException {
		super();
		
		
	}
	@Override
	public synchronized BufferedImage createScreenCapture(Rectangle screenRect) {
		// TODO Auto-generated method stub
		return super.createScreenCapture(screenRect);
	}

}
