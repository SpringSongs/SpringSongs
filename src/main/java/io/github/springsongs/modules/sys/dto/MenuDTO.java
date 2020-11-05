package io.github.springsongs.modules.sys.dto;

import java.util.List;

public class MenuDTO {
	private String id;
	private String icon;
	private String link;
	private String title;
	private String code;
	private int index;
	private List<MenuDTO> menuDtoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<MenuDTO> getMenuDtoList() {
		return menuDtoList;
	}

	public void setMenuDtoList(List<MenuDTO> menuDtoList) {
		this.menuDtoList = menuDtoList;
	}

}
