package com.msil.irecruit.dms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "api_respose")
public class ApiResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String apiName;
	private String apiResponce;
	private String apiMessage;
	private String accesskey;
	private String request;
	private int count;
	private Date apiDate;
	
	
	public ApiResponse() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiResponce() {
		return apiResponce;
	}
	public void setApiResponce(String apiResponce) {
		this.apiResponce = apiResponce;
	}
	public String getApiMessage() {
		return apiMessage;
	}
	public void setApiMessage(String apiMessage) {
		this.apiMessage = apiMessage;
	}
	public String getAccesskey() {
		return accesskey;
	}
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getDate() {
		return apiDate;
	}
	public void setApiDate(Date apiDate) {
		this.apiDate = apiDate;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public Date getApiDate() {
		return apiDate;
	}
	
}
