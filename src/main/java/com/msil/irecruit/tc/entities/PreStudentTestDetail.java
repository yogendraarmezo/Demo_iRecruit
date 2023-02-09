package com.msil.irecruit.tc.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pre_student_test_detail")
public class PreStudentTestDetail {

	
	@Column(name = "srno", nullable = false)
	private Integer srNo;
	
	@Id
	@Column(name = "accesskey", nullable = false)
	private String accessKey;
	
	@Column(name = "testid", nullable = false)
	private Integer testId;
	
	@Column(name = "allotment_date")
	private Date allotmentdate;
	
	@Column(name = "sttime")
	private Date stTime;
	
	@Column(name = "reactivated_sttime")
	private Date reactivatedSttime;
	
	@Column(name = "entime")
	private Date enTime;
	
	@Column(name = "loginip")
	private String loginIp;
	
	@Column(name = "teststatus")
	private Integer testStatus;

	@Column(name = "currentsection")
	private Integer currentSection;
	
	@Column(name = "currentquestion")
	private Integer currentQuestion;
	
	@Column(name = "remtime")
	private Integer remTime;
	
	@Column(name = "ruser")
	private String rUser;
	
	@Column(name = "raddress")
	private String rAddress;
	
	@Column(name = "rhost")
	private String rHost;
	
	@Column(name = "reactivate_cnt")
	private Integer reactivateCnt;

	public PreStudentTestDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PreStudentTestDetail(Integer srNo, String accessKey, Integer testId, Date allotmentdate, Date stTime,
			Date reactivatedSttime, Date enTime, String loginIp, Integer testStatus, Integer currentSection,
			Integer currentQuestion, Integer remTime, String rUser, String rAddress, String rHost,
			Integer reactivateCnt) {
		super();
		this.srNo = srNo;
		this.accessKey = accessKey;
		this.testId = testId;
		this.allotmentdate = allotmentdate;
		this.stTime = stTime;
		this.reactivatedSttime = reactivatedSttime;
		this.enTime = enTime;
		this.loginIp = loginIp;
		this.testStatus = testStatus;
		this.currentSection = currentSection;
		this.currentQuestion = currentQuestion;
		this.remTime = remTime;
		this.rUser = rUser;
		this.rAddress = rAddress;
		this.rHost = rHost;
		this.reactivateCnt = reactivateCnt;
	}

	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
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

	public Date getAllotmentdate() {
		return allotmentdate;
	}

	public void setAllotmentdate(Date allotmentdate) {
		this.allotmentdate = allotmentdate;
	}

	public Date getStTime() {
		return stTime;
	}

	public void setStTime(Date stTime) {
		this.stTime = stTime;
	}

	public Date getReactivatedSttime() {
		return reactivatedSttime;
	}

	public void setReactivatedSttime(Date reactivatedSttime) {
		this.reactivatedSttime = reactivatedSttime;
	}

	public Date getEnTime() {
		return enTime;
	}

	public void setEnTime(Date enTime) {
		this.enTime = enTime;
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

	public Integer getRemTime() {
		return remTime;
	}

	public void setRemTime(Integer remTime) {
		this.remTime = remTime;
	}

	public String getrUser() {
		return rUser;
	}

	public void setrUser(String rUser) {
		this.rUser = rUser;
	}

	public String getrAddress() {
		return rAddress;
	}

	public void setrAddress(String rAddress) {
		this.rAddress = rAddress;
	}

	public String getrHost() {
		return rHost;
	}

	public void setrHost(String rHost) {
		this.rHost = rHost;
	}

	public Integer getReactivateCnt() {
		return reactivateCnt;
	}

	public void setReactivateCnt(Integer reactivateCnt) {
		this.reactivateCnt = reactivateCnt;
	}

	@Override
	public String toString() {
		return "PreStudentTestDetail [srNo=" + srNo + ", accessKey=" + accessKey + ", testId=" + testId
				+ ", allotmentdate=" + allotmentdate + ", stTime=" + stTime + ", reactivatedSttime=" + reactivatedSttime
				+ ", enTime=" + enTime + ", loginIp=" + loginIp + ", testStatus=" + testStatus + ", currentSection="
				+ currentSection + ", currentQuestion=" + currentQuestion + ", remTime=" + remTime + ", rUser=" + rUser
				+ ", rAddress=" + rAddress + ", rHost=" + rHost + ", reactivateCnt=" + reactivateCnt + "]";
	}
	
	
}
