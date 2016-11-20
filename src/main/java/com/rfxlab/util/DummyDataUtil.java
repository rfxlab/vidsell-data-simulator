package com.rfxlab.util;

import java.sql.Timestamp;
import java.util.Date;

public class DummyDataUtil {
	
	private static final String BEGIN_DATE = "2016-10-18 00:00:00";
	private static final String END_DATE = "2015-01-01 00:00:00";
	
	public static Date getRamdomizedDate(String beginDate, String endDate){
		long offset = Timestamp.valueOf(beginDate).getTime();
		long end = Timestamp.valueOf(endDate).getTime();
		long diff = end - offset + 1;
		Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
	   
		return new Date(rand.getTime());
	}

	public static Date getRamdomizedDate(){
		return getRamdomizedDate(BEGIN_DATE, END_DATE);
	}
	
	public static String getRamdomLocation(){
		String loc = "Ho Chi Minh City";
		//TODO
		return loc;
	}

}
