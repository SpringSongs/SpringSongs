package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuRouterTreeDTO {
	private List<MenuRouterDTO> menuRouterDTOList = new ArrayList<>();

	public MenuRouterTreeDTO(List<MenuRouterDTO> menuRouterDTOList) {
		super();
		this.menuRouterDTOList = menuRouterDTOList;
	}

	// 建立树形结构
	public List<MenuRouterDTO> builTree() {
		List<MenuRouterDTO> treeMenuRouterDTOs = new ArrayList<MenuRouterDTO>();
		for (MenuRouterDTO menuRouterDTONode : getRootNode()) {
			menuRouterDTONode = buildChilTree(menuRouterDTONode);
			treeMenuRouterDTOs.add(menuRouterDTONode);
		}
		return treeMenuRouterDTOs;
	}

	// 递归，建立子树形结构
	private MenuRouterDTO buildChilTree(MenuRouterDTO pNode) {
		List<MenuRouterDTO> chilMenuRouterDTOs = new ArrayList<MenuRouterDTO>();
		for (MenuRouterDTO menuRouterDTONode : menuRouterDTOList) {
			if (menuRouterDTONode.getParentId().equals(pNode.getId())) {
				chilMenuRouterDTOs.add(buildChilTree(menuRouterDTONode));
			}
		}
		pNode.setChildren(chilMenuRouterDTOs);
		return pNode;
	}

	// 获取根节点
	private List<MenuRouterDTO> getRootNode() {
		List<MenuRouterDTO> rootMenuRouterDTOLists = new ArrayList<MenuRouterDTO>();
		for (MenuRouterDTO menuRouterDTONode : menuRouterDTOList) {
			if (menuRouterDTONode.getParentId().equals("0")) {
				rootMenuRouterDTOLists.add(menuRouterDTONode);
			}
		}
		return rootMenuRouterDTOLists;
	}
}
