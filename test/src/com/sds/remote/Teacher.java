package com.sds.remote;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.sun.image.codec.jpeg.*;

public class Teacher extends JFrame {
  public static final int PORT = 5555;												//�룷�듃 踰덊샇
  private static final long SCREEN_SHOT_PERIOD = 2000;			//�뒪�겕由곗꺑 李띾뒗 二쇨린
  private static final int WINDOW_HEIGHT = 400;						//�쐢�룄�슦李� �꼻�씠
  private static final int WINDOW_WIDTH = 500;							//�쐢�룄�슦李� �넂�씠

  private final ObjectInputStream in;													//媛앹껜inputstream
  private final ObjectOutputStream out;											//媛앹껜outputstream
  private final String studentName;											
  private final JLabel iconLabel = new JLabel();
  private final RobotActionQueue jobs = new RobotActionQueue();	//RobotActionQueue -> RobotAction�씠 �닔�뻾�빐�빞�븷 �봽濡쒖꽭�뒪�뱾�쓽 ���옣�냼
  private final Thread writer;
  private final Timer timer;
  private volatile boolean running = true;

  public Teacher(Socket socket) throws IOException, ClassNotFoundException {//�꽑�깮�떂 �솕硫� �깮�꽦�옄 with Socket
    out = new ObjectOutputStream(
    		socket.getOutputStream());		//�냼罹ｌ쑝濡� 媛앹껜瑜� stream�쑝濡� 蹂대궡�뒗 ObjectOutputStream 媛앹껜 蹂��닔
    
    in = new ObjectInputStream(
    		new BufferedInputStream(socket.getInputStream()));	//�냼罹ｌ쑝濡쒕��꽣 媛앹껜瑜� stream�쑝濡� 諛쏆븘�삤�뒗 ObjectInputStream 媛앹껜 蹂��닔
    
    studentName = (String) in.readObject();	//in 媛앹껜蹂��닔濡� 遺��꽣 諛쏆� Object瑜쇱씫�뼱���꽌 �뒪�듃留� �삎�깭濡� 蹂��솚 �썑 studentName 蹂��닔�뿉 ���옣
    
    setupUI();		//setupUI 硫붿꽌�뱶 �떎�뻾

    createReaderThread();		//createReaderThread 硫붿꽌�뱶 �떎�뻾
    
    timer = createScreenShotThread();		//timer�뿉 createScreenShotThread()�뿉�꽌 諛섑솚�맂 timer瑜� ���엯
    writer = createWriterThread();				//writer�뿉 createWriterThread()�뿉�꽌 諛섑솚�맂 writer thread瑜� ���엯
    
    addWindowListener(new WindowAdapter() {		//window �떕�쓣 �븣 timer 痍⑥냼
      public void windowClosing(WindowEvent e) {
        timer.cancel();
      }
    });
    addWindowListener(new WindowAdapter() {	//windowClosing 硫붿꽌�뱶瑜� �넻�빐 out, in�쓣 �떕�븘以�
      public void windowClosing(WindowEvent e) {
        try {
          out.close();
        } catch (IOException ex) { }
        try {
          in.close();
        } catch (IOException ex) { }
      }
    });
    System.out.println("finished connecting to " + socket);
  }

  private Timer createScreenShotThread() {	// Timer�삎�깭瑜� 諛섑솚�븯�뒗 �뒪�겕由곗꺑 thread 留ㅼ꽌�뱶
    Timer timer = new Timer();			//Timer媛앹껜 �깮�꽦
    timer.schedule(new TimerTask() {		//timer�뿉 �뒪耳�伊� 議곌굔 �엯�젰 public void schedule(TimerTask task, long delay, long period)
    															//																	TimerTask�삎 , �뵜�젅�씠 �떆媛� 1, �뒪�겕由곗꺑 二쇨린 2000
      public void run() {
        jobs.add(new ScreenShot());
      }
    }, 1, SCREEN_SHOT_PERIOD);
    return timer;					//timer諛섑솚
  }

  private void setupUI() {								// UI�깮�꽦 硫붿꽌�뱶
    setTitle("Screen from " + studentName);
    getContentPane().add(new JScrollPane(iconLabel));		//iconLabel(JLabel)�뿉 JScrollPane�쓣 遺숈씠怨� ContentPane�뿉 �꽔湲�
    iconLabel.addMouseListener(new MouseAdapter() {		//iconLabel�뿉 留덉슦�뒪 由ъ뒪�꼫 遺숈씠湲�(MouseAdapter�궗�슜)
      public void mouseClicked(MouseEvent e) {
        if (running) {										//running 蹂��닔媛� true�씠硫� jobs�뿉 留덉슦�뒪 �씠踰ㅽ듃�� �뒪�겕由곗꺑 �깮�꽦留ㅼ꽌�뱶 �꽔湲�, jobs�뒗 RobotActionQueue
          jobs.add(new MoveMouse(e));
          jobs.add(new ClickMouse(e));
          jobs.add(new ScreenShot());
        } else {
          Toolkit.getDefaultToolkit().beep();		//running�씠 false�씠硫� beep留ㅼ꽌�뱶 �샇異�
        }
      }
    });
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setVisible(true);
  }

