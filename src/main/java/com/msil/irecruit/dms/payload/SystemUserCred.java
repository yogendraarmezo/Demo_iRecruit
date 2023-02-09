package com.msil.irecruit.dms.payload;

public class SystemUserCred {
	
	private String AuthKey;
	
	private String UserName;
	
	private String Password;
	
	private String ConfirmPassword;
	
	public SystemUserCred() {
		// TODO Auto-generated constructor stub
	}

	public SystemUserCred(String authKey, String userName, String password, String confirmPassword) {
		super();
		AuthKey = authKey;
		UserName = userName;
		Password = password;
		ConfirmPassword = confirmPassword;
	}

	public String getAuthKey() {
		return AuthKey;
	}

	public void setAuthKey(String authKey) {
		AuthKey = authKey;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getConfirmPassword() {
		return ConfirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "SystemUserCred [AuthKey=" + AuthKey + ", UserName=" + UserName + ", Password=" + Password
				+ ", ConfirmPassword=" + ConfirmPassword + "]";
	}
	
	
	
}

