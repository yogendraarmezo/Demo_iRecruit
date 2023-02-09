package com.msil.irecruit.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "screen_toggle")
public class OnScreenToggle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long adminId;
	private String adminRole;
	private String module;
	private String srNo;
	private String candidateName;
	private String mspin;
	private String accesskey;
	private String designation;
	private String registrationDate;
	private String mobileNumber;
	private String assessmentDate;
	private String totalMark;
	private String obtainMark;
	private String percentage;
	private String assessmentStatus;
	private String assessmentReport;
	private String dataScience;
	private String interviewDate;
	private String interviewScore;
	private String registrationForm;
	private String praarambhStatus;
	private String finalDesignation;
	private String fsdmApproval;
	private String onHold;
	private String interviewForm;
	private String emailId;
	private String reactivate;
	private String status;
	private String assessmentCompletionDate;
	private String communication;
	private String designationCode;
	private String profile;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAdminId() {
		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	public String getAdminRole() {
		return adminRole;
	}
	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getMspin() {
		return mspin;
	}
	public void setMspin(String mspin) {
		this.mspin = mspin;
	}
	public String getAccesskey() {
		return accesskey;
	}
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAssessmentDate() {
		return assessmentDate;
	}
	public void setAssessmentDate(String assessmentDate) {
		this.assessmentDate = assessmentDate;
	}
	public String getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(String totalMark) {
		this.totalMark = totalMark;
	}
	public String getObtainMark() {
		return obtainMark;
	}
	public void setObtainMark(String obtainMark) {
		this.obtainMark = obtainMark;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getAssessmentStatus() {
		return assessmentStatus;
	}
	public void setAssessmentStatus(String assessmentStatus) {
		this.assessmentStatus = assessmentStatus;
	}
	public String getAssessmentReport() {
		return assessmentReport;
	}
	public void setAssessmentReport(String assessmentReport) {
		this.assessmentReport = assessmentReport;
	}
	public String getDataScience() {
		return dataScience;
	}
	public void setDataScience(String dataScience) {
		this.dataScience = dataScience;
	}
	public String getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getInterviewScore() {
		return interviewScore;
	}
	public void setInterviewScore(String interviewScore) {
		this.interviewScore = interviewScore;
	}
	public String getRegistrationForm() {
		return registrationForm;
	}
	public void setRegistrationForm(String registrationForm) {
		this.registrationForm = registrationForm;
	}
	public String getPraarambhStatus() {
		return praarambhStatus;
	}
	public void setPraarambhStatus(String praarambhStatus) {
		this.praarambhStatus = praarambhStatus;
	}
	public String getFinalDesignation() {
		return finalDesignation;
	}
	public void setFinalDesignation(String finalDesignation) {
		this.finalDesignation = finalDesignation;
	}
	public String getFsdmApproval() {
		return fsdmApproval;
	}
	public void setFsdmApproval(String fsdmApproval) {
		this.fsdmApproval = fsdmApproval;
	}
	public String getOnHold() {
		return onHold;
	}
	public void setOnHold(String onHold) {
		this.onHold = onHold;
	}
	public String getInterviewForm() {
		return interviewForm;
	}
	public void setInterviewForm(String interviewForm) {
		this.interviewForm = interviewForm;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getReactivate() {
		return reactivate;
	}
	public void setReactivate(String reactivate) {
		this.reactivate = reactivate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssessmentCompletionDate() {
		return assessmentCompletionDate;
	}
	public void setAssessmentCompletionDate(String assessmentCompletionDate) {
		this.assessmentCompletionDate = assessmentCompletionDate;
	}
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	public String getDesignationCode() {
		return designationCode;
	}
	public void setDesignationCode(String designationCode) {
		this.designationCode = designationCode;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	
}
