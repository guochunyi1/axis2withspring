package sample.pojo.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StringToXML {

	public static boolean validateXml(String xml) {
		try {
		    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		    InputStream stream = new ByteArrayInputStream(xml.getBytes("gbk"));
		    DefaultHandler handler = new DefaultHandler();
		    saxParser.parse(stream, handler);
		    return true;
		} catch (Exception e) {
		    // not valid XML String
			return false;
		}
	}

	public static void putToXMLChild(StringBuffer sb, String tag, String value) {
		if (value == null) {
			value = "";
		}
		if ((value.indexOf("\"") > -1) || (value.indexOf("'") > -1) || (value.indexOf(">") > -1)
				|| (value.indexOf("<") > -1) || (value.indexOf("&") > -1)) {
			sb.append("<" + tag + "><![CDATA[" + value + "]]></" + tag + ">");
		} else {
			sb.append("<" + tag + ">" + value + "</" + tag + ">");
		}
	}

	public static void putToXMLFather(StringBuffer sb, String tag) {
		sb.insert(0, "<" + tag + ">");
		sb.append("</" + tag + ">");
	}
}
