package com.rfxlab.vidsell.bot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.google.common.hash.Hashing;
import com.ning.http.client.AsyncHttpClient;
import com.rfxlab.model.db.DbProduct;
import com.rfxlab.model.db.DbSellingOrder;
import com.rfxlab.model.db.DbVideo;
import com.rfxlab.util.BotHttpClientUtil;
import com.rfxlab.util.LocationDataUtil;
import com.rfxlab.util.SpringContextUtil;
import com.rfxlab.vidsell.dao.DbProductDao;
import com.rfxlab.vidsell.dao.DbSellingOrderDao;
import com.rfxlab.vidsell.dao.DbUserDao;
import com.rfxlab.vidsell.dao.DbVideoDao;

import rfx.core.util.RandomUtil;
import rfx.core.util.StringPool;
import rfx.core.util.StringUtil;
import rfx.core.util.Utils;


public class VidsellDataSimulatorBot {
	
	private static final String PAGEVIEW = "pageview";
	private static final String CLICK = "click";
	private static final String ORDER = "order";

	public static String buildLogQueryString(String metric, String uuid, long viewerId, 
			String location,String referrer, String url, String productId , String videoId, long orderId){
		StringBuilder queryStr = new StringBuilder("?");		
		try {
			queryStr.append("metric=").append(metric);
			queryStr.append("&uuid=").append(uuid);
			queryStr.append("&location=").append(location);
			queryStr.append("&referrer=").append(URLEncoder.encode(referrer, StringPool.UTF_8));
			queryStr.append("&url=").append(URLEncoder.encode(url, StringPool.UTF_8));
			queryStr.append("&product=").append(productId);
			queryStr.append("&video=").append(videoId);		
			if(metric.equals(ORDER) && orderId > 0){
				queryStr.append("&order=").append(orderId);	
			}
			if(viewerId > 0){
				queryStr.append("&viewer=").append(viewerId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}				
		return queryStr.toString();
	}
	
	static List<String> referrerSources = new ArrayList<>(10);
	static List<String> baseDomains = new ArrayList<>(10);
	
	static DbProductDao dbProductDao;
	static DbVideoDao dbVideoDao;
	static DbUserDao dbUserDao;
	static DbSellingOrderDao dbSellingOrderDao;
	
	static {
		//SoftKey Ace
		baseDomains.add("http://61.28.227.197:8080/hello");
		//The Stinger
		baseDomains.add("http://61.28.227.192/logs");
		//THPT
		baseDomains.add("http://61.28.227.193:9000/track");
		//babydata
		baseDomains.add("http://61.28.227.194:7777/babydata");		
		
		//DNewbies
		baseDomains.add("http://61.28.227.195:8080/logs");		
		//team 6
		baseDomains.add("http://61.28.227.196/track");		
		//GNV
		baseDomains.add("http://61.28.227.204/track");		
		//test
		baseDomains.add("http://54.254.165.29:6666/v1.0/events");
		
		//referrer
		referrerSources.add("https://facebook.com");
		referrerSources.add("https://google.com");
		referrerSources.add("https://youtube.com");
		referrerSources.add("https://twitter.com");
		referrerSources.add("https://www.pinterest.com");
		referrerSources.add("https://www.linkedin.com");
		referrerSources.add("https://plus.google.com/");
		
		//init Spring DAO 
		ApplicationContext context = SpringContextUtil.getContext();
		dbVideoDao = context.getBean(DbVideoDao.class);
		dbUserDao = context.getBean(DbUserDao.class);
		dbSellingOrderDao = context.getBean(DbSellingOrderDao.class);
		dbProductDao = context.getBean(DbProductDao.class);
	}
	
	
	
	protected static long generateOrder(String productId, long cusId, Date orderDate, int price) {
		DbSellingOrder order = new DbSellingOrder();
		order.setCustomerId(cusId);
		order.setProductId(productId);
		order.setOrderDate(orderDate);
		
		int q = RandomUtil.getRandomInteger(5, 1);
		int revenue = q * price;
		float discount = RandomUtil.getRandomInteger(10, 0) / 100f;
		order.setQuantity(q);
		order.setRevenue(revenue);
		order.setDiscount(discount);
		return dbSellingOrderDao.save(order);
	}
	
	static void startAutoBot(){	
		
		while (true) {
			long timeToSleep = 20;
			System.out.println("a");
			
			for (String baseDomain : baseDomains) {
				List<DbVideo> dbVideos = dbVideoDao.getRandomVideos(200);
				for (DbVideo dbVideo : dbVideos) {
					AsyncHttpClient httpClient = BotHttpClientUtil.getRamdomAsyncHttpClient();
					
					long viewerId = 0;
					int ranNum = RandomUtil.getRandomInteger(1000, 0);
					if(ranNum<400){
						viewerId = dbUserDao.getRandomUser().getId();
					}
					
					String pageviewMetric = PAGEVIEW;					
					String referrer = referrerSources.get(RandomUtil.getRandomInteger(referrerSources.size()-1, 0));
					String url = dbVideo.getUrl().replace("youtu.be", "vidsell.rfxlab.com/video/");//https://youtu.be/_7c10lxlJdA
					String location = LocationDataUtil.getRamdomLocationName();
					String productId = dbVideo.getProductId();
					String videoId = dbVideo.getVideo_id();
					
					
					String seed = StringUtil.toString(viewerId, httpClient.getConfig().getUserAgent(), location , ranNum); 
					String uuid = Hashing.sha256().hashString(seed, StandardCharsets.UTF_8).toString();
					
					//log pageview
					doHttpLog(baseDomain, httpClient, viewerId, pageviewMetric, referrer, url, location, productId, videoId, uuid, 0);
					
					DbProduct product = dbProductDao.getProductById(productId);
					String catname = StringUtils.stripAccents(product.getCategory()).replaceAll(" ", "-");
					if(product != null){
						//log click
						int ran2 = RandomUtil.getRandomInteger(1000, 0);
						if(ran2<300){
							// 10% percent is from browsing activity
							String clickMetric = CLICK;
							String referrerFromBrowsing = "http://vidsell.rfxlab.com/category/"+catname;
							doHttpLog(baseDomain, httpClient, viewerId, clickMetric, referrerFromBrowsing, url, location, productId, videoId, uuid, 0);
						}
						
						//log order
						
						if(ran2<200 && viewerId > 0 && viewerId != product.getOwnerId()){
							// 1% percent is from browsing activity
							String orderMetric = ORDER;
							String referForOrder = referrer;
							if(RandomUtil.getRandom(10)<3){
								referForOrder = "http://vidsell.rfxlab.com/category/"+catname;
							}		
							DbProduct product2 = dbProductDao.getRandomProduct();
							
							long orderId = generateOrder(product2.getProduct_id(), viewerId, new Date(), product2.getPrice());
							doHttpLog(baseDomain, httpClient, viewerId, orderMetric, referForOrder, url, location, productId, videoId, uuid, orderId);
						}
					}									
				}
			}
			Utils.sleep(timeToSleep);
		}
	}

	static boolean debug = true;
	protected static void doHttpLog(String baseDomain, AsyncHttpClient httpClient, long viewerId, String metric,
			String referrer, String url, String location, String productId, String videoId, String uuid, long orderId) {
		String logUrl = baseDomain + buildLogQueryString(metric, uuid, viewerId,location, referrer, url, productId, videoId, orderId);
		
		if(debug){
			if(metric.equals(ORDER)){
				System.out.println("====> ORDER "+logUrl);	
			} else if(metric.equals(CLICK)){
				System.out.println("==> CLICK "+logUrl);	
			} else {
				System.out.println("=> PAGEVIEW "+logUrl);
			}
		}
		
		httpClient.prepareGet(logUrl).execute();
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		BotHttpClientUtil.initHttpClientPool();
		startAutoBot();
				
		//BotHttpClientUtil.shutdownAllHttpClientPools();		
	}

}
