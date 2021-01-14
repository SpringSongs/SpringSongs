package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SpringAttachmentCategoryUiTreeDTO {
	private String id;
	@JsonIgnore
	private String parentId;
	private String text;
	private List<SpringAttachmentCategoryUiTreeDTO>  children = new ArrayList<>();
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
	public List<SpringAttachmentCategoryUiTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<SpringAttachmentCategoryUiTreeDTO> children) {
		this.children = children;
	}
}
