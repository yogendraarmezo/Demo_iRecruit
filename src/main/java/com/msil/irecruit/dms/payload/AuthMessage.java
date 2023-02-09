package com.msil.irecruit.dms.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthMessage {
	
	@JsonProperty
	private String Message;
	
	public AuthMessage() {
		// TODO Auto-generated constructor stub
	}

	public AuthMessage(String message) {
		super();
		Message = message;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}

	@Override
	public String toString() {
		return "AuthMessage [Message=" + Message + "]";
	}
	
	

}
