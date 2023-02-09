package com.msil.irecruit.dms.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TehsilVillageCursor {
	
	@JsonProperty("P_LIST_CURSOR")
	private List<TehsilVillagePayload> P_LIST_CURSOR;
	@JsonProperty("P_ERR_CD")
	private String P_ERR_CD;
	@JsonProperty("P_ERR_MSG")
	private String P_ERR_MSG;
	
	public TehsilVillageCursor() {
	}

	public TehsilVillageCursor(List<TehsilVillagePayload> p_LIST_CURSOR, String p_ERR_CD, String p_ERR_MSG) {
		super();
		P_LIST_CURSOR = p_LIST_CURSOR;
		P_ERR_CD = p_ERR_CD;
		P_ERR_MSG = p_ERR_MSG;
	}

	public List<TehsilVillagePayload> getP_LIST_CURSOR() {
		return P_LIST_CURSOR;
	}

	public void setP_LIST_CURSOR(List<TehsilVillagePayload> p_LIST_CURSOR) {
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
		return "TehsilVillageCursor [P_LIST_CURSOR=" + P_LIST_CURSOR + ", P_ERR_CD=" + P_ERR_CD + ", P_ERR_MSG="
				+ P_ERR_MSG + "]";
	}
	

}
