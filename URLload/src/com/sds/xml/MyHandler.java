/*sax파싱 방식은 각 태그 요소를 모두 메모리에올리는 형식이 아니라, 각 태그요소가 발견할때마다ㅏ. 개발자에가ㅏ 이벤트를 통해
 * 전달한다. 따라서 메모리에 올라옹느 ㄴ객체는 따로 없다.
 * 따라서 개발자가 이 입ㄴ트 타이밍을놓치지 않아야 한다.*/
package com.sds.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler{
	
	String[] member;
	

	public void startDocument() throws SAXException {
		System.out.println("문서가 시작됬네요");
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data=new String(ch,start,length);
		
	}
}
