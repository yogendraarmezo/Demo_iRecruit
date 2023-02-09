package com.msil.irecruit.dms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name ="dmsState")
public class StateDms {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String stateCode;
	
	private String stateDesc;
	
	@OneToMany(fetch = FetchType.EAGER, 
    		mappedBy = "stateDms")
    @Cascade({CascadeType.ALL})
    private List<City> cities =new ArrayList<>();

	
	public StateDms() {
	}
	public StateDms(Long id, String stateCode, String stateDesc, List<City> cities) {
		super();
		this.id = id;
		this.stateCode = stateCode;
		this.stateDesc = stateDesc;
		this.cities = cities;
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
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	@Override
	public String toString() {
		return "State [" + (id != null ? "id=" + id + ", " : "")
				+ (stateCode != null ? "StateCode=" + stateCode + ", " : "")
				+ (stateDesc != null ? "stateDesc=" + stateDesc + ", " : "")
				+ (cities != null ? "cities=" + cities : "") + "]";
	}
	
	
	
	
}
