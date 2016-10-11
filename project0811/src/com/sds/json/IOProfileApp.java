package com.sds.json;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IOProfileApp extends JFrame{
	JButton bt;
	JPanel p_west,p_cen;
	JFileChooser chooser;
	FileReader reader;
	BufferedReader buffr;
	StringBuffer sb;
	Member m;
	JScrollPane scroll;
	public IOProfileApp() {
		JButton bt=new JButton("버튼");//서쪽 시작
		p_west=new JPanel();//버튼 패널
		p_cen=new JPanel();//이미지 나오는 곳
		chooser=new JFileChooser();//파일 찾기
		
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
				p_cen.updateUI();
			}
		});
		
		scroll=new JScrollPane(p_cen);//스크롤에 들어간다.
		setBackground(Color.CYAN);
		p_cen.setPreferredSize(new Dimension(800, 800));
		p_cen.setLayout(new FlowLayout());
		
		p_west.add(bt);
		
		
		add(p_west,BorderLayout.WEST);
		add(scroll,BorderLayout.CENTER);
		
		setSize(new Dimension(1000, 1000));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		
	}
	
	public void openFile(){
		
		if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			File file=chooser.getSelectedFile();
			try {
				reader=new FileReader(file);
				buffr=new BufferedReader(reader);
				sb=new StringBuffer();
				String data=null;
				while((data=buffr.readLine())!=null){
					sb.append(data);
				}
				JSONParser jsonParser=new JSONParser();
				JSONObject jsonObject=(JSONObject) jsonParser.parse(sb.toString());
				JSONArray jsonArray=(JSONArray)jsonObject.get("members");
				for(int i=0;i<jsonArray.size();i++){
					JSONObject member=(JSONObject)jsonArray.get(i);
					String name=(String)member.get("name");
					Long age=(Long)member.get("age");
					String gender=(String)member.get("gender");
					String path=(String)member.get("photo");
					m=new Member(path);
					p_cen.add(m);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new IOProfileApp();
	}

}
