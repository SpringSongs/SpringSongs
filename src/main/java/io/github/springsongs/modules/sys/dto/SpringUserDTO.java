package io.github.springsongs.modules.sys.dto;

import javax.persistence.Transient;

import io.github.springsongs.modules.sys.domain.SpringUser;

public class SpringUserDTO extends SpringUser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009449152333909065L;
	@Transient
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
