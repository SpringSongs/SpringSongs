package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SpringResourceTableTreeDTO {
	protected String id;
	@JsonIgnore
	protected String parentId;
	private String title;
	private String code;
	private boolean menuFlag;
	private String vueUrl;
	private String vueIcon;
	private int sortCode;
	private boolean showStatus;
	private boolean enableEdit;
	private boolean enableDelete;
	private List<SpringResourceTableTreeDTO> children = new ArrayList<>();

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(boolean menuFlag) {
		this.menuFlag = menuFlag;
	}

	public String getVueUrl() {
		return vueUrl;
	}

	public void setVueUrl(String vueUrl) {
		this.vueUrl = vueUrl;
	}

	public String getVueIcon() {
		return vueIcon;
	}

	public void setVueIcon(String vueIcon) {
		this.vueIcon = vueIcon;
	}

	public int getSortCode() {
		return sortCode;
	}

	public void setSortCode(int sortCode) {
		this.sortCode = sortCode;
	}

	public boolean isShowStatus() {
		return showStatus;
	}

	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}

	public boolean isEnableEdit() {
		return enableEdit;
	}

	public void setEnableEdit(boolean enableEdit) {
		this.enableEdit = enableEdit;
	}

	public boolean isEnableDelete() {
		return enableDelete;
	}

	public void setEnableDelete(boolean enableDelete) {
		this.enableDelete = enableDelete;
	}

	public List<SpringResourceTableTreeDTO> getChildren() {
		return children;
	}

	public void setChildren(List<SpringResourceTableTreeDTO> children) {
		this.children = children;
	}

}
