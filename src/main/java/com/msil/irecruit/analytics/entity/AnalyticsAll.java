package com.msil.irecruit.analytics.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "analyticsAll")
public class AnalyticsAll {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long participantId;
	private Long dealerId; // It is used, To find by Login type
	private String accesskey; // any data related to part > for registered count
	private String registered; // regStatus = 1;
	private String assessments;
	private String passFailStatus; // pass or fail > pass count
	private String offerStatus; // yes or no > offers count
	private String recruitedStatus; // yes or no > rec count
	private String region;
	private String state;
	private String city;
	private String dealershipName;
	private String dealerCode;
	private String dealerName;
	private Date recruitDate; // part reg date
	private String recSource;
	private String candidateExperience;
	private String candidateExperienceNonAuto;
	private String assessmentReport; // percentage
	private String designationType; // Sales or Non-Sales
	private String age;
	private String gender;
	// Action Points Pending
	private String assessmentStatus; // completed or not completed > assessment count
	private String interviewStatus; // Interview is completed or not, if interview score!=null, So store interview
									// score
	private String documentUploadStatus; // Check document is uploaded or not
	private String prarambhStatus; // Prarambh Status
	private String fsdmApproval; // FSDM ApprovalStatus get here
	private String finalDesignation;
	private String mspin;

	// Saving data for popup
	private String firstName;
	private String middleName;
	private String lastName;
	private String mobile;
	private String marksObtained;
	private Date registrationDate;
	private Date assessmentDate;
	private Date interviewDate;
	private Date modifiedDate;

	public AnalyticsAll() {
	}

	

	public AnalyticsAll(Long id, Long participantId, Long dealerId, String accesskey, String registered,
			String assessments, String passFailStatus, String offerStatus, String recruitedStatus, String region,
			String state, String city, String dealershipName, String dealerCode, String dealerName, Date recruitDate,
			String recSource, String candidateExperience, String candidateExperienceNonAuto, String assessmentReport,
			String designationType, String age, String gender, String assessmentStatus, String interviewStatus,
			String documentUploadStatus, String prarambhStatus, String fsdmApproval, String finalDesignation,
			String mspin, String firstName, String middleName, String lastName, String mobile, String marksObtained,
			Date registrationDate, Date assessmentDate, Date interviewDate, Date modifiedDate) {
		super();
		this.id = id;
		this.participantId = participantId;
		this.dealerId = dealerId;
		this.accesskey = accesskey;
		this.registered = registered;
		this.assessments = assessments;
		this.passFailStatus = passFailStatus;
		this.offerStatus = offerStatus;
		this.recruitedStatus = recruitedStatus;
		this.region = region;
		this.state = state;
		this.city = city;
		this.dealershipName = dealershipName;
		this.dealerCode = dealerCode;
		this.dealerName = dealerName;
		this.recruitDate = recruitDate;
		this.recSource = recSource;
		this.candidateExperience = candidateExperience;
		this.candidateExperienceNonAuto = candidateExperienceNonAuto;
		this.assessmentReport = assessmentReport;
		this.designationType = designationType;
		this.age = age;
		this.gender = gender;
		this.assessmentStatus = assessmentStatus;
		this.interviewStatus = interviewStatus;
		this.documentUploadStatus = documentUploadStatus;
		this.prarambhStatus = prarambhStatus;
		this.fsdmApproval = fsdmApproval;
		this.finalDesignation = finalDesignation;
		this.mspin = mspin;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.marksObtained = marksObtained;
		this.registrationDate = registrationDate;
		this.assessmentDate = assessmentDate;
		this.interviewDate = interviewDate;
		this.modifiedDate = modifiedDate;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}

	public String getAssessments() {
		return assessments;
	}

	public void setAssessments(String assessments) {
		this.assessments = assessments;
	}

	public String getPassFailStatus() {
		return passFailStatus;
	}

