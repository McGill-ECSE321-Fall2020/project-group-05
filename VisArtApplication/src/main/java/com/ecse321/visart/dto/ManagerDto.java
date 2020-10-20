package com.ecse321.visart.dto;

public class ManagerDto {
	
	private UserDto user;

	public ManagerDto(UserDto user) {
		super();
		this.user = user;
	}
	
	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
	

}
