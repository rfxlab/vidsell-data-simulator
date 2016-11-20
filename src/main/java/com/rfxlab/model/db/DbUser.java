package com.rfxlab.model.db;

import java.util.Date;

import com.google.gson.Gson;

public class DbUser {

	long id;
	String name;
	String email;
	int age;
	int gender;
	String location;
	Date creationDate;
	Date modification;
	
	public DbUser(String email) {
		this.name = "";
		this.email = email;
		location = "unknown";
		creationDate = new Date();
		modification = new Date();
	}
	
	public DbUser(long id, String name, String email, int age, int gender, String location, Date creation_date,
			Date modification) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.location = location;
		this.creationDate = creation_date;
		this.modification = modification;
	}
	
	
	
	public DbUser(String name, String email, int age, int gender, String location, Date creation_date,
			Date modification) {
		super();
		this.name = name;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.location = location;
		this.creationDate = creation_date;
		this.modification = modification;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creation_date) {
		this.creationDate = creation_date;
	}
	public Date getModification() {
		return modification;
	}
	public void setModification(Date modification) {
		this.modification = modification;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
