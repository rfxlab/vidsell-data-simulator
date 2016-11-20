package com.rfxlab.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import com.ning.http.client.Response;

import rfx.core.util.RandomUtil;

public class BotHttpClientUtil {

	static List<String> pcUAs = new ArrayList<>();
	static List<String> mWebUAs = new ArrayList<>();

	static {
		pcUAs.add("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
		pcUAs.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
		pcUAs.add("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
		pcUAs.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36");
		pcUAs.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36");
		pcUAs.add("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2226.0 Safari/537.36");
		pcUAs.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A");
		pcUAs.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");

		mWebUAs.add("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");
		mWebUAs.add("Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
		mWebUAs.add("Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
		mWebUAs.add("Mozilla/5.0 (BlackBerry; U; BlackBerry 9860; en-US) AppleWebKit/534.11+ (KHTML, like Gecko) Version/7.0.0.254 Mobile Safari/534.11+");
		mWebUAs.add("Mozilla/5.0 (Linux; Android 5.0.1; SAMSUNG GT-I9500 Build/LRX22C) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/2.1 Chrome/34.0.1847.76 Mobile Safari/537.36");
		mWebUAs.add("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A366 Safari/600.1.4");
		mWebUAs.add("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A366 Safari/600.1.4");
		
	}

	
	static List<AsyncHttpClient> httpClientPcPool = new ArrayList<>();
	static List<AsyncHttpClient> httpClientMobilePool = new ArrayList<>();
	public static void initHttpClientPool(){
		Builder builder = new AsyncHttpClientConfig.Builder();
		builder.setCompressionEnforced(true).setAllowPoolingConnections(true).setRequestTimeout(5000);
		
		for (String ua : pcUAs) {
			builder.setUserAgent(ua);			
			httpClientPcPool.add(new AsyncHttpClient(builder.build()));
		}
		for (String ua : mWebUAs) {
			builder.setUserAgent(ua);			
			httpClientMobilePool.add(new AsyncHttpClient(builder.build()));
		}
	}
	
	public static void shutdownAllHttpClientPools(){
		for (AsyncHttpClient asyncHttpClient : httpClientPcPool) {
			asyncHttpClient.close();
		}
		for (AsyncHttpClient asyncHttpClient : httpClientMobilePool) {
			asyncHttpClient.close();
		}
	}
	
	public static AsyncHttpClient getRamdomAsyncHttpClient() {
		int r = RandomUtil.getRandomInteger(2, 1);
		if (r == 1) {
			int i = RandomUtil.getRandom(httpClientPcPool.size() - 1);
			return httpClientPcPool.get(i);
		} else {
			int i = RandomUtil.getRandom(httpClientMobilePool.size() - 1);
			return httpClientMobilePool.get(i);
		}
	}

	public static void main(String[] args) throws IOException {
		initHttpClientPool();
		for (int i = 0; i < 100; i++) {
			try {
				String url = "https://log1.adsplay.net/ping";
				AsyncHttpClient ramdomAsyncHttpClient = getRamdomAsyncHttpClient();
				String ua = ramdomAsyncHttpClient.getConfig().getUserAgent();
				
				Future<Response> f = ramdomAsyncHttpClient.prepareGet(url).execute();
				Response r = f.get();
				System.out.println(ua + " => " +r.getResponseBody());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		System.out.println("done");
		shutdownAllHttpClientPools();
	}
}
