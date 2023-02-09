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
@Table(name="fsdm_notification")
public class FSDMNotification {

	    @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		private Integer id;
		private String  accesskey;
		private String  candidateName;
		private String  dealerName;
	    private Integer fsdmId;
	    private Long    dealerId;;
	    private String  status;
	    private String  mspinStatus;
	    private String  mspin;
	    private String  outletCode;
	    private String  outletName;
	    private String  region;
	    private String  applicationStatus;
	    private String  designation;
	    private String  registraionDate;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "notification_date")
	    private Date    notificationDate;
	    
	    public FSDMNotification() {
	    	
	    }

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getAccesskey() {
			return accesskey;
		}

		public void setAccesskey(String accesskey) {
			this.accesskey = accesskey;
		}

		public String getCandidateName() {
			return candidateName;
		}

		public void setCandidateName(String candidateName) {
			this.candidateName = candidateName;
		}

		public String getDealerName() {
			return dealerName;
		}

		public void setDealerName(String dealerName) {
			this.dealerName = dealerName;
		}

		public Integer getFsdmId() {
			return fsdmId;
		}

		public void setFsdmId(Integer fsdmId) {
			this.fsdmId = fsdmId;
		}

		public Long getDealerId() {
			return dealerId;
		}

		public void setDealerId(Long dealerId) {
			this.dealerId = dealerId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getMspinStatus() {
			return mspinStatus;
		}

		public void setMspinStatus(String mspinStatus) {
			this.mspinStatus = mspinStatus;
		}

		public String getMspin() {
			return mspin;
		}

		public void setMspin(String mspin) {
			this.mspin = mspin;
		}

		public String getOutletCode() {
			return outletCode;
		}

		public void setOutletCode(String outletCode) {
			this.outletCode = outletCode;
		}

		public String getOutletName() {
			return outletName;
		}

		public void setOutletName(String outletName) {
			this.outletName = outletName;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getApplicationStatus() {
			return applicationStatus;
		}

		public void setApplicationStatus(String applicationStatus) {
			this.applicationStatus = applicationStatus;
		}

		public Date getNotificationDate() {
			return notificationDate;
		}

		public void setNotificationDate(Date notificationDate) {
			this.notificationDate = notificationDate;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public String getRegistraionDate() {
			return registraionDate;
		}

		public void setRegistraionDate(String registraionDate) {
			this.registraionDate = registraionDate;
		}		
	    
}
