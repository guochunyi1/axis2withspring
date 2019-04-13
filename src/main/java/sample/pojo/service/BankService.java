package sample.pojo.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sample.pojo.data.RequestResult;

@Service
public class BankService {

	private final static String XMLHEAD = "<?xml version=\"1.0\" encoding=\"gbk\"?>";

	@Resource
	private BankQueryService bankQueryService;

	// 1.模拟银行接受民政请求查询人员列表
	public String acceptRequestList(String requestList) {
		boolean isXml = StringToXML.validateXml(requestList);
		String returnFlag = "100";
		if (isXml) {
			String queryNumber = UUID.randomUUID().toString().replace("-", "");
			returnFlag = bankQueryService.processXml(requestList, queryNumber);
			if ("400".equals(returnFlag)) {
				return return400Failed();
			}
			return bankQueryService.getSuccessReq(returnFlag, queryNumber);
		} else {
			return return400Failed();
		}
	}

	private String return400Failed() {
		String returnFlag = "400";
		String errorInfo = "报文格式不正确";
		return bankQueryService.getFailedReq(returnFlag, errorInfo);
	}

	// 2.模拟银行返回查询结果
	public String acceptResponseList(String serialNum) {
		boolean isXml = StringToXML.validateXml(serialNum);
		if (!isXml) {
			return return400Failed();
		}
		StringBuffer bufhead = new StringBuffer();
		StringBuffer bufbody = new StringBuffer();
		StringBuffer mes = new StringBuffer();
		List<RequestResult> list = bankQueryService.getRequestResult(serialNum);
		Integer num = list.size();
		// 模拟银行未查询到数据
		if (num == 0) {
			// 组装报文头<HEAD></HEAD>
			StringToXML.putToXMLChild(bufhead, "RETURNFLAG", "300");
			StringToXML.putToXMLChild(bufhead, "ALLNUM", "0");
			StringToXML.putToXMLChild(bufhead, "ERRORINFO", "");
			StringToXML.putToXMLFather(bufhead, "HEAD");
			// 组装报文体INFO中的标签
			StringToXML.putToXMLChild(bufbody, "SERIALNUM", "");
			StringToXML.putToXMLChild(bufbody, "UUID", "");
			StringToXML.putToXMLChild(bufbody, "VCHKCODE", "");
			StringToXML.putToXMLChild(bufbody, "IDCARDNUM", "");
			StringToXML.putToXMLChild(bufbody, "VNAME", "");
			StringToXML.putToXMLChild(bufbody, "BANKCODE", "");
			StringToXML.putToXMLChild(bufbody, "BANKNAME", "");
			StringToXML.putToXMLChild(bufbody, "CARDNO", "");
			StringToXML.putToXMLChild(bufbody, "ACCOUNTTYPE", "");
			StringToXML.putToXMLChild(bufbody, "ACCOUNTSTATUS", "");
			StringToXML.putToXMLChild(bufbody, "ACCOUNTROWNO", "");
			StringToXML.putToXMLChild(bufbody, "CURRENTBALANCE", "");
			StringToXML.putToXMLChild(bufbody, "LASTQUARTERBALANCE", "");
			StringToXML.putToXMLChild(bufbody, "LASTTWOQUARTERBALANCE", "");
			StringToXML.putToXMLChild(bufbody, "CURRENCY", "");
			StringToXML.putToXMLChild(bufbody, "FINANCIALBALANCE", "");
			StringToXML.putToXMLChild(bufbody, "FINANCIALSHARES", "");
			StringToXML.putToXMLChild(bufbody, "FINANCIALMARKETVALUE", "");
			StringToXML.putToXMLChild(bufbody, "QUERYDATE", "");
			// 添加到INFO标签
			StringToXML.putToXMLFather(bufbody, "INFO");

			// 添加到BODY标签
			StringToXML.putToXMLFather(bufbody, "BODY");

			// 将bufhead,bufbody合并后增加<MSG></MSG>标签中
			mes = bufhead.append(bufbody);
			StringToXML.putToXMLFather(mes, "MSG");
		} else {
			// 模拟银行返回数据
			// 组装报文头<HEAD></HEAD>

			StringToXML.putToXMLChild(bufhead, "RETURNFLAG", "200");
			StringToXML.putToXMLChild(bufhead, "ALLNUM", num.toString());
			StringToXML.putToXMLChild(bufhead, "ERRORINFO", "");
			StringToXML.putToXMLFather(bufhead, "HEAD");

			StringBuffer buf = new StringBuffer();
			for (RequestResult result : list) {
				// 组装报文体INFO中的标签
				StringToXML.putToXMLChild(buf, "SERIALNUM", result.getSerialnum());
				StringToXML.putToXMLChild(buf, "UUID", result.getUuid());
				StringToXML.putToXMLChild(buf, "VCHKCODE", result.getVchkcode());
				StringToXML.putToXMLChild(buf, "IDCARDNUM", result.getIdcardnum());
				StringToXML.putToXMLChild(buf, "VNAME", result.getVname());
				StringToXML.putToXMLChild(buf, "BANKCODE", result.getBankcode());
				StringToXML.putToXMLChild(buf, "BANKNAME", result.getBankname());
				StringToXML.putToXMLChild(buf, "CARDNO", result.getCardno());
				StringToXML.putToXMLChild(buf, "ACCOUNTTYPE", result.getAccounttype());
				StringToXML.putToXMLChild(buf, "ACCOUNTSTATUS", result.getAccountstatus());
				StringToXML.putToXMLChild(buf, "ACCOUNTROWNO", result.getAccountrowno());
				StringToXML.putToXMLChild(buf, "CURRENTBALANCE", result.getCurrentbalance() == null ? "" : result.getCurrentbalance().toString());
				StringToXML.putToXMLChild(buf, "LASTQUARTERBALANCE", result.getLastquarterbalance() == null ? "" : result.getLastquarterbalance().toString());
				StringToXML.putToXMLChild(buf, "LASTTWOQUARTERBALANCE", result.getLasttwoquarterbalance() == null ? "" : result.getLasttwoquarterbalance().toString());
				StringToXML.putToXMLChild(buf, "CURRENCY", result.getCurrency());
				StringToXML.putToXMLChild(buf, "FINANCIALBALANCE", result.getFinancialbalance() == null ? "" : result.getFinancialbalance().toString());
				StringToXML.putToXMLChild(buf, "FINANCIALSHARES", result.getFinancialshares() == null ? "" : result.getFinancialshares().toString());
				StringToXML.putToXMLChild(buf, "FINANCIALMARKETVALUE", result.getFinancialmarketvalue() == null ? "" : result.getFinancialmarketvalue().toString());
				StringToXML.putToXMLChild(buf, "QUERYDATE", result.getQuerydate() == null ? "" : result.getQuerydate().toString());
				// 添加到INFO标签
				StringToXML.putToXMLFather(buf, "INFO");
				bufbody.append(buf);
				// 清空缓存buf
				buf.delete(0, buf.length());
			}

			// 添加到BODY标签
			StringToXML.putToXMLFather(bufbody, "BODY");

			// 将bufhead,bufbody合并后增加<MSG></MSG>标签中
			mes = bufhead.append(bufbody);
			StringToXML.putToXMLFather(mes, "MSG");
		}

		return XMLHEAD + mes.toString();
	}

}