package cn.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringResource;
import cn.spring.domain.SpringResourceRole;
import cn.spring.dto.ModuleRoleDto;
import cn.spring.util.R;
import cn.spring.vo.ElementUiTreeVo;
import cn.spring.vo.MenuVo;

public interface ISpringResourceService {
	void deleteByPrimaryKey(String id);

	void insert(SpringResource record);

	SpringResource selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringResource record);

	Page<SpringResource> getAllRecordByPage(SpringResource record, Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	List<MenuVo> ListModuleByUserId(String userId);
	
	public List<ModuleRoleDto> listAllRoleModules();

	void delete(List<String> ids);

	List<SpringResource> listByIds(List<String> ids);

	List<ElementUiTreeVo> getModulesByParentId(String parentId, String systemId);

	void delete(Map map);

	void saveModuleToRole(List<SpringResourceRole> baseModuleRoleEntityList, String roleId);
	
	public List<SpringResourceRole>  listModulesByRoleId(String roleId);

}
