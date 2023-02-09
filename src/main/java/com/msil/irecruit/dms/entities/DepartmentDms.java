package com.msil.irecruit.dms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "dmsDepartment")
public class DepartmentDms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@JsonProperty
	private String dealerMapCode;
	@JsonProperty
	private String departmentCode;
	@JsonProperty
	private String departmentDesc;
	
	public DepartmentDms() {
		}

	public DepartmentDms(Long id, String dealerMapCode, String departmentCode, String departmentDesc) {
		super();
		this.id = id;
		this.dealerMapCode = dealerMapCode;
		this.departmentCode = departmentCode;
		this.departmentDesc = departmentDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDealerMapCode() {
		return dealerMapCode;
	}

	public void setDealerMapCode(String dealerMapCode) {
		this.dealerMapCode = dealerMapCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	@Override
	public String toString() {
		return "DepartmentDms [id=" + id + ", dealerMapCode=" + dealerMapCode + ", departmentCode=" + departmentCode
				+ ", departmentDesc=" + departmentDesc + "]";
	}
	
	

}
