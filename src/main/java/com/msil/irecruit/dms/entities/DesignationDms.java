package com.msil.irecruit.dms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dmsDesignation")
public class DesignationDms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String designationCode;
	
	private String designationDesc;
	
	public DesignationDms() {
	}

	public DesignationDms(Long id, String designationCode, String designationDesc) {
		super();
		this.id = id;
		this.designationCode = designationCode;
		this.designationDesc = designationDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesignationCode() {
		return designationCode;
	}

	public void setDesignationCode(String designationCode) {
		this.designationCode = designationCode;
	}

	public String getDesignationDesc() {
		return designationDesc;
	}

	public void setDesignationDesc(String designationDesc) {
		this.designationDesc = designationDesc;
	}

	@Override
	public String toString() {
		return "DesignationDms [" + (id != null ? "id=" + id + ", " : "")
				+ (designationCode != null ? "designationCode=" + designationCode + ", " : "")
				+ (designationDesc != null ? "designationDesc=" + designationDesc : "") + "]";
	}
	
	

}
