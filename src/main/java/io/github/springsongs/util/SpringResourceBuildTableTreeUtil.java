package io.github.springsongs.util;

import java.util.ArrayList;
import java.util.List;

import io.github.springsongs.modules.sys.dto.SpringResourceTableTreeDTO;

public class SpringResourceBuildTableTreeUtil {
	private List<SpringResourceTableTreeDTO> springResourceDTOList = new ArrayList<>();

	public SpringResourceBuildTableTreeUtil(List<SpringResourceTableTreeDTO> springResourceDTOList) {
		super();
		this.springResourceDTOList = springResourceDTOList;
	}

	public List<SpringResourceTableTreeDTO> getSpringResourceDTOList() {
		return springResourceDTOList;
	}

	public void setSpringResourceDTOList(List<SpringResourceTableTreeDTO> springResourceDTOList) {
		this.springResourceDTOList = springResourceDTOList;
	}

	// 建立树形结构
	public List<SpringResourceTableTreeDTO> builTree() {
		List<SpringResourceTableTreeDTO> treeSpringResourceDTOs = new ArrayList<SpringResourceTableTreeDTO>();
		for (SpringResourceTableTreeDTO springResourceDTONode : getRootNode()) {
			springResourceDTONode = buildChilTree(springResourceDTONode);
			treeSpringResourceDTOs.add(springResourceDTONode);
		}
		return treeSpringResourceDTOs;
	}

	// 递归，建立子树形结构
	private SpringResourceTableTreeDTO buildChilTree(SpringResourceTableTreeDTO pNode) {
		List<SpringResourceTableTreeDTO> chilSpringResourceDTOs = new ArrayList<SpringResourceTableTreeDTO>();
		for (SpringResourceTableTreeDTO SpringResourceDTONode : springResourceDTOList) {
			if (SpringResourceDTONode.getParentId().equals(pNode.getId())) {
				chilSpringResourceDTOs.add(buildChilTree(SpringResourceDTONode));
			}
		}
		pNode.setChildren(chilSpringResourceDTOs);
		return pNode;
	}

	// 获取根节点
	private List<SpringResourceTableTreeDTO> getRootNode() {
		List<SpringResourceTableTreeDTO> rootSpringResourceDTOLists = new ArrayList<SpringResourceTableTreeDTO>();
		for (SpringResourceTableTreeDTO springResourceDTONode : springResourceDTOList) {
			if (springResourceDTONode.getParentId().equals("0")) {
				rootSpringResourceDTOLists.add(springResourceDTONode);
			}
		}
		return rootSpringResourceDTOLists;
	}
}
