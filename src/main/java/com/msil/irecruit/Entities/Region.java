package com.msil.irecruit.Entities;

import java.util.ArrayList;
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
@Table(name="region")
public class Region {
	  	@Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		private Integer id;
		private String  regionCode;
	    private Boolean status;
	    
	    @ManyToOne
	    @JoinColumn(name = "fsdm_id", nullable = false)
	    private FSDM fsdm;
	    
	/*
	 * @OneToMany(fetch = FetchType.EAGER, mappedBy = "region")
	 * 
	 * @Cascade({CascadeType.ALL}) private List<State> state =new ArrayList<>();
	 */	
	    
		public Region() {
		}
		
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getRegionCode() {
			return regionCode;
		}
		public void setRegionCode(String regionCode) {
			this.regionCode = regionCode;
		}
		public Boolean getStatus() {
			return status;
		}
		public void setStatus(Boolean status) {
			this.status = status;
		}
		public FSDM getFsdm() {
			return fsdm;
		}
		public void setFsdm(FSDM fsdm) {
			this.fsdm = fsdm;
		}

	
	/*
	 * public List<State> getstate() { return state; } public void
	 * setstate(List<State> state) { this.state = state; }
	 */
	 
		
		
}
