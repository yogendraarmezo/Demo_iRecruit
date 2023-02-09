package com.msil.irecruit.Entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name="dealer")
public class Dealer {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    private String mspin;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String dealerMapCode;
    
    @OneToMany(fetch = FetchType.EAGER,mappedBy="dealer")
	@Cascade({CascadeType.ALL})
	private List<Outlet> outlet;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "deactivation_date")
    private Date deactivationDate;
    private Boolean status;
    
    
	public Dealer() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	

	public List<Outlet> getOutlet() {
		return outlet;
	}

	public void setOutlet(List<Outlet> outlet) {
		this.outlet = outlet;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getDealerMapCode() {
		return dealerMapCode;
	}

	public void setDealerMapCode(String dealerMapCode) {
		this.dealerMapCode = dealerMapCode;
	}
   
	
	public Date getDeactivationDate() {
		return deactivationDate;
	}

	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Dealer [id=" + id + ", mspin=" + mspin + ", name=" + name + ", email=" + email + ", mobile=" + mobile
				+ ", password=" + password + ", outlet=" + outlet + "]";
	}


}
