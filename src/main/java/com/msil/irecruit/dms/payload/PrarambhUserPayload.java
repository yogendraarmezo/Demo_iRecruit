package com.msil.irecruit.dms.payload;

public class PrarambhUserPayload {
	
	private String name;
	
	private String username;
	
	private String password;
	
	private String role;
	
	public PrarambhUserPayload() {
		}

	public PrarambhUserPayload(String name, String username, String password, String role) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "PrarambhUserPayload [name=" + name + ", username=" + username + ", password=" + password + ", role="
				+ role + "]";
	}
	
	

}
