package io.github.springsongs.modules.sys.dto;

import java.util.List;

public class UserInfoDTO {
	private List<MenuDTO> menuDTOs;
	private List<String> roles;

	

	

	public List<MenuDTO> getMenuDTOs() {
		return menuDTOs;
	}

	public void setMenuDTOs(List<MenuDTO> menuDTOs) {
		this.menuDTOs = menuDTOs;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
