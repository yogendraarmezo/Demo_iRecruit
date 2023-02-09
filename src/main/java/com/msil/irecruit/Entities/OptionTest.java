package com.msil.irecruit.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OptionTest {
	

	
	@ManyToOne(optional = false)
    @JoinColumn(name = "id", nullable = false)
	private List<ParticipantAssessmentTest> ParticipantAssesmentDetails;
	
	@Column(name = "option_id")
	private Integer optionId;
	
	@Column(name = "option")
	private String option;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "option_mark")
	private Integer option_Mark;
	
	
	

	public OptionTest() {
		super();

	}

	public OptionTest(Integer optionId, String option, Integer status, Integer option_Mark) {
		super();
		this.optionId = optionId;
		this.option = option;
		this.status = status;
		this.option_Mark = option_Mark;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOption_Mark() {
		return option_Mark;
	}

	public void setOption_Mark(Integer option_Mark) {
		this.option_Mark = option_Mark;
	}

	@Override
	public String toString() {
		return "OptionTest [optionId=" + optionId + ", option=" + option + ", status=" + status + ", option_Mark="
				+ option_Mark + "]";
	}
	

}
