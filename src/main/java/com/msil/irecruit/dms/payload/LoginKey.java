package com.msil.irecruit.dms.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginKey {
	
	@JsonProperty
	private String UserName;
	
	@JsonProperty
	private String Password;
	
	public LoginKey() {
	}

	public LoginKey(String userName, String password) {
		super();
		UserName = userName;
		Password = password;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@Override
	public String toString() {
		return "LoginKey [UserName=" + UserName + ", Password=" + Password + "]";
	}
	
	
	
	

}
