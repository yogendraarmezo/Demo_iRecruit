package com.msil.irecruit.dms.payload;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CityList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8985238111959278155L;
	@JsonProperty
	private String CITY_CD;
	@JsonProperty
	private String CITY_DESC;
	@JsonProperty
	private String STATE_CD;
	@JsonProperty
	private String STATE_DESC;
	@JsonProperty
	private String PIN_CD;
	
	public CityList() {
		// TODO Auto-generated constructor stub
	}

	public CityList(String cITY_CD, String cITY_DESC, String sTATE_CD, String sTATE_DESC, String pIN_CD) {
		super();
		CITY_CD = cITY_CD;
		CITY_DESC = cITY_DESC;
		STATE_CD = sTATE_CD;
		STATE_DESC = sTATE_DESC;
		PIN_CD = pIN_CD;
	}

	public String getCITY_CD() {
		return CITY_CD;
	}

	public void setCITY_CD(String cITY_CD) {
		CITY_CD = cITY_CD;
	}

	public String getCITY_DESC() {
		return CITY_DESC;
	}

	public void setCITY_DESC(String cITY_DESC) {
		CITY_DESC = cITY_DESC;
	}

	public String getSTATE_CD() {
		return STATE_CD;
	}

	public void setSTATE_CD(String sTATE_CD) {
		STATE_CD = sTATE_CD;
	}

	public String getSTATE_DESC() {
		return STATE_DESC;
	}

	public void setSTATE_DESC(String sTATE_DESC) {
		STATE_DESC = sTATE_DESC;
	}

	public String getPIN_CD() {
		return PIN_CD;
	}
	
	public void setPIN_CD(String pIN_CD) {
		PIN_CD = pIN_CD;
	}

	@Override
	public String toString() {
		return "CityList [" + (CITY_CD != null ? "CITY_CD=" + CITY_CD + ", " : "")
				+ (CITY_DESC != null ? "CITY_DESC=" + CITY_DESC + ", " : "")
				+ (STATE_CD != null ? "STATE_CD=" + STATE_CD + ", " : "")
				+ (STATE_DESC != null ? "STATE_DESC=" + STATE_DESC + ", " : "")
				+ (PIN_CD != null ? "PIN_CD=" + PIN_CD : "") + "]";
	}
	
	
	

}
