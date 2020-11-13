package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

public class SpringOrganizationTreeDTO {
	private List<SpringOrganizationDTO> springOrganizationDTOList = new ArrayList<>();

	public SpringOrganizationTreeDTO(List<SpringOrganizationDTO> springOrganizationDTOList) {
		super();
		this.springOrganizationDTOList = springOrganizationDTOList;
	}

	// 建立树形结构
	public List<SpringOrganizationDTO> builTree() {
		List<SpringOrganizationDTO> treeSpringOrganizationDTOs = new ArrayList<SpringOrganizationDTO>();
		for (SpringOrganizationDTO springOrganizationDTONode : getRootNode()) {
			springOrganizationDTONode = buildChilTree(springOrganizationDTONode);
			treeSpringOrganizationDTOs.add(springOrganizationDTONode);
		}
		return treeSpringOrganizationDTOs;
	}

	// 递归，建立子树形结构
	private SpringOrganizationDTO buildChilTree(SpringOrganizationDTO pNode) {
		List<SpringOrganizationDTO> chilSpringOrganizationDTOs = new ArrayList<SpringOrganizationDTO>();
		for (SpringOrganizationDTO springOrganizationDTONode : springOrganizationDTOList) {
			if (springOrganizationDTONode.getParentId().equals(pNode.getId())) {
				chilSpringOrganizationDTOs.add(buildChilTree(springOrganizationDTONode));
			}
		}
		pNode.setChildren(chilSpringOrganizationDTOs);
		return pNode;
	}

	// 获取根节点
	private List<SpringOrganizationDTO> getRootNode() {
		List<SpringOrganizationDTO> rootSpringOrganizationDTOLists = new ArrayList<SpringOrganizationDTO>();
		for (SpringOrganizationDTO springOrganizationDTONode : springOrganizationDTOList) {
			if (springOrganizationDTONode.getParentId().equals("0")) {
				rootSpringOrganizationDTOLists.add(springOrganizationDTONode);
			}
		}
		return rootSpringOrganizationDTOLists;
	}
}
