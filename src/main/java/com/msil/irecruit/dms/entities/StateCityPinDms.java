package com.msil.irecruit.dms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dmsStateCityPin")
public class StateCityPinDms {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String stateCode;
	private String stateDesc;
	private String cityCode;
	private String cityDesc;
	private String pinCode;
	
	public StateCityPinDms() {
		}

	public StateCityPinDms(Long id, String stateCode, String stateDesc, String cityCode, String cityDesc,
			String pinCode) {
		super();
		this.id = id;
		this.stateCode = stateCode;
		this.stateDesc = stateDesc;
		this.cityCode = cityCode;
		this.cityDesc = cityDesc;
		this.pinCode = pinCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityDesc() {
		return cityDesc;
	}

	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	@Override
	public String toString() {
		return "StateCityPinDms [id=" + id + ", stateCode=" + stateCode + ", stateDesc=" + stateDesc + ", cityCode="
				+ cityCode + ", cityDesc=" + cityDesc + ", pinCode=" + pinCode + "]";
	}
	
	
	

}
