package sample.pojo.persistence;

import java.util.List;

import sample.pojo.data.RequestInfo;
import sample.pojo.data.RequestList;
import sample.pojo.data.RequestResult;
import sample.pojo.data.UserTest;

public interface BankMapper {
	
	public List<UserTest> findUserTest();
	public void insertRequestResult(RequestResult result);
	
	public void insertRequestInfo(RequestInfo info);
	public void insertRequestList(List<RequestList> list);
	
	public List<RequestResult> selectRequestResult(String querynumber);

}
