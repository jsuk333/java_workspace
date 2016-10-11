package com.sds.json;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class JsonProfile extends JFrame {
	JPanel p_west,p_center;
	JButton bt;
	JScrollPane scroll;
	JFileChooser chooser;
	FileReader reader;
	BufferedReader buffr;
	StringBuffer sb;
	public JsonProfile() {
		// TODO Auto-generated constructor stub
		bt=new JButton("찾기");
		p_west=new JPanel();
		p_center=new JPanel();
		scroll=new JScrollPane(p_center);
		chooser=new JFileChooser("C:/java_workspace/project0812/res");
		
		p_west.add(bt);
		p_west.setBackground(Color.pink);
		p_center.setBackground(Color.yellow);
		
		//createProfile();
		
		//아래거 안해주면 우측으로 한없이 붙는다.
		p_center.setPreferredSize(new Dimension(700, 600));
		
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openFile();
			}
		});
		
		add(p_west,BorderLayout.WEST);
		add(scroll,BorderLayout.CENTER);
		
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void createProfile(){
		//파싱 시작
		JSONParser jsonParser=new JSONParser();
		try {
			JSONObject jsonObject=(JSONObject) jsonParser.parse(sb.toString());
			JSONArray jsonArray=(JSONArray)jsonObject.get("members");
			System.out.println(jsonArray);
			for(int i=0;i<jsonArray.size();i++){
				JSONObject member=(JSONObject)jsonArray.get(i);
				String name=(String)member.get("name");
				String gender=(String)member.get("gender");
				String photo=(String)member.get("photo");
				
				Long age=(Long)member.get("age");
				
				Profile profile=new Profile(photo);
				profile.l_name.setText(name);
				profile.l_gender.setText(gender);
				profile.l_age.setText(""+age);
				p_center.add(profile);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p_center.updateUI();
	}
	public void openFile(){
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			//json 열기
			File file=chooser.getSelectedFile();
			try {
				reader=new FileReader(file);
				buffr=new BufferedReader(reader);
				String str=null;
				sb=new StringBuffer();
				while((str=buffr.readLine())!=null){
					sb.append(str);
				}
				createProfile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(buffr!=null){
					try {
						buffr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JsonProfile();

	}

}
