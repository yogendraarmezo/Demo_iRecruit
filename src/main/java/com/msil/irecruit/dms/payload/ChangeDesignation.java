package com.msil.irecruit.dms.payload;

public class ChangeDesignation {

	private String P_PMC;
	private String P_MSPIN;
	private String P_NEW_DESG_CD;
	private String AuthKey;
	public String getP_PMC() {
		return P_PMC;
	}
	public void setP_PMC(String p_PMC) {
		P_PMC = p_PMC;
	}
	public String getP_MSPIN() {
		return P_MSPIN;
	}
	public void setP_MSPIN(String p_MSPIN) {
		P_MSPIN = p_MSPIN;
	}
	public String getP_NEW_DESG_CD() {
		return P_NEW_DESG_CD;
	}
	public void setP_NEW_DESG_CD(String p_NEW_DESG_CD) {
		P_NEW_DESG_CD = p_NEW_DESG_CD;
	}
	public String getAuthKey() {
		return AuthKey;
	}
	public void setAuthKey(String authKey) {
		AuthKey = authKey;
	}
	
}
