package sample.pojo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import sample.pojo.data.RequestInfo;
import sample.pojo.data.RequestList;
import sample.pojo.data.RequestResult;
import sample.pojo.persistence.BankMapper;

@Service
public class BankQueryService {
	
	@Resource
	private BankMapper bankMapper;
	
	public String processXml(String xml, String queryNumber) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			doc.setXMLEncoding("gbk");
			Element msg = doc.getRootElement();
			Iterator head = msg.elementIterator("HEAD");
			RequestInfo ri = new RequestInfo();
			ri.setQuerynumber(queryNumber);
			while(head.hasNext()) {
				Element recordEle = (Element) head.next();
				String allnum = recordEle.elementTextTrim("ALLNUM");
				ri.setAllnum(Integer.parseInt(allnum));
			}
			if(ri.getAllnum() == 0) {
				return "400";
			}
			Iterator body = msg.elementIterator("BODY");
			List<RequestList> rlist = new ArrayList<>();
			while(body.hasNext()) {
				Element infos = (Element)body.next();
				Iterator info = infos.elementIterator("INFO");
				while(info.hasNext()) {
					Element recordEle = (Element)info.next();
					RequestList r = new RequestList();
					r.setIdcardnum(recordEle.elementTextTrim("IDCARDNUM"));
					r.setUuid(recordEle.elementTextTrim("UUID"));
					r.setVname(recordEle.elementTextTrim("VNAME"));
					r.setVchkcode(recordEle.elementTextTrim("VCHKCODE"));
					r.setQuerynumber(queryNumber);
					rlist.add(r);
				}
			}
			if(ri.getAllnum() != rlist.size()) {
				return "400";
			}
			String date = DateUtil.date2String();
			ri.setRqDate(date.substring(0, 8));
			ri.setRqTime(date.substring(8));
			ri.setVchkcode(rlist.get(0).getVchkcode());
			bankMapper.insertRequestInfo(ri);
			bankMapper.insertRequestList(rlist);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			return "400";
		}
	}


	public String getSuccessReq(String returnFlag, String queryNumber) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("MSG");
		Element head = root.addElement("HEAD");
		Element returnflag = head.addElement("RETURNFLAG");
		returnflag.setText(returnFlag);
		Element info = root.addElement("BODY").addElement("INFO");
		Element querynumber = info.addElement("QUERYNUMBER");
		querynumber.setText(queryNumber);
		System.out.println(document.asXML());
		return document.asXML();
	}
	
	public String getFailedReq(String returnFlag, String errorInfo) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("MSG");
		Element head = root.addElement("HEAD");
		Element returnflag = head.addElement("RETURNFLAG");
		returnflag.setText(returnFlag);
		Element errorinfo = head.addElement("ERRORINFO");
		errorinfo.setText(errorInfo);
		Element info = root.addElement("BODY").addElement("INFO");
		System.out.println(document.asXML());
		return document.asXML();
	}
	
	public List<RequestResult> getRequestResult(String queryString) {
		String querynumber = getQuerynumber(queryString);
		return bankMapper.selectRequestResult(querynumber);
	}
	
	private String getQuerynumber(String queryString) {
		try {
			Document doc = DocumentHelper.parseText(queryString);
			doc.setXMLEncoding("gbk");
			Element msg = doc.getRootElement();
			Iterator body = msg.elementIterator("BODY");
			while(body.hasNext()) {
				Element recordEle = (Element) body.next();
				return recordEle.elementTextTrim("QUERYNUMBER");
			}
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
}