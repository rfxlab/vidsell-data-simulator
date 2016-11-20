package com.rfxlab.model.db;

import java.util.Date;

public class DbSellingOrder {

	long orderId;
	String productId;
	long customerId;// ~ user_id , the PK of user
	Date orderDate;
	int quantity;
	long revenue;
	float discount;
	
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long order_id) {
		this.orderId = order_id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String product_id) {
		this.productId = product_id;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customer_id) {
		this.customerId = customer_id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date order_date) {
		this.orderDate = order_date;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getRevenue() {
		return revenue;
	}
	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	
	
}
