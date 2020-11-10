package io.github.springsongs.modules.activiti.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SpringActModel {
	@NotBlank(message = "请填写模型名称")
	private String name;
	@NotBlank(message = "请填写模型标识")
	@Pattern(regexp = "^[A-Za-z]+$", message = "模型标识请填写字母")
	private String key;
	private String description;
	
	@NotBlank(message = "请选择模型分类")
	@Pattern(regexp = "^[A-Za-z]+$", message = "模型分类请填写字母")
	private String categoryCode;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	
}
