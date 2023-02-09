package com.msil.irecruit.dms.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentDetails {
	
	@JsonProperty("DEALER_MAP_CD")
	private String DEALER_MAP_CD;
	
	@JsonProperty("DEPT_CD")
	private String DEPT_CD;
	
	@JsonProperty("DEPT_DESC")
	private String DEPT_DESC;
	
	public DepartmentDetails() {
		}

	public String getDEALER_MAP_CD() {
		return DEALER_MAP_CD;
	}

	public void setDEALER_MAP_CD(String dEALER_MAP_CD) {
		DEALER_MAP_CD = dEALER_MAP_CD;
	}

	public String getDEPT_CD() {
		return DEPT_CD;
	}

	public void setDEPT_CD(String dEPT_CD) {
		DEPT_CD = dEPT_CD;
	}

	public String getDEPT_DESC() {
		return DEPT_DESC;
	}

	public void setDEPT_DESC(String dEPT_DESC) {
		DEPT_DESC = dEPT_DESC;
	}
	
	
	

}
