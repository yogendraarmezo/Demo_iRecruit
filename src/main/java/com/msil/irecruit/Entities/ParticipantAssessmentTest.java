package com.msil.irecruit.Entities;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class ParticipantAssessmentTest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Integer Id;
	 
	@Column(name = "access_key")
	private String accessKey;
	
	@Column(name = "question_id")
	private Integer questionId;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "list_of_option")
	@OneToMany(fetch =  FetchType.EAGER ,mappedBy = "options", cascade = CascadeType.ALL)
	private List<OptionTest> listOfOption;
	
	@Column(name = "sel_option")
	private Integer selOption;
	
	@Column(name = "question_status")
	private Integer questionStatus;
	
	@Column(name = "test_id")
	private Integer testId;
	
	@Column(name = "section_id")
	private Integer sectionId;
	
	@Column(name = "attempt_id")
	private Integer attemptId;

	public ParticipantAssessmentTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParticipantAssessmentTest(Integer id, String accessKey, Integer questionId, String question,
			List<OptionTest> listOfOption, Integer selOption, Integer questionStatus, Integer testId, Integer sectionId,
			Integer attemptId) {
		super();
		Id = id;
		this.accessKey = accessKey;
		this.questionId = questionId;
		this.question = question;
		this.listOfOption = listOfOption;
		this.selOption = selOption;
		this.questionStatus = questionStatus;
		this.testId = testId;
		this.sectionId = sectionId;
		this.attemptId = attemptId;
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

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<OptionTest> getListOfOption() {
		return listOfOption;
	}

	public void setListOfOption(List<OptionTest> listOfOption) {
		this.listOfOption = listOfOption;
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

	public Integer getAttemptId() {
		return attemptId;
	}

	public void setAttemptId(Integer attemptId) {
		this.attemptId = attemptId;
	}

	@Override
	public String toString() {
		return "ParticipantAssessmentTest [Id=" + Id + ", accessKey=" + accessKey + ", questionId=" + questionId
				+ ", question=" + question + ", listOfOption=" + listOfOption + ", selOption=" + selOption
				+ ", questionStatus=" + questionStatus + ", testId=" + testId + ", sectionId=" + sectionId
				+ ", attemptId=" + attemptId + "]";
	}

	
	
	

}
