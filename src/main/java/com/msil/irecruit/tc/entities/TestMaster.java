package com.msil.irecruit.tc.entities;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_master")
public class TestMaster {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "testid", nullable = false)
	private Integer testId;
	
	@Column(name = "test_name", nullable = false)
	private String testName;
	
	@Column(name = "total_question")
	private Integer totalQuestion;
	
	@Column(name = "total_time")
	private String totalTime;
	
	@Column(name = "result_display")
	private Integer resultDisplay;
	
	@Column(name = "ip_restriction")
	private Integer ipRestriction;
	
	@Column(name = "total_section")
	private Integer totalSection;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "candidates_appeared")
	private Integer candidatesAppeared;
	
	@Column(name = "total_marks",columnDefinition = "NUMERIC")
	private Integer totalMarks;
	
	@Column(name = "passing_percent", columnDefinition = "NUMERIC")
	private Integer passingPercent;
	
	@Column(name = "sec_cutoff_flag")
	private Integer secCutOffFlag;
	
	

	public TestMaster() {
		super();
	}
	
	

	public TestMaster(Integer testId, String testName, Integer totalQuestion, String totalTime, Integer resultDisplay,
			Integer ipRestriction, Integer totalSection, Date createDate, Integer status, Integer candidatesAppeared,
			Integer totalMarks, Integer passingPercent, Integer secCutOffFlag) {
		super();
		this.testId = testId;
		this.testName = testName;
		this.totalQuestion = totalQuestion;
		this.totalTime = totalTime;
		this.resultDisplay = resultDisplay;
		this.ipRestriction = ipRestriction;
		this.totalSection = totalSection;
		this.createDate = createDate;
		this.status = status;
		this.candidatesAppeared = candidatesAppeared;
		this.totalMarks = totalMarks;
		this.passingPercent = passingPercent;
		this.secCutOffFlag = secCutOffFlag;
	}

	


	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Integer getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(Integer totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getResultDisplay() {
		return resultDisplay;
	}

	public void setResultDisplay(Integer resultDisplay) {
		this.resultDisplay = resultDisplay;
	}

	public Integer getIpRestriction() {
		return ipRestriction;
	}

	public void setIpRestriction(Integer ipRestriction) {
		this.ipRestriction = ipRestriction;
	}

	public Integer getTotalSection() {
		return totalSection;
	}

	public void setTotalSection(Integer totalSection) {
		this.totalSection = totalSection;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCandidatesAppeared() {
		return candidatesAppeared;
	}

	public void setCandidatesAppeared(Integer candidatesAppeared) {
		this.candidatesAppeared = candidatesAppeared;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Integer getPassingPercent() {
		return passingPercent;
	}

	public void setPassingPercent(Integer passingPercent) {
		this.passingPercent = passingPercent;
	}

	public Integer getSecCutOffFlag() {
		return secCutOffFlag;
	}

	public void setSecCutOffFlag(Integer secCutOffFlag) {
		this.secCutOffFlag = secCutOffFlag;
	}



	@Override
	public String toString() {
		return "TestMaster [testId=" + testId + ", testName=" + testName + ", totalQuestion=" + totalQuestion
				+ ", totalTime=" + totalTime + ", resultDisplay=" + resultDisplay + ", ipRestriction=" + ipRestriction
				+ ", totalSection=" + totalSection + ", createDate=" + createDate + ", status=" + status
				+ ", candidatesAppeared=" + candidatesAppeared + ", totalMarks=" + totalMarks + ", passingPercent="
				+ passingPercent + ", secCutOffFlag=" + secCutOffFlag + "]";
	}
	
	

	
	

}
