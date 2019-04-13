package sample.pojo.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import sample.pojo.data.RequestInfo;
import sample.pojo.data.RequestResult;
import sample.pojo.data.UserTest;
import sample.pojo.persistence.BankMapper;
import sample.pojo.service.BankService;

public class SampleTest extends BaseTest {
	
	@Resource
	private BankService bankService;
	
	@Resource
	private BankMapper bankMapper;
	
	
	@Test
	public void testOracle() {
		RequestResult re = new RequestResult();
		List<RequestResult> list = bankMapper.selectRequestResult("1");
		System.out.println(JSONObject.toJSONString(list));
	}
	
	public void testInsert() {
		RequestInfo info = new RequestInfo();
		info.setRqDate("111");
		info.setRqTime("2222");
		info.setAllnum(10);
		info.setQuerynumber("testquerynumber");
		info.setVchkcode("testvchkcode");
		bankMapper.insertRequestInfo(info);
	}
	
	@Test
	public void acceptRequestList() {
		List<UserTest> list = new ArrayList<>();
		bankService.acceptRequestList("1");
	}
	
	@Test
	public void acceptResponseList() {
		List<UserTest> list = new ArrayList<>();
		bankService.acceptResponseList("1");
	}
}
