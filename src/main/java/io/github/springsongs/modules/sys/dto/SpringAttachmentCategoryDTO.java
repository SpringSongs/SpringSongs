package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

import io.github.springsongs.modules.sys.domain.SpringAttachmentCategory;

public class SpringAttachmentCategoryDTO extends SpringAttachmentCategory {
	private List<SpringAttachmentCategoryDTO> children=new ArrayList<>();

	public List<SpringAttachmentCategoryDTO> getChildren() {
		return children;
	}

	public void setChildren(List<SpringAttachmentCategoryDTO> children) {
		this.children = children;
	}
	
}
