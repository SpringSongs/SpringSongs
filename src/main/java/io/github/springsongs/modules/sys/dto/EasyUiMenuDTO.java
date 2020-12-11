package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

public class EasyUiMenuDTO {
	private String id;
	private String text;
	private String iconCls;
	private String state;
	private String parentId;
	private List<EasyUiMenuDTO> children=new ArrayList<>();
	private MenuAttributes attributes;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getIconCls() {
		return iconCls;
	}


	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public List<EasyUiMenuDTO> getChildren() {
		return children;
	}


	public void setChildren(List<EasyUiMenuDTO> children) {
		this.children = children;
	}


	public MenuAttributes getAttributes() {
		return attributes;
	}


	public void setAttributes(MenuAttributes attributes) {
		this.attributes = attributes;
	}


	public static class MenuAttributes{
		private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
	}
}
