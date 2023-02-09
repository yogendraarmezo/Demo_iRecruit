package com.msil.irecruit.dms.payload;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DesignationList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String DESG_CD;
	
	@JsonProperty
	private String DESG_DESC;

	public DesignationList() {
	}
	
	public DesignationList(String dESG_CD, String dESG_DESC) {
		super();
		DESG_CD = dESG_CD;
		DESG_DESC = dESG_DESC;
	}

	public String getDESG_CD() {
		return DESG_CD;
	}

	public void setDESG_CD(String dESG_CD) {
		DESG_CD = dESG_CD;
	}

	public String getDESG_DESC() {
		return DESG_DESC;
	}

	public void setDESG_DESC(String dESG_DESC) {
		DESG_DESC = dESG_DESC;
	}

	@Override
	public String toString() {
		return "DesignationList [" + (DESG_CD != null ? "DESG_CD=" + DESG_CD + ", " : "")
				+ (DESG_DESC != null ? "DESG_DESC=" + DESG_DESC : "") + "]";
	}
	
	
	

}
