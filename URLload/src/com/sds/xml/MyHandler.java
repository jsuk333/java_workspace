/*sax�Ľ� ����� �� �±� ��Ҹ� ��� �޸𸮿��ø��� ������ �ƴ϶�, �� �±׿�Ұ� �߰��Ҷ����٤�. �����ڿ����� �̺�Ʈ�� ����
 * �����Ѵ�. ���� �޸𸮿� �ö�˴� ����ü�� ���� ����.
 * ���� �����ڰ� �� �Ԥ�Ʈ Ÿ�̹�����ġ�� �ʾƾ� �Ѵ�.*/
package com.sds.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler{
	
	String[] member;
	

	public void startDocument() throws SAXException {
		System.out.println("������ ���ۉ�׿�");
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
