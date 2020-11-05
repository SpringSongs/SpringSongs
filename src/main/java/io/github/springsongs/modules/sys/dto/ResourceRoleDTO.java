package io.github.springsongs.modules.sys.dto;

public class ResourceRoleDTO {
	

	public ResourceRoleDTO() {
		super();
	}

	public ResourceRoleDTO(String navigateUrl, String code) {
		super();
		this.navigateUrl = navigateUrl;
		this.code = code;
	}

	private String navigateUrl;

	public String getNavigateUrl() {
		return this.navigateUrl;
	}

	public void setNavigateUrl(String navigateUrl) {
		this.navigateUrl = navigateUrl;
	}

	private String code;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
