package com.rfxlab.vidsell.test.db;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.rfxlab.model.db.DbProduct;
import com.rfxlab.model.db.DbSellingOrder;
import com.rfxlab.util.SpringContextUtil;
import com.rfxlab.vidsell.dao.DbProductDao;
import com.rfxlab.vidsell.dao.DbSellingOrderDao;
import com.rfxlab.vidsell.dao.DbUserDao;

import rfx.core.util.RandomUtil;

public class JobGenerateSellOrder {
	
	static int MAX_SIMULATED_ORDER = 1000;
	
	public static void main(String[] args) {
		ApplicationContext context = SpringContextUtil.getContext();
		DbUserDao dbUserDao = context.getBean(DbUserDao.class);
		DbProductDao dbProductDao = context.getBean(DbProductDao.class);
		DbSellingOrderDao dbSellingOrderDao = context.getBean(DbSellingOrderDao.class);
		
//		List<DbSellingOrder> orders = new ArrayList<>(MAX_SIMULATED_ORDER);
		for (int i = 0; i < MAX_SIMULATED_ORDER; i++) {
			System.out.println("order: "+ i);
			long offset = Timestamp.valueOf("2016-10-01 00:00:00").getTime();
			long end = Timestamp.valueOf("2016-11-08 00:00:00").getTime();
			long diff = end - offset + 1;
			Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));	   
			Date orderDate =  new Date(rand.getTime());
			
			DbProduct product = dbProductDao.getRandomProduct();
			
			saveOrder(dbUserDao, dbSellingOrderDao, orderDate, product);				
		}
		
		System.out.println("DONE");
		
	}

	protected static void saveOrder(DbUserDao dbUserDao, DbSellingOrderDao dbSellingOrderDao, Date orderDate,
			DbProduct product) {
		DbSellingOrder order = new DbSellingOrder();
		order.setCustomerId(dbUserDao.getRandomUser().getId());
		order.setProductId(product.getProduct_id());
		order.setOrderDate(orderDate);
		
		int q = RandomUtil.getRandomInteger(3, 1);
		int revenue = q * product.getPrice();
		float discount = RandomUtil.getRandomInteger(10, 0) / 100f;
		order.setQuantity(q);
		order.setRevenue(revenue);
		order.setDiscount(discount);
//			orders.add(order);
		dbSellingOrderDao.save(order);
	}

}
