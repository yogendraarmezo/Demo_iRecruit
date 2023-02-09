package com.msil.irecruit.dms.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name = "dmsCity")
public class City {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String cityCode;
	
	private String cityDesc;
	
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    //@Cascade({CascadeType.PERSIST})
    private StateDms stateDms;
    
    @OneToMany(fetch = FetchType.EAGER, 
    		mappedBy = "city")
    @Cascade({CascadeType.ALL})
    private List<PinCode> pinCodes;
    
    public City() {
	}

	public City(Long id, String cityCode, String cityDesc, StateDms stateDms, List<PinCode> pinCodes) {
		super();
		this.id = id;
		this.cityCode = cityCode;
		this.cityDesc = cityDesc;
		this.stateDms = stateDms;
		this.pinCodes = pinCodes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public StateDms getState() {
		return stateDms;
	}

	public void setState(StateDms stateDms) {
		this.stateDms = stateDms;
	}

	public List<PinCode> getPinCodes() {
		return pinCodes;
	}

	public void setPinCodes(List<PinCode> pinCodes) {
		this.pinCodes = pinCodes;
	}

	@Override
	public String toString() {
		return "City [" + (id != null ? "id=" + id + ", " : "")
				+ (cityCode != null ? "cityCode=" + cityCode + ", " : "")
				+ (cityDesc != null ? "cityDesc=" + cityDesc + ", " : "")
				+ (stateDms != null ? "state=" + stateDms + ", " : "") + (pinCodes != null ? "pinCodes=" + pinCodes : "")
				+ "]";
	}

    
}
