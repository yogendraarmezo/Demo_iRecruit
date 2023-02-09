package com.msil.irecruit.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="state")
public class State {
	 	
	 
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer sateId;
	    private String stateName;
	    private String stateCode;
	    private String  regionCode;
	    private Boolean status;
	    
	    public Integer getSateId() {
	        return this.sateId;
	    }
	    
	    public void setSateId(final Integer sateId) {
	        this.sateId = sateId;
	    }
	    
	    public String getStateName() {
	        return this.stateName;
	    }
	    
	    public void setStateName(final String stateName) {
	        this.stateName = stateName;
	    }
	    
	    public String getStateCode() {
	        return this.stateCode;
	    }
	    
	    public void setStateCode(final String stateCode) {
	        this.stateCode = stateCode;
	    }
	    
	    
	    
	    public String getRegionCode() {
			return regionCode;
		}

		public void setRegionCode(String regionCode) {
			this.regionCode = regionCode;
		}

		public Boolean getStatus() {
	        return this.status;
	    }
	    
	    public void setStatus(final Boolean status) {
	        this.status = status;
	    }

	    
		
	 

		
}
