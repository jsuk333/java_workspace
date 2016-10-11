package com.sds.json;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProfileApp extends JFrame{
	BufferedReader buffr;
	FileReader reader;
	String path="C:/java_workspace/project0811/res/member.json";
	public ProfileApp() {
		// TODO Auto-generated constructor stub
		try {
			reader=new FileReader(path);
			buffr=new BufferedReader(reader);
			String data=null;
			StringBuffer sb=new StringBuffer();
			while((data=buffr.readLine())!=null){
				//System.out.println(data);
				sb.append(data);
			}
			//파싱 시작
			JSONParser jsonParser=new JSONParser();
			JSONObject jsonObject=(JSONObject)jsonParser.parse(sb.toString());
			
			JSONArray jsonArray=(JSONArray)jsonObject.get("members");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject member=(JSONObject)jsonArray.get(i);
				System.out.println(member.get("name")+","+member.get("gender")+","+member.get("age")+","+member.get("photo"));
				
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
			System.out.println("제이슨 문법 좀 공부해");
		}finally{
			
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ProfileApp();
	}

}
