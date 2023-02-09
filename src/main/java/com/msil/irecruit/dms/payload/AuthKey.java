package com.msil.irecruit.dms.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthKey {
	
	@JsonProperty
	private String AuthKey;
	
	public AuthKey() {
		// TODO Auto-generated constructor stub
	}

	public AuthKey(String authKey) {
		super();
		AuthKey = authKey;
	}

	public String getAuthKey() {
		return AuthKey;
	}

	public void setAuthKey(String authKey) {
		AuthKey = authKey;
	}

	@Override
	public String toString() {
		return "AuthKey [AuthKey=" + AuthKey + "]";
	}
	

}
