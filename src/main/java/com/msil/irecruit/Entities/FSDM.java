package com.msil.irecruit.Entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;



@Entity
@Table(name="fsdm")
public class FSDM {
 @Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id;
private String mspin;
private String name;
private String email;
private String mobile;
private Boolean status;
private String password;

@OneToMany(fetch = FetchType.EAGER,mappedBy="fsdm")
@Cascade({CascadeType.ALL})
private List<Region> region;



public FSDM() {
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getMspin() {
	return mspin;
}
public void setMspin(String mspin) {
	this.mspin = mspin;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public List<Region> getRegion() {
	return region;
}
public void setRegion(List<Region> region) {
	this.region = region;
}
public Boolean getStatus() {
	return status;
}
public void setStatus(Boolean status) {
	this.status = status;
}

public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

}
