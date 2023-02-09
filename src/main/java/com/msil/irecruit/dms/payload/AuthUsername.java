package com.msil.irecruit.dms.payload;

public class AuthUsername {
	
	private String AuthKey;
	
	private String UserName;
	
	public AuthUsername() {
		// TODO Auto-generated constructor stub
	}

	public AuthUsername(String authKey, String userName) {
		super();
		AuthKey = authKey;
		UserName = userName;
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

	@Override
	public String toString() {
		return "AuthUsername [AuthKey=" + AuthKey + ", UserName=" + UserName + "]";
	}
	
	
	

}
