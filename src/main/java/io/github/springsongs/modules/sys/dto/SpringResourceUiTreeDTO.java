package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SpringResourceUiTreeDTO{
	protected String id;
	@JsonIgnore
	protected String parentId;
	private String text;
	private List<SpringResourceUiTreeDTO>  children = new ArrayList<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<SpringResourceUiTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<SpringResourceUiTreeDTO> children) {
		this.children = children;
	}
}
