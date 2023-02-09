package com.msil.irecruit.dms.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CityListCursor {
	
	@JsonProperty
	private List<CityList> P_LIST_CURSOR;
	
	@JsonProperty
	private int P_ERR_CD;
	
	@JsonProperty
	private String P_ERR_MSG;
	
	public CityListCursor() {
		
	}

	public CityListCursor(List<CityList> p_LIST_CURSOR, int p_ERR_CD, String p_ERR_MSG) {
		super();
		P_LIST_CURSOR = p_LIST_CURSOR;
		P_ERR_CD = p_ERR_CD;
		P_ERR_MSG = p_ERR_MSG;
	}

	public List<CityList> getP_LIST_CURSOR() {
		return P_LIST_CURSOR;
	}

	public void setP_LIST_CURSOR(List<CityList> p_LIST_CURSOR) {
		P_LIST_CURSOR = p_LIST_CURSOR;
	}

	public int getP_ERR_CD() {
		return P_ERR_CD;
	}

	public void setP_ERR_CD(int p_ERR_CD) {
		P_ERR_CD = p_ERR_CD;
	}

	public String getP_ERR_MSG() {
		return P_ERR_MSG;
	}

	public void setP_ERR_MSG(String p_ERR_MSG) {
		P_ERR_MSG = p_ERR_MSG;
	}

	@Override
	public String toString() {
		return "ListCursor [P_LIST_CURSOR=" + P_LIST_CURSOR + ", P_ERR_CD=" + P_ERR_CD + ", P_ERR_MSG=" + P_ERR_MSG
				+ "]";
	}
	
}
