package com.msil.irecruit.tc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pre_student_attempt")
public class PreStudentAttempt {
	
	@Column(name = "srno", nullable = false)
	private Integer srNo;
	
	@Id
	@Column(name = "accesskey", nullable = false)
	private String accessKey;
	
	@Column(name = "testid", nullable = false)
	private Integer testId;
	
	@Column(name = "sectionid")
	private Integer sectionId;
	
	@Column(name = "questionid")
	private Integer questionId;
	
	@Column(name = "seloption")
	private String selOption;
	
	@Column(name = "question_status")
	private Integer questionStatus;
	
	@Column(name = "categ_id")
	private Integer categId;
	
	@Column(name = "difficulty_id")
	private Integer difficultyId;

	public PreStudentAttempt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PreStudentAttempt(Integer srNo, String accessKey, Integer testId, Integer sectionId, Integer questionId,
			String selOption, Integer questionStatus, Integer categId, Integer difficultyId) {
		super();
		this.srNo = srNo;
		this.accessKey = accessKey;
		this.testId = testId;
		this.sectionId = sectionId;
		this.questionId = questionId;
		this.selOption = selOption;
		this.questionStatus = questionStatus;
		this.categId = categId;
		this.difficultyId = difficultyId;
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

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getSelOption() {
		return selOption;
	}

	public void setSelOption(String selOption) {
		this.selOption = selOption;
	}

	public Integer getQuestionStatus() {
		return questionStatus;
	}

	public void setQuestionStatus(Integer questionStatus) {
		this.questionStatus = questionStatus;
	}

	public Integer getCategId() {
		return categId;
	}

	public void setCategId(Integer categId) {
		this.categId = categId;
	}

	public Integer getDifficultyId() {
		return difficultyId;
	}

	public void setDifficultyId(Integer difficultyId) {
		this.difficultyId = difficultyId;
	}

	@Override
	public String toString() {
		return "PreStudentAttempt [srNo=" + srNo + ", accessKey=" + accessKey + ", testId=" + testId + ", sectionId="
				+ sectionId + ", questionId=" + questionId + ", selOption=" + selOption + ", questionStatus="
				+ questionStatus + ", categId=" + categId + ", difficultyId=" + difficultyId + "]";
	}
	

	
	
}
