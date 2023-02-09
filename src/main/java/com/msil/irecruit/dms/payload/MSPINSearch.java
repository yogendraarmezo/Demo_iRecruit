package com.msil.irecruit.dms.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MSPINSearch {
	
	@JsonProperty
	private String PARENT_GROUP;
	@JsonProperty
	private String DEALER_MAP_CD;
	@JsonProperty
	private String LOC_CD;
	@JsonProperty
	private String MUL_DEALER_CD;
	@JsonProperty
	private String FOR_CD;
	@JsonProperty
	private String MSPIN;
	@JsonProperty
	private String EMP_DESG_CD;
	@JsonProperty
	private String SUB_DESG_CD;
	@JsonProperty
	private String EMP_MSIL_YN;
	@JsonProperty
	private String EMP_LEAVING_DATE;
	@JsonProperty
	private String EMP_SEX;
	@JsonProperty
	private String EMP_DOB;
	@JsonProperty
	private String TITLE_CD;
	@JsonProperty
	private String EMP_NAME;
	@JsonProperty
	private String FIRST_NAME;
	@JsonProperty
	private String MIDDLE_NAME;
	@JsonProperty
	private String LAST_NAME;
	@JsonProperty
	private String MOBILE;
	@JsonProperty
	private String EMP_ADDRESS1;
	@JsonProperty
	private String EMP_EMAIL_ID;
	@JsonProperty
	private String EMP_DEPT_CD;
	@JsonProperty
	private String EMP_CATEG;
	@JsonProperty
	private String EMP_CITY_CD;
	@JsonProperty
	private String EMP_PIN;
	@JsonProperty
	private String ID_PROOF;
	@JsonProperty
	private String ID_PROOF_NO;
	@JsonProperty
	private String VALIDITY_DL;
	@JsonProperty
	private String EMP_QUALIFICATION;
	@JsonProperty
	private String PRIM_LANG_CD;
	@JsonProperty
	private String SEC_LANG_CD;
	
	public MSPINSearch() {
		}

	public MSPINSearch(String pARENT_GROUP, String dEALER_MAP_CD, String lOC_CD, String mUL_DEALER_CD, String fOR_CD,
			String mSPIN, String eMP_DESG_CD, String sUB_DESG_CD, String eMP_MSIL_YN, String eMP_LEAVING_DATE,
			String eMP_SEX, String eMP_DOB, String tITLE_CD, String eMP_NAME, String fIRST_NAME, String mIDDLE_NAME,
			String lAST_NAME, String mOBILE, String eMP_ADDRESS1, String eMP_EMAIL_ID, String eMP_DEPT_CD,
			String eMP_CATEG, String eMP_CITY_CD, String eMP_PIN, String iD_PROOF, String iD_PROOF_NO,
			String vALIDITY_DL, String eMP_QUALIFICATION, String pRIM_LANG_CD, String sEC_LANG_CD) {
		super();
		PARENT_GROUP = pARENT_GROUP;
		DEALER_MAP_CD = dEALER_MAP_CD;
		LOC_CD = lOC_CD;
		MUL_DEALER_CD = mUL_DEALER_CD;
		FOR_CD = fOR_CD;
		MSPIN = mSPIN;
		EMP_DESG_CD = eMP_DESG_CD;
		SUB_DESG_CD = sUB_DESG_CD;
		EMP_MSIL_YN = eMP_MSIL_YN;
		EMP_LEAVING_DATE = eMP_LEAVING_DATE;
		EMP_SEX = eMP_SEX;
		EMP_DOB = eMP_DOB;
		TITLE_CD = tITLE_CD;
		EMP_NAME = eMP_NAME;
		FIRST_NAME = fIRST_NAME;
		MIDDLE_NAME = mIDDLE_NAME;
		LAST_NAME = lAST_NAME;
		MOBILE = mOBILE;
		EMP_ADDRESS1 = eMP_ADDRESS1;
		EMP_EMAIL_ID = eMP_EMAIL_ID;
		EMP_DEPT_CD = eMP_DEPT_CD;
		EMP_CATEG = eMP_CATEG;
		EMP_CITY_CD = eMP_CITY_CD;
		EMP_PIN = eMP_PIN;
		ID_PROOF = iD_PROOF;
		ID_PROOF_NO = iD_PROOF_NO;
		VALIDITY_DL = vALIDITY_DL;
		EMP_QUALIFICATION = eMP_QUALIFICATION;
		PRIM_LANG_CD = pRIM_LANG_CD;
		SEC_LANG_CD = sEC_LANG_CD;
	}

	public String getPARENT_GROUP() {
		return PARENT_GROUP;
	}

	public void setPARENT_GROUP(String pARENT_GROUP) {
		PARENT_GROUP = pARENT_GROUP;
	}

	public String getDEALER_MAP_CD() {
		return DEALER_MAP_CD;
	}

	public void setDEALER_MAP_CD(String dEALER_MAP_CD) {
		DEALER_MAP_CD = dEALER_MAP_CD;
	}

	public String getLOC_CD() {
		return LOC_CD;
	}

	public void setLOC_CD(String lOC_CD) {
		LOC_CD = lOC_CD;
	}

	public String getMUL_DEALER_CD() {
		return MUL_DEALER_CD;
	}

	public void setMUL_DEALER_CD(String mUL_DEALER_CD) {
		MUL_DEALER_CD = mUL_DEALER_CD;
	}

	public String getFOR_CD() {
		return FOR_CD;
	}

	public void setFOR_CD(String fOR_CD) {
		FOR_CD = fOR_CD;
	}

	public String getMSPIN() {
		return MSPIN;
	}

	public void setMSPIN(String mSPIN) {
		MSPIN = mSPIN;
	}

	public String getEMP_DESG_CD() {
		return EMP_DESG_CD;
	}

	public void setEMP_DESG_CD(String eMP_DESG_CD) {
		EMP_DESG_CD = eMP_DESG_CD;
	}

	public String getSUB_DESG_CD() {
		return SUB_DESG_CD;
	}

	public void setSUB_DESG_CD(String sUB_DESG_CD) {
		SUB_DESG_CD = sUB_DESG_CD;
	}

	public String getEMP_MSIL_YN() {
		return EMP_MSIL_YN;
	}

	public void setEMP_MSIL_YN(String eMP_MSIL_YN) {
		EMP_MSIL_YN = eMP_MSIL_YN;
	}

	public String getEMP_LEAVING_DATE() {
		return EMP_LEAVING_DATE;
	}

	public void setEMP_LEAVING_DATE(String eMP_LEAVING_DATE) {
		EMP_LEAVING_DATE = eMP_LEAVING_DATE;
	}

	public String getEMP_SEX() {
		return EMP_SEX;
	}

	public void setEMP_SEX(String eMP_SEX) {
		EMP_SEX = eMP_SEX;
	}

	public String getEMP_DOB() {
		return EMP_DOB;
	}

	public void setEMP_DOB(String eMP_DOB) {
		EMP_DOB = eMP_DOB;
	}

	public String getTITLE_CD() {
		return TITLE_CD;
	}

	public void setTITLE_CD(String tITLE_CD) {
		TITLE_CD = tITLE_CD;
	}

	public String getEMP_NAME() {
		return EMP_NAME;
	}

	public void setEMP_NAME(String eMP_NAME) {
		EMP_NAME = eMP_NAME;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getMIDDLE_NAME() {
		return MIDDLE_NAME;
	}

	public void setMIDDLE_NAME(String mIDDLE_NAME) {
		MIDDLE_NAME = mIDDLE_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getEMP_ADDRESS1() {
		return EMP_ADDRESS1;
	}

	public void setEMP_ADDRESS1(String eMP_ADDRESS1) {
		EMP_ADDRESS1 = eMP_ADDRESS1;
	}

	public String getEMP_EMAIL_ID() {
		return EMP_EMAIL_ID;
	}

	public void setEMP_EMAIL_ID(String eMP_EMAIL_ID) {
		EMP_EMAIL_ID = eMP_EMAIL_ID;
	}

	public String getEMP_DEPT_CD() {
		return EMP_DEPT_CD;
	}

	public void setEMP_DEPT_CD(String eMP_DEPT_CD) {
		EMP_DEPT_CD = eMP_DEPT_CD;
	}

	public String getEMP_CATEG() {
		return EMP_CATEG;
	}

	public void setEMP_CATEG(String eMP_CATEG) {
		EMP_CATEG = eMP_CATEG;
	}

	public String getEMP_CITY_CD() {
		return EMP_CITY_CD;
	}

	public void setEMP_CITY_CD(String eMP_CITY_CD) {
		EMP_CITY_CD = eMP_CITY_CD;
	}

	public String getEMP_PIN() {
		return EMP_PIN;
	}

	public void setEMP_PIN(String eMP_PIN) {
		EMP_PIN = eMP_PIN;
	}

	public String getID_PROOF() {
		return ID_PROOF;
	}

	public void setID_PROOF(String iD_PROOF) {
		ID_PROOF = iD_PROOF;
	}

	public String getID_PROOF_NO() {
		return ID_PROOF_NO;
	}

	public void setID_PROOF_NO(String iD_PROOF_NO) {
		ID_PROOF_NO = iD_PROOF_NO;
	}

	public String getVALIDITY_DL() {
		return VALIDITY_DL;
	}

	public void setVALIDITY_DL(String vALIDITY_DL) {
		VALIDITY_DL = vALIDITY_DL;
	}

	public String getEMP_QUALIFICATION() {
		return EMP_QUALIFICATION;
	}

	public void setEMP_QUALIFICATION(String eMP_QUALIFICATION) {
		EMP_QUALIFICATION = eMP_QUALIFICATION;
	}

	public String getPRIM_LANG_CD() {
		return PRIM_LANG_CD;
	}

	public void setPRIM_LANG_CD(String pRIM_LANG_CD) {
		PRIM_LANG_CD = pRIM_LANG_CD;
	}

	public String getSEC_LANG_CD() {
		return SEC_LANG_CD;
	}

	public void setSEC_LANG_CD(String sEC_LANG_CD) {
		SEC_LANG_CD = sEC_LANG_CD;
	}

	@Override
	public String toString() {
		return "MSPINSearch [PARENT_GROUP=" + PARENT_GROUP + ", DEALER_MAP_CD=" + DEALER_MAP_CD + ", LOC_CD=" + LOC_CD
				+ ", MUL_DEALER_CD=" + MUL_DEALER_CD + ", FOR_CD=" + FOR_CD + ", MSPIN=" + MSPIN + ", EMP_DESG_CD="
				+ EMP_DESG_CD + ", SUB_DESG_CD=" + SUB_DESG_CD + ", EMP_MSIL_YN=" + EMP_MSIL_YN + ", EMP_LEAVING_DATE="
				+ EMP_LEAVING_DATE + ", EMP_SEX=" + EMP_SEX + ", EMP_DOB=" + EMP_DOB + ", TITLE_CD=" + TITLE_CD
				+ ", EMP_NAME=" + EMP_NAME + ", FIRST_NAME=" + FIRST_NAME + ", MIDDLE_NAME=" + MIDDLE_NAME
				+ ", LAST_NAME=" + LAST_NAME + ", MOBILE=" + MOBILE + ", EMP_ADDRESS1=" + EMP_ADDRESS1
				+ ", EMP_EMAIL_ID=" + EMP_EMAIL_ID + ", EMP_DEPT_CD=" + EMP_DEPT_CD + ", EMP_CATEG=" + EMP_CATEG
				+ ", EMP_CITY_CD=" + EMP_CITY_CD + ", EMP_PIN=" + EMP_PIN + ", ID_PROOF=" + ID_PROOF + ", ID_PROOF_NO="
				+ ID_PROOF_NO + ", VALIDITY_DL=" + VALIDITY_DL + ", EMP_QUALIFICATION=" + EMP_QUALIFICATION
				+ ", PRIM_LANG_CD=" + PRIM_LANG_CD + ", SEC_LANG_CD=" + SEC_LANG_CD + "]";
	}
	
	
	
}
