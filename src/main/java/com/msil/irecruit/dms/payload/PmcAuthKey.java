package com.msil.irecruit.dms.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmcAuthKey {
	
	@JsonProperty
	private String P_PMC;
	@JsonProperty
	private String AuthKey;
	
	public PmcAuthKey() {
	}

	public PmcAuthKey(String p_PMC, String authKey) {
		super();
		P_PMC = p_PMC;
		AuthKey = authKey;
	}

	public String getP_PMC() {
		return P_PMC;
	}

	public void setP_PMC(String p_PMC) {
		P_PMC = p_PMC;
	}

	public String getAuthKey() {
		return AuthKey;
	}

	public void setAuthKey(String authKey) {
		AuthKey = authKey;
	}

	@Override
	public String toString() {
		return "PmsAuthKey [P_PMC=" + P_PMC + ", AuthKey=" + AuthKey + "]";
	}
	

}
