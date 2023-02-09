package com.msil.irecruit.dms.payload;

public class LocationList {
	
	private String PARENT_GROUP;
	private String DEALER_MAP_CODE;
	private String LOC_CD;
	private String LOC_DESC;
	
	public LocationList() {
		}

	public LocationList(String pARENT_GROUP, String dEALER_MAP_CODE, String lOC_CD, String lOC_DESC) {
		super();
		PARENT_GROUP = pARENT_GROUP;
		DEALER_MAP_CODE = dEALER_MAP_CODE;
		LOC_CD = lOC_CD;
		LOC_DESC = lOC_DESC;
	}

	public String getPARENT_GROUP() {
		return PARENT_GROUP;
	}

	public void setPARENT_GROUP(String pARENT_GROUP) {
		PARENT_GROUP = pARENT_GROUP;
	}

	public String getDEALER_MAP_CODE() {
		return DEALER_MAP_CODE;
	}

	public void setDEALER_MAP_CODE(String dEALER_MAP_CODE) {
		DEALER_MAP_CODE = dEALER_MAP_CODE;
	}

	public String getLOC_CD() {
		return LOC_CD;
	}

	public void setLOC_CD(String lOC_CD) {
		LOC_CD = lOC_CD;
	}

	public String getLOC_DESC() {
		return LOC_DESC;
	}

	public void setLOC_DESC(String lOC_DESC) {
		LOC_DESC = lOC_DESC;
	}

	@Override
	public String toString() {
		return "LocationList [PARENT_GROUP=" + PARENT_GROUP + ", DEALER_MAP_CODE=" + DEALER_MAP_CODE + ", LOC_CD="
				+ LOC_CD + ", LOC_DESC=" + LOC_DESC + "]";
	}
	
	

}
