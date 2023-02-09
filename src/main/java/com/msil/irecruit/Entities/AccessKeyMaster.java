package com.msil.irecruit.Entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="ACCESSKEY_MASTER")
public class AccessKeyMaster{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "access_key", nullable = false)
	private String accesskey;
		
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="create_date")  
	private Date createdDate = new Date();
	
	@Column(name = "dealer_id", nullable =false)
	private Long dealerId;
	
	@Column(name = "status")
	private String status= "A";
	
	@Column(name = "test_status",nullable = false)
	private Integer testStatus=0;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	public AccessKeyMaster()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(Integer testStatus) {
		this.testStatus = testStatus;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
