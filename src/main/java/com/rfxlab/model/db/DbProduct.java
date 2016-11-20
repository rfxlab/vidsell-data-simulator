package com.rfxlab.model.db;

import java.util.Date;

import com.google.gson.Gson;

public class DbProduct {

	String productId;
	String name;
	long storeId;
	Date creationTime;
	Date updateTime;
	int status;
	int price;
	String description;
	String productType;
	String brand;
	String subcategory;
	String category;
	long ownerId;
	
	
	public DbProduct(String product_id, int price, String name,String category, long ownerId){
		this.productId = product_id;
		this.price = price;
		this.name = name;		
		this.category = category;
		this.ownerId = ownerId;
	}
		
	public DbProduct(String product_id, String name, long store_id, Date creation_time, Date update_time, int status,
			int price, String description, String product_type, String brand, String subcategory, String category) {
		super();
		this.productId = product_id;
		this.name = name;
		this.storeId = store_id;
		this.creationTime = creation_time;
		this.updateTime = update_time;
		this.status = status;
		this.price = price;
		this.description = description;
		this.productType = product_type;
		this.brand = brand;
		this.subcategory = subcategory;
		this.category = category;
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	public String getProduct_id() {
		return productId;
	}
	public void setProduct_id(String product_id) {
		this.productId = product_id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStore_id() {
		return storeId;
	}
	public void setStoreId(long store_id) {
		this.storeId = store_id;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreation_time(Date creation_time) {
		this.creationTime = creation_time;
	}
	public Date getUpdate_time() {
		return updateTime;
	}
	public void setUpdate_time(Date update_time) {
		this.updateTime = update_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProduct_type() {
		return productType;
	}
	public void setProduct_type(String product_type) {
		this.productType = product_type;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {	
		return new Gson().toJson(this);
	}
}