	public void setPassFailStatus(String passFailStatus) {
		this.passFailStatus = passFailStatus;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

	public String getRecruitedStatus() {
		return recruitedStatus;
	}

	public void setRecruitedStatus(String recruitedStatus) {
		this.recruitedStatus = recruitedStatus;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getDealerCode() {
		return dealerCode;
	}
	
	public String getDealershipName() {
		return dealershipName;
	}
	
	public void setDealershipName(String dealershipName) {
		this.dealershipName = dealershipName;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Date getRecruitDate() {
		return recruitDate;
	}

	public void setRecruitDate(Date recruitDate) {
		this.recruitDate = recruitDate;
	}

	public String getRecSource() {
		return recSource;
	}

	public void setRecSource(String recSource) {
		this.recSource = recSource;
	}

	public String getCandidateExperience() {
		return candidateExperience;
	}

	public void setCandidateExperience(String candidateExperience) {
		this.candidateExperience = candidateExperience;
	}

	public String getCandidateExperienceNonAuto() {
		return candidateExperienceNonAuto;
	}

	public void setCandidateExperienceNonAuto(String candidateExperienceNonAuto) {
		this.candidateExperienceNonAuto = candidateExperienceNonAuto;
	}

	public String getAssessmentReport() {
		return assessmentReport;
	}

	public void setAssessmentReport(String assessmentReport) {
		this.assessmentReport = assessmentReport;
	}

	public String getDesignationType() {
		return designationType;
	}

	public void setDesignationType(String designationType) {
		this.designationType = designationType;
	}

	public String getFinalDesignation() {
		return finalDesignation;
	}

	public void setFinalDesignation(String finalDesignation) {
		this.finalDesignation = finalDesignation;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAssessmentStatus() {
		return assessmentStatus;
	}

	public void setAssessmentStatus(String assessmentStatus) {
		this.assessmentStatus = assessmentStatus;
	}

	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public String getDocumentUploadStatus() {
		return documentUploadStatus;
	}

	public void setDocumentUploadStatus(String documentUploadStatus) {
		this.documentUploadStatus = documentUploadStatus;
	}

	public String getPrarambhStatus() {
		return prarambhStatus;
	}

	public void setPrarambhStatus(String prarambhStatus) {
		this.prarambhStatus = prarambhStatus;
	}

	public String getFsdmApproval() {
		return fsdmApproval;
	}

	public void setFsdmApproval(String fsdmApproval) {
		this.fsdmApproval = fsdmApproval;
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

	public String getMarksObtained() {
		return marksObtained;
	}

	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMspin() {
		return mspin;
	}

	public void setMspin(String mspin) {
		this.mspin = mspin;
	}



	@Override
	public String toString() {
		return "AnalyticsAll [id=" + id + ", participantId=" + participantId + ", dealerId=" + dealerId + ", accesskey="
				+ accesskey + ", registered=" + registered + ", assessments=" + assessments + ", passFailStatus="
				+ passFailStatus + ", offerStatus=" + offerStatus + ", recruitedStatus=" + recruitedStatus + ", region="
				+ region + ", state=" + state + ", city=" + city + ", dealershipName=" + dealershipName
				+ ", dealerCode=" + dealerCode + ", dealerName=" + dealerName + ", recruitDate=" + recruitDate
				+ ", recSource=" + recSource + ", candidateExperience=" + candidateExperience
				+ ", candidateExperienceNonAuto=" + candidateExperienceNonAuto + ", assessmentReport="
				+ assessmentReport + ", designationType=" + designationType + ", age=" + age + ", gender=" + gender
				+ ", assessmentStatus=" + assessmentStatus + ", interviewStatus=" + interviewStatus
				+ ", documentUploadStatus=" + documentUploadStatus + ", prarambhStatus=" + prarambhStatus
				+ ", fsdmApproval=" + fsdmApproval + ", finalDesignation=" + finalDesignation + ", mspin=" + mspin
				+ ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", mobile="
				+ mobile + ", marksObtained=" + marksObtained + ", registrationDate=" + registrationDate
				+ ", assessmentDate=" + assessmentDate + ", interviewDate=" + interviewDate + ", modifiedDate="
				+ modifiedDate + "]";
	}

	

}
