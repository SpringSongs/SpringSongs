package io.github.springsongs.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringResourceRole;
import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;
import io.github.springsongs.modules.sys.dto.MenuDTO;
import io.github.springsongs.modules.sys.dto.MenuRouterDTO;
import io.github.springsongs.modules.sys.dto.ResourceRoleDTO;
import io.github.springsongs.modules.sys.dto.SpringResourceDTO;
import io.github.springsongs.modules.sys.dto.query.SpringResourceQuery;

public interface ISpringResourceService {
	void deleteByPrimaryKey(String id);

	void insert(SpringResourceDTO record);

	SpringResourceDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringResourceDTO record);

	Page<SpringResourceDTO> getAllRecordByPage(SpringResourceQuery springResourceQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	List<MenuDTO> ListModuleByUserId(String userId);
	
	public List<ResourceRoleDTO> listAllRoleModules(List<String> roleCode);

	void delete(List<String> ids);

	List<SpringResourceDTO> listByIds(List<String> ids);

	List<ElementUiTreeDTO> getModulesByParentId(String parentId, String systemId);

	void delete(Map map);

	void saveModuleToRole(List<SpringResourceRole> baseModuleRoleEntityList, String roleId);
	
	public List<SpringResourceRole>  listModulesByRoleId(String roleId);
	
	public List<MenuRouterDTO> listResourceByUserId(String userId);
	
	List<SpringResourceDTO> ListAllToTree(String systemCode);
	

}
