package com.rfxlab.model.db;

public class DbSellingStore {

	long storeId;
	String name;
	long userId;
	String location;
	
	public DbSellingStore( String name, long user_id, String location) {
		super();
		this.name = name;
		this.userId = user_id;
		this.location = location;
	}
	
	public DbSellingStore(long store_id, String name, long user_id, String location) {
		super();
		this.storeId = store_id;
		this.name = name;
		this.userId = user_id;
		this.location = location;
	}
	public long getStoreId() {
		return storeId;
	}
	public void setStoreId(long store_id) {
		this.storeId = store_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long user_id) {
		this.userId = user_id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
