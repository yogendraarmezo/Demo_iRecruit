package com.msil.irecruit.dms.entities;

public class DataScienceAuth {

	
    private String UserId;	
	private String Password;	
	private String AppId;
	
	
	  public DataScienceAuth(String UserId,String Password,String AppId) {
	  this.UserId=UserId; 
	  this.Password=Password;
	  this.AppId=AppId; 
	  }
	 
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getAppId() {
		return AppId;
	}
	public void setAppId(String appId) {
		AppId = appId;
	}
	
	
	
}
