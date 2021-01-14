package io.github.springsongs.util;

import java.util.ArrayList;
import java.util.List;

import io.github.springsongs.modules.sys.dto.SpringAttachmentCategoryUiTreeDTO;

public class SpringAttachmentCategoryBuildUiTreeUtil {
	private List<SpringAttachmentCategoryUiTreeDTO> SpringAttachmentCategoryUiTreeDTOList = new ArrayList<>();

	public SpringAttachmentCategoryBuildUiTreeUtil(List<SpringAttachmentCategoryUiTreeDTO> SpringAttachmentCategoryUiTreeDTOList) {
		super();
		this.SpringAttachmentCategoryUiTreeDTOList = SpringAttachmentCategoryUiTreeDTOList;
	}

	// 建立树形结构
	public List<SpringAttachmentCategoryUiTreeDTO> builTree() {
		List<SpringAttachmentCategoryUiTreeDTO> treeSpringAttachmentCategoryUiTreeDTOList = new ArrayList<SpringAttachmentCategoryUiTreeDTO>();
		for (SpringAttachmentCategoryUiTreeDTO SpringAttachmentCategoryUiTreeDTONode : getRootNode()) {
			SpringAttachmentCategoryUiTreeDTONode = buildChilTree(SpringAttachmentCategoryUiTreeDTONode);
			treeSpringAttachmentCategoryUiTreeDTOList.add(SpringAttachmentCategoryUiTreeDTONode);
		}
		return treeSpringAttachmentCategoryUiTreeDTOList;
	}

	// 递归，建立子树形结构
	private SpringAttachmentCategoryUiTreeDTO buildChilTree(SpringAttachmentCategoryUiTreeDTO pNode) {
		List<SpringAttachmentCategoryUiTreeDTO> childpringArticleCategoryDTOList = new ArrayList<SpringAttachmentCategoryUiTreeDTO>();
		for (SpringAttachmentCategoryUiTreeDTO SpringAttachmentCategoryUiTreeDTONode : SpringAttachmentCategoryUiTreeDTOList) {
			if (SpringAttachmentCategoryUiTreeDTONode.getParentId().equals(pNode.getId())) {
				childpringArticleCategoryDTOList.add(buildChilTree(SpringAttachmentCategoryUiTreeDTONode));
			}
		}
		pNode.setChildren(childpringArticleCategoryDTOList);
		return pNode;
	}

	// 获取根节点
	private List<SpringAttachmentCategoryUiTreeDTO> getRootNode() {
		List<SpringAttachmentCategoryUiTreeDTO> rootSpringAttachmentCategoryUiTreeDTOList = new ArrayList<SpringAttachmentCategoryUiTreeDTO>();
		for (SpringAttachmentCategoryUiTreeDTO SpringAttachmentCategoryUiTreeDTONode : SpringAttachmentCategoryUiTreeDTOList) {
			if (SpringAttachmentCategoryUiTreeDTONode.getParentId().equals("0")) {
				rootSpringAttachmentCategoryUiTreeDTOList.add(SpringAttachmentCategoryUiTreeDTONode);
			}
		}
		return rootSpringAttachmentCategoryUiTreeDTOList;
	}
}
