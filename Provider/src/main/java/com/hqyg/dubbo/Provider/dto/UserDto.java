package com.hqyg.dubbo.Provider.dto;

import java.io.Serializable;

public class UserDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3050041035247430351L;

	private int id;
	
	private String username;
	
	private String password;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}
