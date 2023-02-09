package com.msil.irecruit.dms.payload;

public class TehsilVillagePayload {
	
	private String TEHSIL_CD;
	private String TEHSIL_DESC;
	private String VILLAGE_CD;
	private String VILLAGE_NAME;
	private String ACTIVE_YN;
	
	public TehsilVillagePayload() {
	}

	public TehsilVillagePayload(String tEHSIL_CD, String tEHSIL_DESC, String vILLAGE_CD, String vILLAGE_NAME,
			String aCTIVE_YN) {
		super();
		TEHSIL_CD = tEHSIL_CD;
		TEHSIL_DESC = tEHSIL_DESC;
		VILLAGE_CD = vILLAGE_CD;
		VILLAGE_NAME = vILLAGE_NAME;
		ACTIVE_YN = aCTIVE_YN;
	}

	public String getTEHSIL_CD() {
		return TEHSIL_CD;
	}

	public void setTEHSIL_CD(String tEHSIL_CD) {
		TEHSIL_CD = tEHSIL_CD;
	}

	public String getTEHSIL_DESC() {
		return TEHSIL_DESC;
	}

	public void setTEHSIL_DESC(String tEHSIL_DESC) {
		TEHSIL_DESC = tEHSIL_DESC;
	}

	public String getVILLAGE_CD() {
		return VILLAGE_CD;
	}

	public void setVILLAGE_CD(String vILLAGE_CD) {
		VILLAGE_CD = vILLAGE_CD;
	}

	public String getVILLAGE_NAME() {
		return VILLAGE_NAME;
	}

	public void setVILLAGE_NAME(String vILLAGE_NAME) {
		VILLAGE_NAME = vILLAGE_NAME;
	}

	public String getACTIVE_YN() {
		return ACTIVE_YN;
	}

	public void setACTIVE_YN(String aCTIVE_YN) {
		ACTIVE_YN = aCTIVE_YN;
	}

	@Override
	public String toString() {
		return "TehsilVillagePayload [TEHSIL_CD=" + TEHSIL_CD + ", TEHSIL_DESC=" + TEHSIL_DESC + ", VILLAGE_CD="
				+ VILLAGE_CD + ", VILLAGE_NAME=" + VILLAGE_NAME + ", ACTIVE_YN=" + ACTIVE_YN + "]";
	}
	
	
	

}
