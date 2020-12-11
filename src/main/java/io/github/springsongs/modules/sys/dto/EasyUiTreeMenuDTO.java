package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

public class EasyUiTreeMenuDTO {
	private List<EasyUiMenuDTO> easyUiMenuDTOList = new ArrayList<>();

	public EasyUiTreeMenuDTO(List<EasyUiMenuDTO> easyUiMenuDTOList) {
		super();
		this.easyUiMenuDTOList = easyUiMenuDTOList;
	}

	// 建立树形结构
	public List<EasyUiMenuDTO> builTree() {
		List<EasyUiMenuDTO> easyUiMenuDTOs = new ArrayList<EasyUiMenuDTO>();
		for (EasyUiMenuDTO easyUiMenuDTO : getRootNode()) {
			easyUiMenuDTO = buildChilTree(easyUiMenuDTO);
			easyUiMenuDTOs.add(easyUiMenuDTO);
		}
		return easyUiMenuDTOs;
	}

	// 递归，建立子树形结构
	private EasyUiMenuDTO buildChilTree(EasyUiMenuDTO pNode) {
		List<EasyUiMenuDTO> easyUiMenuDTOs = new ArrayList<EasyUiMenuDTO>();
		for (EasyUiMenuDTO easyUiMenuDTO : easyUiMenuDTOList) {
			if (easyUiMenuDTO.getParentId().equals(pNode.getId())) {
				easyUiMenuDTOs.add(buildChilTree(easyUiMenuDTO));
			}
		}
		pNode.setChildren(easyUiMenuDTOs);
		return pNode;
	}

	// 获取根节点
	private List<EasyUiMenuDTO> getRootNode() {
		List<EasyUiMenuDTO> easyUiMenuDTOTempList = new ArrayList<EasyUiMenuDTO>();
		for (EasyUiMenuDTO easyUiMenuDTO : easyUiMenuDTOList) {
			if (easyUiMenuDTO.getParentId().equals("0")) {
				easyUiMenuDTOTempList.add(easyUiMenuDTO);
			}
		}
		return easyUiMenuDTOTempList;
	}
}
