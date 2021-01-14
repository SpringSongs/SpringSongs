package io.github.springsongs.util;

import java.util.ArrayList;
import java.util.List;

import io.github.springsongs.modules.sys.dto.SpringArticleCategoryUiTreeDTO;

public class SpringArticleCategoryBuildUiTreeUtil {
	private List<SpringArticleCategoryUiTreeDTO> SpringArticleCategoryUiTreeDTOList = new ArrayList<>();

	public SpringArticleCategoryBuildUiTreeUtil(List<SpringArticleCategoryUiTreeDTO> SpringArticleCategoryUiTreeDTOList) {
		super();
		this.SpringArticleCategoryUiTreeDTOList = SpringArticleCategoryUiTreeDTOList;
	}

	// 建立树形结构
	public List<SpringArticleCategoryUiTreeDTO> builTree() {
		List<SpringArticleCategoryUiTreeDTO> treeSpringArticleCategoryUiTreeDTOList = new ArrayList<SpringArticleCategoryUiTreeDTO>();
		for (SpringArticleCategoryUiTreeDTO SpringArticleCategoryUiTreeDTONode : getRootNode()) {
			SpringArticleCategoryUiTreeDTONode = buildChilTree(SpringArticleCategoryUiTreeDTONode);
			treeSpringArticleCategoryUiTreeDTOList.add(SpringArticleCategoryUiTreeDTONode);
		}
		return treeSpringArticleCategoryUiTreeDTOList;
	}

	// 递归，建立子树形结构
	private SpringArticleCategoryUiTreeDTO buildChilTree(SpringArticleCategoryUiTreeDTO pNode) {
		List<SpringArticleCategoryUiTreeDTO> childpringArticleCategoryDTOList = new ArrayList<SpringArticleCategoryUiTreeDTO>();
		for (SpringArticleCategoryUiTreeDTO SpringArticleCategoryUiTreeDTONode : SpringArticleCategoryUiTreeDTOList) {
			if (SpringArticleCategoryUiTreeDTONode.getParentId().equals(pNode.getId())) {
				childpringArticleCategoryDTOList.add(buildChilTree(SpringArticleCategoryUiTreeDTONode));
			}
		}
		pNode.setChildren(childpringArticleCategoryDTOList);
		return pNode;
	}

	// 获取根节点
	private List<SpringArticleCategoryUiTreeDTO> getRootNode() {
		List<SpringArticleCategoryUiTreeDTO> rootSpringArticleCategoryUiTreeDTOList = new ArrayList<SpringArticleCategoryUiTreeDTO>();
		for (SpringArticleCategoryUiTreeDTO SpringArticleCategoryUiTreeDTONode : SpringArticleCategoryUiTreeDTOList) {
			if (SpringArticleCategoryUiTreeDTONode.getParentId().equals("0")) {
				rootSpringArticleCategoryUiTreeDTOList.add(SpringArticleCategoryUiTreeDTONode);
			}
		}
		return rootSpringArticleCategoryUiTreeDTOList;
	}
}
