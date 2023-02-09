package com.msil.irecruit.dms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dmsList")
public class ListDms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	private String listName;
	
	private String listCode;
	
	private String listDesc;
	
	public ListDms() {
	}

	public ListDms(Long id, String listName, String listCode, String listDesc) {
		super();
		this.id = id;
		this.listName = listName;
		this.listCode = listCode;
		this.listDesc = listDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListCode() {
		return listCode;
	}

	public void setListCode(String listCode) {
		this.listCode = listCode;
	}

	public String getListDesc() {
		return listDesc;
	}

	public void setListDesc(String listDesc) {
		this.listDesc = listDesc;
	}

	@Override
	public String toString() {
		return "ListDms [" + (id != null ? "id=" + id + ", " : "")
				+ (listName != null ? "listName=" + listName + ", " : "")
				+ (listCode != null ? "listCode=" + listCode + ", " : "")
				+ (listDesc != null ? "listDesc=" + listDesc : "") + "]";
	}
	
	

}
