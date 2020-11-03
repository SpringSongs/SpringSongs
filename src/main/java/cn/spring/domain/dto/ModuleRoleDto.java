package cn.spring.domain.dto;

public class ModuleRoleDto {
	

	public ModuleRoleDto() {
		super();
	}

	public ModuleRoleDto(String navigateUrl, String code) {
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
