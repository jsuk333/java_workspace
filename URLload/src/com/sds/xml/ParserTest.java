/*��  ������ (�ý����� Ʋ��) ������ ��ȯ�� �����ϴ�
 * ������ �ؽ�Ʈ ��� ������ ���̽��� xml�� �ڹٿ��� ����� ����.
 * 
 * �Ϲ����� ���α׷����� �Ľ��ϴ� ����� ũ�� 2������ �ִ�.
 * 
 * 1. DOM ������  ��üȭ �ؼ� ��Ų��.
 * 		���� ��üȭ ���� ������ �� �ִ�. ��ü ���� ��������� ���α׷����� �����ϴ�.
 * 		���� �޸𸮸� ���� ����ϹǷ� Ư�� ����� ��⿡�� �Ҹ��ϴ�.
 * 2. SAX �� ����ΰ� �� �±׸� �߰� �Ҷ����� ������ �̺�Ʈ�� �߻��Ѵ�. �����ڴ� �� �̺�Ʈ Ÿ�̹��� ��Ƴ��� ���ϴ� ó����
 * ���־�� �Ѵ�.
 * 		���� �޸𸮸� ���԰� �ӵ��� ������.
 * 		���� ���α׷����� ��ٷӴ�.
 * 		
 * 		
 * */
package com.sds.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ParserTest {
	//�ļ��� �߻��̹Ƿ� ���� ������ �� ���� �Ʒ� ���丮�� �ν��Ͻ� ȹ��
	SAXParserFactory factory;
	
	
	public ParserTest() {
		factory=SAXParserFactory.newInstance();
		try {
			SAXParser parser=factory.newSAXParser();
			//� ������ �ؼ����� �̺�Ʈ ó���ڴ� �������� ��������
			String path="C:/java_workspace/URLload/res/member.xml";
			File file=new File(path);
			MyHandler handler=new MyHandler();
			parser.parse(file, handler);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new ParserTest();

	}

}
