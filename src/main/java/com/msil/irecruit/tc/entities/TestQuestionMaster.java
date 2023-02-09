package com.msil.irecruit.tc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_question_master")
public class TestQuestionMaster {
	

	@Column(name = "srno")
	private Integer srNo;
	
	@Id
	@Column(name = "testid", nullable = false)
	private Integer testId;
	
	@Column(name = "section_id")
	private Integer sectionId;
	
	
	@Column(name = "categ_id")
	private Integer categId;
	
	@Column(name = "difficulty_id")
	private Integer difficultyId;
	
	@Column(name = "section_name")
	private String sectionName;
	
	
	@Column(name = "qid_list")
	private String qidList;
	
	@Column(name = "marks_dist_list")
	private String marksDistList;

	


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

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getQidList() {
		return qidList;
	}

	public void setQidList(String qidList) {
		this.qidList = qidList;
	}

	public String getMarksDistList() {
		return marksDistList;
	}

	public void setMarksDistList(String marksDistList) {
		this.marksDistList = marksDistList;
	}

	@Override
	public String toString() {
		return "TestCaseStudyMaster [srNo=" + srNo + ", testId=" + testId + ", sectionId=" + sectionId
				+ ", categId=" + categId + ", difficultyId=" + difficultyId + ", sectionName=" + sectionName
				+ ", qidList=" + qidList + ", marksDistList=" + marksDistList + "]";
	}
	
	
	
	

}


