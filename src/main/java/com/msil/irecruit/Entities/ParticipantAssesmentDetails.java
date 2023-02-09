package com.msil.irecruit.Entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "participant_assesment_details")
public class ParticipantAssesmentDetails {
	
	
//	private ParticipantAttemp participantAttemp;
//
//	public ParticipantAttemp getParticipantAttemp() {
//		return participantAttemp;
//	}
//
//	public void setParticipantAttemp(ParticipantAttemp participantAttemp) {
//		this.participantAttemp = participantAttemp;
//	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "access_key")
	private String accessKey;
	
	@Column(name = "test_id")
	private Integer testId;
	
	@Column(name = "attempt_id")
	private Integer attemptId;
	
	@Column(name = "allotment_date")
	private Date allotmentDate;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "end_time")
	private Date endTime;
	
	@Column(name = "login_ip")
	private String loginIp;
	
	
	@Column(name = "test_status")
	private Integer testStatus;
	
	@Column(name = "current_section")
	private Integer currentSection;
	
	@Column(name = "current_question")
	private Integer currentQuestion;
	
	@Column(name = "current_case_question")
	private Integer currentCaseQuestion;
	
	@Column(name ="current_sub_topicid") 
	private Integer currentSubTopicId;
	
	@Column(name = "next_diffid")
	private Integer nextDiffId;
	
	@Column(name = "rem_time")
	private Integer remindertime;
	
	@Column(name = "puser")
	private String puser;

	@Column(name = "address")
	private String address;

	@Column(name = "host")
	private String host;
	
	@Column(name = "attempt_activate_flag")
	private Integer attemptActivateFlag;

	@Column(name = "count")
	private Integer count;
	
	@Column(name = "iscandidate_online")
	private char isCandidateOnline;

	@Column(name = "iscandidate_block")
	private char isCandidateBlock;

	@Column(name = "mobile_login_status")
	private Integer mobileLoginStatus;

	public String getPuser() {
		return puser;
	}

	public void setPuser(String puser) {
		this.puser = puser;
	}

	public Integer getMobileLoginStatus() {
		return mobileLoginStatus;
	}

	public void setMobileLoginStatus(Integer mobileLoginStatus) {
		this.mobileLoginStatus = mobileLoginStatus;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public Integer getAttemptId() {
		return attemptId;
	}

	public void setAttemptId(Integer attemptId) {
		this.attemptId = attemptId;
	}

	public Date getAllotmentDate() {
		return allotmentDate;
	}

	public void setAllotmentDate(Date allotmentDate) {
		this.allotmentDate = allotmentDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(Integer testStatus) {
		this.testStatus = testStatus;
	}

	public Integer getCurrentSection() {
		return currentSection;
	}

	public void setCurrentSection(Integer currentSection) {
		this.currentSection = currentSection;
	}

	public Integer getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Integer currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public Integer getCurrentCaseQuestion() {
		return currentCaseQuestion;
	}

	public void setCurrentCaseQuestion(Integer currentCaseQuestion) {
		this.currentCaseQuestion = currentCaseQuestion;
	}

	public Integer getCurrentSubTopicId() {
		return currentSubTopicId;
	}

	public void setCurrentSubTopicId(Integer currentSubTopicId) {
		this.currentSubTopicId = currentSubTopicId;
	}

	public Integer getNextDiffId() {
		return nextDiffId;
	}

	public void setNextDiffId(Integer nextDiffId) {
		this.nextDiffId = nextDiffId;
	}

	public Integer getRemindertime() {
		return remindertime;
	}

	public void setRemindertime(Integer remindertime) {
		this.remindertime = remindertime;
	}

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getAttemptActivateFlag() {
		return attemptActivateFlag;
	}

	public void setAttemptActivateFlag(Integer attemptActivateFlag) {
		this.attemptActivateFlag = attemptActivateFlag;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public char getIsCandidateOnline() {
		return isCandidateOnline;
	}

	public void setIsCandidateOnline(char isCandidateOnline) {
		this.isCandidateOnline = isCandidateOnline;
	}

	public char getIsCandidateBlock() {
		return isCandidateBlock;
	}

	public void setIsCandidateBlock(char isCandidateBlock) {
		this.isCandidateBlock = isCandidateBlock;
	}

	public Integer getMobileloginstatus() {
		return mobileLoginStatus;
	}

	public void setMobileloginstatus(Integer mobileloginstatus) {
		this.mobileLoginStatus = mobileloginstatus;
	}

	@Override
	public String toString() {
		return "ParticipantTestDetails [Id=" + Id + ", accessKey=" + accessKey + ", testId=" + testId + ", attemptId="
				+ attemptId + ", allotmentDate=" + allotmentDate + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", loginIp=" + loginIp + ", testStatus=" + testStatus + ", currentSection=" + currentSection
				+ ", currentQuestion=" + currentQuestion + ", currentCaseQuestion=" + currentCaseQuestion
				+ ", currentSubTopicId=" + currentSubTopicId + ", nextDiffId=" + nextDiffId + ", remindertime="
				+ remindertime + ", puser=" + puser + ", address=" + address + ", host=" + host + ", attemptActivateFlag="
				+ attemptActivateFlag + ", count=" + count + ", isCandidateOnline=" + isCandidateOnline
				+ ", isCandidateBlock=" + isCandidateBlock + ", mobileloginstatus=" + mobileLoginStatus + "]";
	}




	
	
	
	

}
