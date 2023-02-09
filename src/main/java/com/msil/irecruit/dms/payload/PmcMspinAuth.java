package com.msil.irecruit.dms.payload;

public class PmcMspinAuth {

	
	private String P_PMC;
	
	private String P_MSPIN;
	
	private String AuthKey;
	
	
	public PmcMspinAuth() {
	}


	public PmcMspinAuth(String p_PMC, String p_MSPIN, String authKey) {
		super();
		P_PMC = p_PMC;
		P_MSPIN = p_MSPIN;
		AuthKey = authKey;
	}


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


	public String getAuthKey() {
		return AuthKey;
	}


	public void setAuthKey(String authKey) {
		AuthKey = authKey;
	}


	@Override
	public String toString() {
		return "PmcMspinAuth [P_PMC=" + P_PMC + ", P_MSPIN=" + P_MSPIN + ", AuthKey=" + AuthKey + "]";
	}
	
	
	


}
