package com.msil.irecruit.dms.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TehsilVillageRequest {
	
	@JsonProperty
	private String P_PMC;
	@JsonProperty
	private String P_STATE_CD;
	@JsonProperty
	private String AuthKey;
	
	public TehsilVillageRequest() {
	}

	public TehsilVillageRequest(String p_PMC, String p_STATE_CD, String authKey) {
		super();
		P_PMC = p_PMC;
		P_STATE_CD = p_STATE_CD;
		AuthKey = authKey;
	}

	public String getP_PMC() {
		return P_PMC;
	}

	public void setP_PMC(String p_PMC) {
		P_PMC = p_PMC;
	}

	public String getP_STATE_CD() {
		return P_STATE_CD;
	}

	public void setP_STATE_CD(String p_STATE_CD) {
		P_STATE_CD = p_STATE_CD;
	}

	public String getAuthKey() {
		return AuthKey;
	}

	public void setAuthKey(String authKey) {
		AuthKey = authKey;
	}

	@Override
	public String toString() {
		return "TehsilVillageRequest [P_PMC=" + P_PMC + ", P_STATE_CD=" + P_STATE_CD + ", AuthKey=" + AuthKey + "]";
	}
	
	
	

}
