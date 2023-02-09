package com.msil.irecruit.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "participant_attemp")
public class ParticipantAttemp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "access_key")
	private String accessKey;
	
	@Column(name = "test_id")
	private Integer testId;
	
	@Column(name = "attemp_id")
	private Integer attemptId;
	
	@Column(name = "section_id")
	private Integer sectionId;
	
	@Column(name = "question_id")
	private Integer questionId;
	
	@Column(name = "sel_option")
	private Integer selOption;
	
	@Column(name = "question_status")
	private Integer questionStatus;
	
	@Column(name = "category_id")
	private Integer categoryId;
	
	@Column(name = "difficulty_id")
	private Integer difficultyId;
	

	@Column(name = "marks")
    private Integer marks;
	
	@Column(name = "attempt_option")
    private String attemptOption;
    
	
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

	public Integer getSelOption() {
		return selOption;
	}

	public void setSelOption(Integer selOption) {
		this.selOption = selOption;
	}

	public Integer getQuestionStatus() {
		return questionStatus;
	}

	public void setQuestionStatus(Integer questionStatus) {
		this.questionStatus = questionStatus;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getDifficultyId() {
		return difficultyId;
	}

	public void setDifficultyId(Integer difficultyId) {
		this.difficultyId = difficultyId;
	}

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

	public String getAttemptOption() {
		return attemptOption;
	}

	public void setAttemptOption(String attemptOption) {
		this.attemptOption = attemptOption;
	}

	@Override
	public String toString() {
		return "ParticipantAttemp [Id=" + Id + ", accessKey=" + accessKey + ", testId=" + testId + ", attemptId="
				+ attemptId + ", sectionId=" + sectionId + ", questionId=" + questionId + ", selOption=" + selOption
				+ ", questionStatus=" + questionStatus + ", categoryId=" + categoryId + ", difficultyId=" + difficultyId
				+ ", marks=" + marks + ", attemptOption=" + attemptOption + "]";
	}
	
	



}
