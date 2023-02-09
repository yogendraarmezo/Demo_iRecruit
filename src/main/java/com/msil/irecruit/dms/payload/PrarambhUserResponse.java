package com.msil.irecruit.dms.payload;

import java.io.Serializable;

public class PrarambhUserResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String authKey;
	
	public PrarambhUserResponse() {
	}

	public PrarambhUserResponse(String authKey) {
		super();
		this.authKey = authKey;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	@Override
	public String toString() {
		return "PrarambhUserResponse [ authKey=" + authKey + "]";
	}
	

}
