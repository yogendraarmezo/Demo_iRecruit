package com.msil.irecruit.dms.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DesignationCursorPayload {
	
	@JsonProperty("P_LIST_CURSOR")
	private List<DesignationList> P_LIST_CURSOR;
	@JsonProperty("P_ERR_CD")
	private String P_ERR_CD;
	@JsonProperty("P_ERR_MSG")
	private String P_ERR_MSG;
	
	public DesignationCursorPayload() {
	}

	public DesignationCursorPayload(List<DesignationList> p_LIST_CURSOR, String p_ERR_CD, String p_ERR_MSG) {
		super();
		P_LIST_CURSOR = p_LIST_CURSOR;
		P_ERR_CD = p_ERR_CD;
		P_ERR_MSG = p_ERR_MSG;
	}

	public List<DesignationList> getP_LIST_CURSOR() {
		return P_LIST_CURSOR;
	}

	public void setP_LIST_CURSOR(List<DesignationList> p_LIST_CURSOR) {
		P_LIST_CURSOR = p_LIST_CURSOR;
	}

	public String getP_ERR_CD() {
		return P_ERR_CD;
	}

	public void setP_ERR_CD(String p_ERR_CD) {
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
		return "DesignationCursorPayload [" + (P_LIST_CURSOR != null ? "P_LIST_CURSOR=" + P_LIST_CURSOR + ", " : "")
				+ (P_ERR_CD != null ? "P_ERR_CD=" + P_ERR_CD + ", " : "")
				+ (P_ERR_MSG != null ? "P_ERR_MSG=" + P_ERR_MSG : "") + "]";
	}

	

}
