package com.msil.irecruit.dms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dmsTehsilVillage")
public class TehsilVillage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String tehsilCode;
	private String tehsilDesc;
	private String villageCode;
	private String villageName;
	private String activeYN;
	private String stateCode;
	
	public TehsilVillage() {
	}

	public TehsilVillage(Long id, String tehsilCode, String tehsilDesc, String villageCode, String villageName,
			String activeYN, String stateCode) {
		super();
		this.id =id;
		this.tehsilCode = tehsilCode;
		this.tehsilDesc = tehsilDesc;
		this.villageCode = villageCode;
		this.villageName = villageName;
		this.activeYN = activeYN;
		this.stateCode=stateCode;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTehsilCode() {
		return tehsilCode;
	}

	public void setTehsilCode(String tehsilCode) {
		this.tehsilCode = tehsilCode;
	}

	public String getTehsilDesc() {
		return tehsilDesc;
	}

	public void setTehsilDesc(String tehsilDesc) {
		this.tehsilDesc = tehsilDesc;
	}

	public String getVillageCode() {
		return villageCode;
	}

	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getActiveYN() {
		return activeYN;
	}

	public void setActiveYN(String activeYN) {
		this.activeYN = activeYN;
	}
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Override
	public String toString() {
		return "TehsilVillage [id=" + id + ", tehsilCode=" + tehsilCode + ", tehsilDesc=" + tehsilDesc
				+ ", villageCode=" + villageCode + ", villageName=" + villageName + ", activeYN=" + activeYN
				+ ", stateCode=" + stateCode + "]";
	}


	

}
