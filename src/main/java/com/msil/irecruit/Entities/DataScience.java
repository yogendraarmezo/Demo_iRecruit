package com.msil.irecruit.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="data_science")
public class DataScience {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "access_key", nullable = false)
	private String accesskey;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "middle_name")
	private String middleName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "profile", nullable = false)
	private String profile;
	@Column(name = "designation", nullable = false)
	private String designation;
	@Column(name = "primary_language")
	private String primaryLanguage;
    @Column(name = "secondary_language")
	private String secondaryLanguage;
    @Column(name = "martial_status")
    private String martialStatus;
    @Column(name = "worked_with_msil_before")
    private String workedWithMSILBefore;
    @Column(name = "old_mspin")
    private String oldMspin;
    @Column(name = "msil_exp")
	private String msilExp;
    @Column(name = "auto_Industry_experience")
    private String autoIndustryExperience;
    @Column(name = "knows_driving")
    private String knowDriving;
    @Column(name = "gender")
    private String gender;
    @Column(name = "highest_qualification")
    private String highestQualification;
    @Column(name = "DL")
  	private String DL;
    @Column(name = "own_two_Wheeler")
    private String ownTwoWheeler;
    @Column(name = "mds_certified")
    private String mdsCertified;
    @Column(name = "dealer_code")
    private String dealerCode;
    @Column(name = "total")
    private String total;
    @Column(name = "age")
    private String age;
    @Column(name = "data_science_result")
    private String dataScienceResult;
    @Column(name = "reason")
    private String reason;
    @Column(name = "data_science_prediction")
    private String dataSciencePrediction;
    @Column(name = "data_science_reference_id")
    private String dataScienceReferenceId;
    @Column(name = "interview_status")
    private String interviewStatus;
    @Column(name = "residence_of")
    private String residenceOf;
    @Column(name = "status")
    private String status;
    
    
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPrimaryLanguage() {
		return primaryLanguage;
	}
	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}
	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}
	public void setSecondaryLanguage(String secondaryLanguage) {
		this.secondaryLanguage = secondaryLanguage;
	}
	public String getMartialStatus() {
		return martialStatus;
	}
	public void setMartialStatus(String martialStatus) {
		this.martialStatus = martialStatus;
	}
	public String getWorkedWithMSILBefore() {
		return workedWithMSILBefore;
	}
	public void setWorkedWithMSILBefore(String workedWithMSILBefore) {
		this.workedWithMSILBefore = workedWithMSILBefore;
	}
	public String getOldMspin() {
		return oldMspin;
	}
	public void setOldMspin(String oldMspin) {
		this.oldMspin = oldMspin;
	}
	public String getMsilExp() {
		return msilExp;
	}
	public void setMsilExp(String msilExp) {
		this.msilExp = msilExp;
	}
	public String getAutoIndustryExperience() {
		return autoIndustryExperience;
	}
	public void setAutoIndustryExperience(String autoIndustryExperience) {
		this.autoIndustryExperience = autoIndustryExperience;
	}
	public String getKnowDriving() {
		return knowDriving;
	}
	public void setKnowDriving(String knowDriving) {
		this.knowDriving = knowDriving;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHighestQualification() {
		return highestQualification;
	}
	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}
	public String getDL() {
		return DL;
	}
	public void setDL(String dL) {
		DL = dL;
	}
	public String getOwnTwoWheeler() {
		return ownTwoWheeler;
	}
	public void setOwnTwoWheeler(String ownTwoWheeler) {
		this.ownTwoWheeler = ownTwoWheeler;
	}
	public String getMdsCertified() {
		return mdsCertified;
	}
	public void setMdsCertified(String mdsCertified) {
		this.mdsCertified = mdsCertified;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getDataScienceResult() {
		return dataScienceResult;
	}
	public void setDataScienceResult(String dataScienceResult) {
		this.dataScienceResult = dataScienceResult;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDataSciencePrediction() {
		return dataSciencePrediction;
	}
	public void setDataSciencePrediction(String dataSciencePrediction) {
		this.dataSciencePrediction = dataSciencePrediction;
	}
	public String getDataScienceReferenceId() {
		return dataScienceReferenceId;
	}
	public void setDataScienceReferenceId(String dataScienceReferenceId) {
		this.dataScienceReferenceId = dataScienceReferenceId;
	}
	public String getInterviewStatus() {
		return interviewStatus;
	}
	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}
	
	
	public String getResidenceOf() {
		return residenceOf;
	}
	public void setResidenceOf(String residenceOf) {
		this.residenceOf = residenceOf;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
  
   
	
}
