package com.tcs.project.resource;

import java.sql.Date;

public class VehicleDto {
	private int productId;
    private int customerId;
    
    private String gender;
    private int age;
    
    private String nominee;
    private String model;
    private String make;
    private Date fcDate;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getNominee() {
		return nominee;
	}
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public Date getFcDate() {
		return fcDate;
	}
	public void setFcDate(Date fcDate) {
		this.fcDate = fcDate;
	}

}
