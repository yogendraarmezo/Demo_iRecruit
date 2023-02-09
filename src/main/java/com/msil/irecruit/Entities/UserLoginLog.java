package com.msil.irecruit.Entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name="user_login_log")
public class UserLoginLog {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "mspin")
	private String mspin;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "last_login")
	private Date lastLogin;
	
	public UserLoginLog() {
		
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

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
}


