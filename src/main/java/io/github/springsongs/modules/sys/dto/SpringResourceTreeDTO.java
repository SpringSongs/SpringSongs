package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

public class SpringResourceTreeDTO {
	private List<SpringResourceDTO> springResourceDTOList=new ArrayList<>();

	
	
	public SpringResourceTreeDTO(List<SpringResourceDTO> springResourceDTOList) {
		super();
		this.springResourceDTOList = springResourceDTOList;
	}

	public List<SpringResourceDTO> getSpringResourceDTOList() {
		return springResourceDTOList;
	}

	public void setSpringResourceDTOList(List<SpringResourceDTO> springResourceDTOList) {
		this.springResourceDTOList = springResourceDTOList;
	}

		// 建立树形结构
		public List<SpringResourceDTO> builTree() {
			List<SpringResourceDTO> treeSpringResourceDTOs = new ArrayList<SpringResourceDTO>();
			for (SpringResourceDTO springResourceDTONode : getRootNode()) {
				springResourceDTONode = buildChilTree(springResourceDTONode);
				treeSpringResourceDTOs.add(springResourceDTONode);
			}
			return treeSpringResourceDTOs;
		}

		// 递归，建立子树形结构
		private SpringResourceDTO buildChilTree(SpringResourceDTO pNode) {
			List<SpringResourceDTO> chilSpringResourceDTOs = new ArrayList<SpringResourceDTO>();
			for (SpringResourceDTO SpringResourceDTONode : springResourceDTOList) {
				if (SpringResourceDTONode.getParentId().equals(pNode.getId())) {
					chilSpringResourceDTOs.add(buildChilTree(SpringResourceDTONode));
				}
			}
			pNode.setChildren(chilSpringResourceDTOs);
			return pNode;
		}

		// 获取根节点
		private List<SpringResourceDTO> getRootNode() {
			List<SpringResourceDTO> rootSpringResourceDTOLists = new ArrayList<SpringResourceDTO>();
			for (SpringResourceDTO springResourceDTONode : springResourceDTOList) {
				if (springResourceDTONode.getParentId().equals("0")) {
					rootSpringResourceDTOLists.add(springResourceDTONode);
				}
			}
			return rootSpringResourceDTOLists;
		}
}
