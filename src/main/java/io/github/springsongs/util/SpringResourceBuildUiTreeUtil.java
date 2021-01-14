package io.github.springsongs.util;

import java.util.ArrayList;
import java.util.List;

import io.github.springsongs.modules.sys.dto.SpringResourceUiTreeDTO;

public class SpringResourceBuildUiTreeUtil {
	private List<SpringResourceUiTreeDTO> springResourceDTOList = new ArrayList<>();

	public SpringResourceBuildUiTreeUtil(List<SpringResourceUiTreeDTO> springResourceDTOList) {
		super();
		this.springResourceDTOList = springResourceDTOList;
	}

	public List<SpringResourceUiTreeDTO> getSpringResourceDTOList() {
		return springResourceDTOList;
	}

	public void setSpringResourceDTOList(List<SpringResourceUiTreeDTO> springResourceDTOList) {
		this.springResourceDTOList = springResourceDTOList;
	}

	// 建立树形结构
	public List<SpringResourceUiTreeDTO> builTree() {
		List<SpringResourceUiTreeDTO> treeSpringResourceDTOs = new ArrayList<SpringResourceUiTreeDTO>();
		for (SpringResourceUiTreeDTO springResourceDTONode : getRootNode()) {
			springResourceDTONode = buildChilTree(springResourceDTONode);
			treeSpringResourceDTOs.add(springResourceDTONode);
		}
		return treeSpringResourceDTOs;
	}

	// 递归，建立子树形结构
	private SpringResourceUiTreeDTO buildChilTree(SpringResourceUiTreeDTO pNode) {
		List<SpringResourceUiTreeDTO> chilSpringResourceDTOs = new ArrayList<SpringResourceUiTreeDTO>();
		for (SpringResourceUiTreeDTO SpringResourceDTONode : springResourceDTOList) {
			if (SpringResourceDTONode.getParentId().equals(pNode.getId())) {
				chilSpringResourceDTOs.add(buildChilTree(SpringResourceDTONode));
			}
		}
		pNode.setChildren(chilSpringResourceDTOs);
		return pNode;
	}

	// 获取根节点
	private List<SpringResourceUiTreeDTO> getRootNode() {
		List<SpringResourceUiTreeDTO> rootSpringResourceDTOLists = new ArrayList<SpringResourceUiTreeDTO>();
		for (SpringResourceUiTreeDTO springResourceDTONode : springResourceDTOList) {
			if (springResourceDTONode.getParentId().equals("0")) {
				rootSpringResourceDTOLists.add(springResourceDTONode);
			}
		}
		return rootSpringResourceDTOLists;
	}
}
