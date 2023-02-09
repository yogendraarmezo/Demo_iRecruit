package com.msil.irecruit.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="outlet")
public class Outlet {

	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer outletId;
	private String outletName;
	private String outletCode;
	@OneToOne 
	@JoinColumn(name="region_regionId")
	private Region Region;
	@OneToOne 
	@JoinColumn(name="State_stateId")
	private State State;
	@OneToOne 
	@JoinColumn(name="city_cityId")
	private City City;
	@OneToOne 
	@JoinColumn(name="parentDealer_parentDealerId")
	private ParentDealer parentDealer;
	
	@ManyToOne
    @JoinColumn(name = "dealer_id", nullable = false)
    private Dealer dealer;
	private String mapCode;
	
	
	private String location;


	 
	public Outlet() {
	}

	public Integer getOutletId() {
		return outletId;
	}

	public void setOutletId(Integer outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public Region getRegion() {
		return Region;
	}

	public void setRegion(Region region) {
		Region = region;
	}

	public State getState() {
		return State;
	}

	public void setState(State state) {
		State = state;
	}

	public City getCity() {
		return City;
	}

	public void setCity(City city) {
		City = city;
	}

	public ParentDealer getParentDealer() {
		return parentDealer;
	}

	public void setParentDealer(ParentDealer parentDealer) {
		this.parentDealer = parentDealer;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMapCode() {
		return mapCode;
	}

	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}	

	
}
