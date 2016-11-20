package com.rfxlab.vidsell.test.db;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.ApplicationContext;

import com.rfxlab.model.db.DbUser;
import com.rfxlab.util.SpringContextUtil;
import com.rfxlab.vidsell.dao.DbUserDao;

import rfx.core.util.RandomUtil;
import rfx.core.util.StringUtil;

public class JobSaveDbUser {
	
	static String userdata = "/home/trieu/data/sample-user-data.csv";
	
	
	public static void main(String[] args) {
		ApplicationContext context = SpringContextUtil.getContext();
		DbUserDao dbUserDao = context.getBean(DbUserDao.class);		
		//System.out.println(dbUserDao.save(new DbUser("aaa")));
		
		try {
			Reader in = new FileReader(userdata);
			List<CSVRecord> records = CSVFormat.EXCEL.parse(in).getRecords();
			List<DbUser> users = new ArrayList<>(records.size());
			for (CSVRecord record : records) {				
				long offset = Timestamp.valueOf("2016-10-18 00:00:00").getTime();
				long end = Timestamp.valueOf("2015-01-01 00:00:00").getTime();
				long diff = end - offset + 1;
				Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
			   
				Date creation_date =  new Date(rand.getTime());
			    Date modification = creation_date;
			    String email = RandomUtil.getRamdomString(10)+record.get(2);
			    String name = RandomUtil.getRamdomString(4)+record.get(2).substring(0, +record.get(2).indexOf("@"));
			    email = email.substring(0, email.indexOf("@"))+"@example.com";
			    
			    int gender = record.get(3).equals("Male")?1:0;
			    int age = StringUtil.safeParseInt(record.get(4));
			    String location = record.get(5)+" - "+record.get(6);
			    
				DbUser u = new DbUser(name, email, age, gender, location, creation_date, modification);
			    System.out.println(u);
			    users.add(u);
			}
			dbUserDao.save(users);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
