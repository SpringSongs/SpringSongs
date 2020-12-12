package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

public class SpringArticleCategoryTreeDTO {
	private List<SpringArticleCategoryDTO> springArticleCategoryDTOList = new ArrayList<>();

	public SpringArticleCategoryTreeDTO(List<SpringArticleCategoryDTO> springArticleCategoryDTOList) {
		super();
		this.springArticleCategoryDTOList = springArticleCategoryDTOList;
	}

	// 建立树形结构
	public List<SpringArticleCategoryDTO> builTree() {
		List<SpringArticleCategoryDTO> treeSpringArticleCategoryDTOList = new ArrayList<SpringArticleCategoryDTO>();
		for (SpringArticleCategoryDTO springArticleCategoryDTONode : getRootNode()) {
			springArticleCategoryDTONode = buildChilTree(springArticleCategoryDTONode);
			treeSpringArticleCategoryDTOList.add(springArticleCategoryDTONode);
		}
		return treeSpringArticleCategoryDTOList;
	}

	// 递归，建立子树形结构
	private SpringArticleCategoryDTO buildChilTree(SpringArticleCategoryDTO pNode) {
		List<SpringArticleCategoryDTO> childpringArticleCategoryDTOList = new ArrayList<SpringArticleCategoryDTO>();
		for (SpringArticleCategoryDTO springArticleCategoryDTONode : springArticleCategoryDTOList) {
			if (springArticleCategoryDTONode.getParentId().equals(pNode.getId())) {
				childpringArticleCategoryDTOList.add(buildChilTree(springArticleCategoryDTONode));
			}
		}
		pNode.setChildren(childpringArticleCategoryDTOList);
		return pNode;
	}

	// 获取根节点
	private List<SpringArticleCategoryDTO> getRootNode() {
		List<SpringArticleCategoryDTO> rootSpringArticleCategoryDTOList = new ArrayList<SpringArticleCategoryDTO>();
		for (SpringArticleCategoryDTO springArticleCategoryDTONode : springArticleCategoryDTOList) {
			if (springArticleCategoryDTONode.getParentId().equals("0")) {
				rootSpringArticleCategoryDTOList.add(springArticleCategoryDTONode);
			}
		}
		return rootSpringArticleCategoryDTOList;
	}
}
