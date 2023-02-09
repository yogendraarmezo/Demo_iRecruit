package com.msil.irecruit.dms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dmsPrarambhStatus")
public class PrarambhStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String mspin;
	
	private String prarambhStatus;
	
	private Date completionDateTime;
	
	public PrarambhStatus() {
		}

	public PrarambhStatus(Long id, String mspin ,String prarambhStatus, Date completionDateTime) {
		super();
		this.id = id;
		this.prarambhStatus = prarambhStatus;
		this.completionDateTime = completionDateTime;
		this.mspin=mspin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMspin() {
		return mspin;
	}
	
	public void setMspin(String mspin) {
		this.mspin = mspin;
	}

	public String getPrarambhStatus() {
		return prarambhStatus;
	}

	public void setPrarambhStatus(String prarambhStatus) {
		this.prarambhStatus = prarambhStatus;
	}

	public Date getCompletionDateTime() {
		return completionDateTime;
	}

	public void setCompletionDateTime(Date completionDateTime) {
		this.completionDateTime = completionDateTime;
	}

	@Override
	public String toString() {
		return "PrarambhStatus [id=" + id + ", mspin="+mspin+", prarambhStatus=" + prarambhStatus + ", completionDateTime="
				+ completionDateTime + "]";
	}
	
	

}
