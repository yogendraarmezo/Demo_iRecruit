package com.msil.irecruit.dms.payload;

public class PrarambhStatusResponse {
	
	private String mspin;
	private String message;

	public PrarambhStatusResponse() {
		// TODO Auto-generated constructor stub
	}

	public PrarambhStatusResponse(String mspin, String message) {
		super();
		this.mspin = mspin;
		this.message = message;
	}

	public String getMspin() {
		return mspin;
	}

	public void setMspin(String mspin) {
		this.mspin = mspin;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PrarambhStatusResponse [mspin=" + mspin + ", message=" + message + "]";
	}
	
}
