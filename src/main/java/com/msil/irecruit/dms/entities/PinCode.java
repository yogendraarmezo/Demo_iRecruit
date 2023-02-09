package com.msil.irecruit.dms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
@Entity
@Table(name = "dmsPinCode")
public class PinCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String pinCode;
	
	@ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
	//@Cascade({CascadeType.PERSIST})
	private City city;
	
	public PinCode() {
	}

	public PinCode(Long id, String pinCode, City city) {
		super();
		this.id = id;
		this.pinCode = pinCode;
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "PinCode [" + (id != null ? "id=" + id + ", " : "")
				+ (pinCode != null ? "pinCode=" + pinCode + ", " : "") + (city != null ? "city=" + city : "") + "]";
	}

	
}
