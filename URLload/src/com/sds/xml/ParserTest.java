/*아  기종간 (시스템이 틀린) 데이터 교환시 사요용하는
 * 고전적 텍스트 기반 데이터 베이스인 xml을 자바에서 사용해 본다.
 * 
 * 일반적인 프로그램에서 파싱하는 방법은 크게 2가지가 있다.
 * 
 * 1. DOM 문서를  객체화 해서 시킨다.
 * 		장점 객체화 시켜 제어할 수 있다. 객체 지향 언어방식으로 프로그래밍이 가능하다.
 * 		단점 메모리를 많이 사용하므로 특히 모바일 기기에서 불리하다.
 * 2. SAX 각 실행부가 각 태그를 발견 할때마다 적절한 이벤트가 발생한다. 개발자는 이 이벤트 타이밍을 잡아내서 원하는 처리를
 * 해주어야 한다.
 * 		장점 메모리를 덜먹고 속도가 빠르다.
 * 		단점 프로그래밍이 까다롭다.
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
	//파서가 추상이므로 직접 생성할 수 없고 아래 팩토리로 인스턴스 획득
	SAXParserFactory factory;
	
	
	public ParserTest() {
		factory=SAXParserFactory.newInstance();
		try {
			SAXParser parser=factory.newSAXParser();
			//어떤 파일을 해석할지 이벤트 처리자는 누구인지 지정하자
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
