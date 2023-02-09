package com.msil.irecruit.dms.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentDetailsCursorPayload {
	
	@JsonProperty("P_LIST_CURSOR")
	private List<DepartmentDetails> P_LIST_CURSOR;
	
	
	public DepartmentDetailsCursorPayload() {
	}
	
	public DepartmentDetailsCursorPayload(List<DepartmentDetails> P_LIST_CURSOR) {
		this.P_LIST_CURSOR=P_LIST_CURSOR;
	}

	public List<DepartmentDetails> getP_LIST_CURSOR() {
		return P_LIST_CURSOR;
	}

	public void setP_LIST_CURSOR(List<DepartmentDetails> p_LIST_CURSOR) {
		P_LIST_CURSOR = p_LIST_CURSOR;
	}

	

	


	
}
