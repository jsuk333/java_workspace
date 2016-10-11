package com.sds.remote;



import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Student extends Thread{
  private final ObjectOutputStream out;
  private final ObjectInputStream in;
  private final Robot robot;
  RobotAction action;
  public Student()
      throws IOException, AWTException {
	  
    Socket socket = new Socket("localhost", 5555);
    robot = new Robot();
    out = new ObjectOutputStream(socket.getOutputStream());
    in = new ObjectInputStream(
      new BufferedInputStream(socket.getInputStream()));
    out.writeObject("me");
    out.flush();
	start();
  }

  public void run() {
    try {
      while (true) {
		try {
			action = (RobotAction) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Object result = action.execute(robot);
        if (result != null) {
			System.out.println(result.toString());
          out.writeObject(result);
          out.flush();
          out.reset();
        }
      }
    } catch (IOException ex) {
      System.out.println("Connection closed");
    }
  }

  public static void main(String[] args) throws Exception {
    Student student = new Student();
  }
}