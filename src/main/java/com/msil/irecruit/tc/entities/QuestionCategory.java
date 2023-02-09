package com.msil.irecruit.tc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question_category")
public class QuestionCategory {
	
	@Id
	@Column(name = "srno", nullable = false)
	private Integer srNo;
	
	@Column(name = "level_name")
	private String levelName;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "table_name")
	private String tableName;

	public QuestionCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionCategory(Integer srNo, String levelName, String categoryName, String tableName) {
		super();
		this.srNo = srNo;
		this.levelName = levelName;
		this.categoryName = categoryName;
		this.tableName = tableName;
	}

	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "QuestionCategory [srNo=" + srNo + ", levelName=" + levelName + ", categoryName=" + categoryName
				+ ", tableName=" + tableName + "]";
	}
	
	

}
