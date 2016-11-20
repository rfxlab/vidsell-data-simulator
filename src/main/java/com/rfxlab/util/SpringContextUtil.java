package com.rfxlab.util;

import java.io.File;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.UrlResource;

public class SpringContextUtil {
	static String path = "./configs/database-domain.xml"; 
	
	static ApplicationContext context = null;
	
	public static ApplicationContext getContext(){
		if(context == null){			
			try {
				File file = new File(path); 	
				if(file.isFile()){
					URL resourceUrl = new URL("file://"+file.getAbsolutePath());
					context = new GenericXmlApplicationContext(new UrlResource(resourceUrl));	
				} else {
					System.err.println("Not found Spring config"+path);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return context;
	}
}
