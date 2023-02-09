package com.msil.irecruit.Entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "participant_score")
public class ParticipantScore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Integer Id;
	
	@Column(name = "access_key")
	private String accessKey;
	
	@Column(name = "test_id")
	private Integer testId;
	
	@Column(name = "attempt_id")
	private Integer attemptId;
	
	@Column(name = "SECTION_1")
	private double section1;

	@Column(name = "SECTION_2")
	private double section2;
	
	@Column(name = "SECTION_3")
	private double section3;
	
	@Column(name = "SECTION_4")
	private double section4;
	
	@Column(name = "SECTION_5")
	private double section5;
	
	@Column(name = "SECTION_6")
	private double section6;
	
	@Column(name = "SECTION_7")
	private double section7;
	
	@Column(name = "SECTION_8")
	private double section8;
	
	@Column(name = "SECTION_9")
	private double section9;
	
	@Column(name = "SECTION_10")
	private double section10;
	
	@Column(name = "total_score")
	private double totalScore;
	
	@Column(name = "subjective_score")
	private double subjectiveScore;
	
	@Column(name = "eval_date")
	private Date evalDate;
	
	@Column(name = "status")
	private String status;

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

	public double getSection1() {
		return section1;
	}

	public void setSection1(double section1) {
		this.section1 = section1;
	}

	public double getSection2() {
		return section2;
	}

	public void setSection2(double section2) {
		this.section2 = section2;
	}

	public double getSection3() {
		return section3;
	}

	public void setSection3(double section3) {
		this.section3 = section3;
	}

	public double getSection4() {
		return section4;
	}

	public void setSection4(double section4) {
		this.section4 = section4;
	}

	public double getSection5() {
		return section5;
	}

	public void setSection5(double section5) {
		this.section5 = section5;
	}

	public double getSection6() {
		return section6;
	}

	public void setSection6(double section6) {
		this.section6 = section6;
	}

	public double getSection7() {
		return section7;
	}

	public void setSection7(double section7) {
		this.section7 = section7;
	}

	public double getSection8() {
		return section8;
	}

	public void setSection8(double section8) {
		this.section8 = section8;
	}

	public double getSection9() {
		return section9;
	}

	public void setSection9(double section9) {
		this.section9 = section9;
	}

	public double getSection10() {
		return section10;
	}

	public void setSection10(double section10) {
		this.section10 = section10;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public double getSubjectiveScore() {
		return subjectiveScore;
	}

	public void setSubjectiveScore(double subjectiveScore) {
		this.subjectiveScore = subjectiveScore;
	}

	public Date getEvalDate() {
		return evalDate;
	}

	public void setEvalDate(Date evalDate) {
		this.evalDate = evalDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ParticipantScore [Id=" + Id + ", accessKey=" + accessKey + ", testId=" + testId + ", attemptId="
				+ attemptId + ", section1=" + section1 + ", section2=" + section2 + ", section3=" + section3
				+ ", section4=" + section4 + ", section5=" + section5 + ", section6=" + section6 + ", section7="
				+ section7 + ", section8=" + section8 + ", section9=" + section9 + ", section10=" + section10
				+ ", totalScore=" + totalScore + ", subjectiveScore=" + subjectiveScore + ", evalDate=" + evalDate
				+ ", status=" + status + "]";
	}
	
	
	

}
