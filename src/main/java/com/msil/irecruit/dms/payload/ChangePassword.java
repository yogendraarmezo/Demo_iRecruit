package com.msil.irecruit.dms.payload;

public class ChangePassword {
	
	private String AuthKey;
	
	private String UserName;
	
	private String OldPassword;
	
	private String NewPassword;
	
	public ChangePassword() {
		// TODO Auto-generated constructor stub
	}

	public ChangePassword(String authKey, String userName, String oldPassword, String newPassword) {
		super();
		AuthKey = authKey;
		UserName = userName;
		OldPassword = oldPassword;
		NewPassword = newPassword;
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

	public String getOldPassword() {
		return OldPassword;
	}

	public void setOldPassword(String oldPassword) {
		OldPassword = oldPassword;
	}

	public String getNewPassword() {
		return NewPassword;
	}

	public void setNewPassword(String newPassword) {
		NewPassword = newPassword;
	}

	@Override
	public String toString() {
		return "ChangePassword [AuthKey=" + AuthKey + ", UserName=" + UserName + ", OldPassword=" + OldPassword
				+ ", NewPassword=" + NewPassword + "]";
	}
	
}
