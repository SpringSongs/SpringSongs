package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import io.github.springsongs.modules.sys.domain.SpringArticleCategory;

public class SpringArticleCategoryDTO extends SpringArticleCategory {
	
	@Transient
	private String text;
	
	public String getText() {
		return this.getTitle();
	}
	
	
	private List<SpringArticleCategoryDTO> children=new ArrayList<>();

	public List<SpringArticleCategoryDTO> getChildren() {
		return children;
	}

	public void setChildren(List<SpringArticleCategoryDTO> children) {
		this.children = children;
	}
	
}
