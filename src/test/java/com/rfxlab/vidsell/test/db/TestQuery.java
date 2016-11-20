package com.rfxlab.vidsell.test.db;

import org.springframework.context.ApplicationContext;

import com.rfxlab.util.SpringContextUtil;
import com.rfxlab.vidsell.dao.DbUserDao;

public class TestQuery {
	
	
	
	
	public static void main(String[] args) {
		ApplicationContext context = SpringContextUtil.getContext();
		DbUserDao dbUserDao = context.getBean(DbUserDao.class);		
		System.out.println(dbUserDao.getIdByEmail("9369LvP0nethinhthy777@example.com"));		
		
	}

}
