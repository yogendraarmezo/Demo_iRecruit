package com.msil.irecruit.dms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dmsLocation")
public class LocationDms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String parentGroup;
	private String dealerMapCode;
	private String locationCode;
	private String locationDesc;
	
	public LocationDms() {
		// TODO Auto-generated constructor stub
	}

	public LocationDms(Long id, String parentGroup, String dealerMapCode, String locationCode, String locationDesc) {
		super();
		this.id = id;
		this.parentGroup = parentGroup;
		this.dealerMapCode = dealerMapCode;
		this.locationCode = locationCode;
		this.locationDesc = locationDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(String parentGroup) {
		this.parentGroup = parentGroup;
	}

	public String getDealerMapCode() {
		return dealerMapCode;
	}

	public void setDealerMapCode(String dealerMapCode) {
		this.dealerMapCode = dealerMapCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	@Override
	public String toString() {
		return "LocationDms [id=" + id + ", parentGroup=" + parentGroup + ", dealerMapCode=" + dealerMapCode
				+ ", locationCode=" + locationCode + ", locationDesc=" + locationDesc + "]";
	}
	

}
