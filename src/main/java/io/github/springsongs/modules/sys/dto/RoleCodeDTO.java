package io.github.springsongs.modules.sys.dto;

public class RoleCodeDTO {
	private String id;
	private String code;

	
	public RoleCodeDTO(String id, String code) {
		super();
		this.id = id;
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
