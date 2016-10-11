package com.sds.table;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler{
	MyModel model;
	String[] data;
	int count=0;
	boolean name;
	boolean age;
	boolean gender;
	public MyHandler(MyModel model) {
		this.model=model;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("member")){
			data=new String[3];
		}else if(qName.equals("name")){
			name=true;
		}else if(qName.equals("age")){
			age=true;
		}else if(qName.equals("gender")){
			gender=true;
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str=new String(ch,start,length);
		if(name){
			data[0]=str;
		}else if(age){
			data[1]=str;
		}else if(gender){
			data[2]=str;
		}
		
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("member")){
			model.list.add(data);
		}else if(qName.equals("name")){
			name=false;
		}else if(qName.equals("age")){
			age=false;
		}else if(qName.equals("gender")){
			gender=false;
		}
	}
	
}
