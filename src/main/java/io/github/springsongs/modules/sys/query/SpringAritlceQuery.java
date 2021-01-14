package io.github.springsongs.modules.sys.query;

import io.github.springsongs.common.dto.SearchDate;

public class SpringAritlceQuery {
	private String categoryId;
	private String tag;
	private String keyword;
	private String title;
	private String summary;
	private String author;
	private boolean status;
	private boolean topStatus;
	private boolean hotStatus;
	private boolean featured;
	private String createdUserId;
	private SearchDate searchDate;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isTopStatus() {
		return topStatus;
	}

	public void setTopStatus(boolean topStatus) {
		this.topStatus = topStatus;
	}

	public boolean isHotStatus() {
		return hotStatus;
	}

	public void setHotStatus(boolean hotStatus) {
		this.hotStatus = hotStatus;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public SearchDate getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(SearchDate searchDate) {
		this.searchDate = searchDate;
	}

}
