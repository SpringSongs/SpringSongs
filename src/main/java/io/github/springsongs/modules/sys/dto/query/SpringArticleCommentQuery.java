package io.github.springsongs.modules.sys.dto.query;

public class SpringArticleCommentQuery {
	private String content;
	private String createdBy;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
