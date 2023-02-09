package com.msil.irecruit.tc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_section_details_master")
public class TestSectionDetailsMaster {
	
	
	@Column(name = "srno")
	private Integer srNo;
	
	@Id
	@Column(name = "testid", nullable = false)
	private Integer testId;
	
	@Column(name = "section_id")
	private Integer sectionId;
	
	@Column(name = "section_name")
	private String sectionName;
	
	@Column(name = "total_questions")
	private Integer totalQuestions;
	
	@Column(name = "section_cutoff")
	private Integer sectionCutoff;

	public TestSectionDetailsMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestSectionDetailsMaster(Integer srNo, Integer testId, Integer sectionId, String sectionName,
			Integer totalQuestions, Integer sectionCutoff) {
		super();
		this.srNo = srNo;
		this.testId = testId;
		this.sectionId = sectionId;
		this.sectionName = sectionName;
		this.totalQuestions = totalQuestions;
		this.sectionCutoff = sectionCutoff;
	}

	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
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

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(Integer totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public Integer getSectionCutoff() {
		return sectionCutoff;
	}

	public void setSectionCutoff(Integer sectionCutoff) {
		this.sectionCutoff = sectionCutoff;
	}

	@Override
	public String toString() {
		return "TestSectionDetailsMaster [srNo=" + srNo + ", testId=" + testId + ", sectionId=" + sectionId
				+ ", sectionName=" + sectionName + ", totalQuestions=" + totalQuestions + ", sectionCutoff="
				+ sectionCutoff + "]";
	}

	
	
}
