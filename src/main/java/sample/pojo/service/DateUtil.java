package sample.pojo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String date2String() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
}
