package com.msil.irecruit.dms.payload;

public class MessagePayload {
	
	private String message;
	public MessagePayload() {
		}
	public MessagePayload(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MessagePayload [message=" + message + "]";
	}
	

}
