package com.msil.irecruit.tc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pre_student_score")
public class PreStudentScore {
	
	@Column(name = "srno", nullable = false)
	private Integer srNo;
	
	@Id
	@Column(name = "accesskey", nullable = false)
	private String accessKey;
	
	@Column(name = "testid", nullable = false)
	private Integer testId;
	
	
	@Column(name = "section_1",columnDefinition = "NUMERIC")
	private Integer section1;

	@Column(name = "section_2",columnDefinition = "NUMERIC")
	private Integer section2;
	
	@Column(name = "section_3",columnDefinition = "NUMERIC")
	private Integer section3;
	
	@Column(name = "section_4",columnDefinition = "NUMERIC")
	private Integer section4;
	
	@Column(name = "section_5",columnDefinition = "NUMERIC")
	private Integer section5;
	
	@Column(name = "section_6",columnDefinition = "NUMERIC")
	private Integer section6;
	
	@Column(name = "section_7",columnDefinition = "NUMERIC")
	private Integer section7;
	
	@Column(name = "section_8",columnDefinition = "NUMERIC")
	private Integer section8;
	
	@Column(name = "section_9",columnDefinition = "NUMERIC")
	private Integer section9;
	
	@Column(name = "section_10",columnDefinition = "NUMERIC")
	private Integer section10;
	
	@Column(name = "total_score",columnDefinition = "NUMERIC")
	private Integer totalScore;
	
	@Column(name = "status")
	private String status;

	public PreStudentScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PreStudentScore(Integer srNo, String accessKey, Integer testId, Integer section1, Integer section2,
			Integer section3, Integer section4, Integer section5, Integer section6, Integer section7, Integer section8,
			Integer section9, Integer section10, Integer totalScore, String status) {
		super();
		this.srNo = srNo;
		this.accessKey = accessKey;
		this.testId = testId;
		this.section1 = section1;
		this.section2 = section2;
		this.section3 = section3;
		this.section4 = section4;
		this.section5 = section5;
		this.section6 = section6;
		this.section7 = section7;
		this.section8 = section8;
		this.section9 = section9;
		this.section10 = section10;
		this.totalScore = totalScore;
		this.status = status;
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

	public Integer getSection1() {
		return section1;
	}

	public void setSection1(Integer section1) {
		this.section1 = section1;
	}

	public Integer getSection2() {
		return section2;
	}

	public void setSection2(Integer section2) {
		this.section2 = section2;
	}

	public Integer getSection3() {
		return section3;
	}

	public void setSection3(Integer section3) {
		this.section3 = section3;
	}

	public Integer getSection4() {
		return section4;
	}

	public void setSection4(Integer section4) {
		this.section4 = section4;
	}

	public Integer getSection5() {
		return section5;
	}

	public void setSection5(Integer section5) {
		this.section5 = section5;
	}

	public Integer getSection6() {
		return section6;
	}

	public void setSection6(Integer section6) {
		this.section6 = section6;
	}

	public Integer getSection7() {
		return section7;
	}

	public void setSection7(Integer section7) {
		this.section7 = section7;
	}

	public Integer getSection8() {
		return section8;
	}

	public void setSection8(Integer section8) {
		this.section8 = section8;
	}

	public Integer getSection9() {
		return section9;
	}

	public void setSection9(Integer section9) {
		this.section9 = section9;
	}

	public Integer getSection10() {
		return section10;
	}

	public void setSection10(Integer section10) {
		this.section10 = section10;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PreStudentScore [srNo=" + srNo + ", accessKey=" + accessKey + ", testId=" + testId + ", section1="
				+ section1 + ", section2=" + section2 + ", section3=" + section3 + ", section4=" + section4
				+ ", section5=" + section5 + ", section6=" + section6 + ", section7=" + section7 + ", section8="
				+ section8 + ", section9=" + section9 + ", section10=" + section10 + ", totalScore=" + totalScore
				+ ", status=" + status + "]";
	}
	

}