  private Thread createWriterThread() {			//Thread�삎�깭瑜� 諛섑솚�븯�뒗 createWriterThread 留ㅼ꽌�뱶
    Thread writer = new Thread("Writer") {		//Thread�뿉 Writer媛앹껜 �궫�엯, Writer�뒗 異붿긽硫붿꽌�뱶, char�삎�깭 stream
      public void run() {
        try {
          while (true) {
            RobotAction action = jobs.next();			//jobs(RobotActionQueue)�쓽 �떎�쓬 �옉�뾽�쓣 RobotAction媛앹껜 蹂��닔�씤 action�뿉 ���엯
            out.writeObject(action);						//out(ObjectOutputStream)�뿉 action�쓣 �엯�젰
            out.flush();											//out�뿉 �궓�븘�엳�뒗 �뜲�씠�꽣瑜� 媛뺤젣濡� �궡蹂대깂
          }
        } catch (Exception e) {
          System.out.println("Connection to " + studentName +
              " closed (" + e + ')');
          setTitle(getTitle() + " - disconnected");
        }
      }
    };
    writer.start();											//writer thread �떎�뻾
    return writer;											//writer 諛섑솚
  }

  private void showIcon(byte[] byteImage) throws IOException {				//student濡쒕��꽣 諛쏆븘�삩 �씠誘몄� �뜲�씠�꽣瑜� �궗吏꾩쑝濡� 蹂��솕�떆�궎�뒗 留ㅼ냼�뱶
    ByteArrayInputStream bin = new ByteArrayInputStream(byteImage);		//byte諛곗뿴 �삎�깭濡� �꽆�뼱�삩 byteImage(利� �궗吏� �뜲�씠�꽣)瑜� 
    																												//ByteArrayInputStream媛앹껜蹂��닔 bin�뿉 �엯�젰
    
    JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(bin);			//bin�뿉 �엳�뒗 �뜲�씠�꽣瑜� JPEG �삎�깭濡� 蹂��솚�븯�뿬 decoder�뿉 ���엯			
    final BufferedImage img = decoder.decodeAsBufferedImage();					//decoder�뿉 �엳�뒗 �뜲�씠�꽣瑜� decode�빐二쇰뒗 
    																												//deocodeAsBufferedImage()瑜� �씠�슜�븯�뿬 img�뿉 �엯�젰
    
    SwingUtilities.invokeLater(new Runnable() {			//invokeLater ->�쁽�옱 �쐢�룄�슦(JFrame)瑜� 理쒖떊�쑝濡� 媛깆떊�떆耳쒖＜�뒗 留ㅼ꽌�뱶									
      public void run() {												//Runnable�쓣 �넻�빐 img瑜� iconLabel�뿉 �꽔�뼱以�
        iconLabel.setIcon(new ImageIcon(img));
      }
    });
  }

  private void createReaderThread() {					//Teacher媛앹껜媛� Student濡쒕��꽣 �궗吏꾩쓣 諛쏆븘�삤�뒗 createReaderThread 硫붿꽌�뱶
    Thread readThread = new Thread() {
      public void run() {
        while (true) {						
          try {
            byte[] img = (byte[]) in.readObject();			//byte�삎 諛곗뿴 �꽑�뼵 �썑 in�뿉 �엳�뒗 �뜲�씠�꽣瑜� �씫�뼱�뱾�엫
            System.out.println("Received screenshot of " +
                img.length + " bytes from " + studentName);
            showIcon(img);											//showIcon留ㅼ꽌�뱶�뿉 img瑜� �꽔�쓬
          } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex);
            writer.interrupt();											//thread媛� 硫덉텛硫� writer, timer 硫덉텛湲�
            timer.cancel();
            running = false;											//iconLabel�뿉 beep()�쓣 �빐二쇨린 �쐞�빐 running�쓣 false濡� 諛붽퓭以�
            return;
          }
        }
      }
    };
    readThread.start();
  }

  public static void main(String[] args) throws Exception {
    ServerSocket ss = new ServerSocket(PORT);			//�넻�떊 �꽌踰꾩냼罹� �룷�듃 �뿴湲� -> Student媛� �젒�냽�빐 �삤�뒗 怨�. 利� ServerSocket�� Student媛� 二쇱껜
    while (true) {
      Socket socket = ss.accept();								//ss瑜� 諛쏆븘�뱾�씠�뒗 留ㅼ꽌�뱶 accept(), ss�쓽 �젙蹂대�� Socket(二쇱껜�뒗 Teacher)媛앹껜 socket�뿉 �꽔怨�
      System.out.println("Connection From " + socket);
      new Teacher(socket);											//Teacher �깮�꽦
    }
  }
}
