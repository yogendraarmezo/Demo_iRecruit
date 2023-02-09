package com.msil.irecruit.dms.payload;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListDmsList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6400954594462367420L;
	
	@JsonProperty
	private String LIST_NAME;
	@JsonProperty
	private String LIST_CODE;
	@JsonProperty
	private String LIST_DESC;
	
	public ListDmsList() {
	}

	public ListDmsList(String lIST_NAME, String lIST_CODE, String lIST_DESC) {
		super();
		LIST_NAME = lIST_NAME;
		LIST_CODE = lIST_CODE;
		LIST_DESC = lIST_DESC;
	}

	public String getLIST_NAME() {
		return LIST_NAME;
	}

	public void setLIST_NAME(String lIST_NAME) {
		LIST_NAME = lIST_NAME;
	}

	public String getLIST_CODE() {
		return LIST_CODE;
	}

	public void setLIST_CODE(String lIST_CODE) {
		LIST_CODE = lIST_CODE;
	}

	public String getLIST_DESC() {
		return LIST_DESC;
	}

	public void setLIST_DESC(String lIST_DESC) {
		LIST_DESC = lIST_DESC;
	}

	@Override
	public String toString() {
		return "ListDmsList [" + (LIST_NAME != null ? "LIST_NAME=" + LIST_NAME + ", " : "")
				+ (LIST_CODE != null ? "LIST_CODE=" + LIST_CODE + ", " : "")
				+ (LIST_DESC != null ? "LIST_DESC=" + LIST_DESC : "") + "]";
	}
	
	

}
