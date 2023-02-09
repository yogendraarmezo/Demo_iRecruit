package com.msil.irecruit.dms.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PrarambhStatusPayloadRequest {
	
	private String mspin;
	private String prarambhStatus;
//	 @JsonFormat(pattern = "dd/MM/yyyy")
//	private Date completionDate;
	 private String completionDate="1990-12-31";
	private String authKey;
	
	public PrarambhStatusPayloadRequest() {
		}

	public PrarambhStatusPayloadRequest(String mspin, String prarambhStatus, String completionDate, String authKey) {
		super();
		this.mspin = mspin;
		this.prarambhStatus = prarambhStatus;
		this.completionDate = completionDate;
		this.authKey = authKey;
	}

	public String getMspin() {
		return mspin;
	}

	public void setMspin(String mspin) {
		this.mspin = mspin;
	}

	public String getPrarambhStatus() {
		return prarambhStatus;
	}

	public void setPrarambhStatus(String prarambhStatus) {
		this.prarambhStatus = prarambhStatus;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	@Override
	public String toString() {
		return "PrarambhStatusPayloadRequest [mspin=" + mspin + ", prarambhStatus=" + prarambhStatus
				+ ", completionDate=" + completionDate + ", authKey=" + authKey + "]";
	}
	
	
	

}
