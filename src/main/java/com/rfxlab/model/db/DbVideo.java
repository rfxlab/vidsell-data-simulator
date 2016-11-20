package com.rfxlab.model.db;

import java.util.Date;

import com.google.gson.Gson;

public class DbVideo {

	//PK
	String videoId;
	
	String url;
	String category;
	String title;
	String description;
	String thumbnailImage;
	Date publishedTime;
	String channelId;
	String channelTitle;
	
	//PK
	String productId;
	
	public DbVideo(String video_id, String url, String product_id) {
		super();
		this.videoId = video_id;
		this.url = url;
		this.productId = product_id;
	}
	

	public DbVideo(String video_id, String url, String category, String title, String description,
			String thumbnail_image, Date published_time, String channel_id, String channel_title, String product_id) {
		super();
		this.videoId = video_id;
		this.url = url;
		this.category = category;
		this.title = title;
		this.description = description;
		this.thumbnailImage = thumbnail_image;
		this.publishedTime = published_time;
		this.channelId = channel_id;
		this.channelTitle = channel_title;
		this.productId = product_id;
	}

	public String getVideo_id() {
		return videoId;
	}

	public void setVideo_id(String video_id) {
		this.videoId = video_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(String thumbnail_image) {
		this.thumbnailImage = thumbnail_image;
	}

	public Date getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(Date published_time) {
		this.publishedTime = published_time;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channel_id) {
		this.channelId = channel_id;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channel_title) {
		this.channelTitle = channel_title;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String product_id) {
		this.productId = product_id;
	}
	
	@Override
	public String toString() {	
		return new Gson().toJson(this);
	}
	
}
