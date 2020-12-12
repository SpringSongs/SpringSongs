package io.github.springsongs.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringUserRole;
import io.github.springsongs.modules.sys.dto.SpringRoleDTO;
import io.github.springsongs.modules.sys.dto.query.SpringRoleQuery;

public interface ISpringRoleService {
	void deleteByPrimaryKey(String id);

	void insert(SpringRoleDTO record);

	SpringRoleDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringRoleDTO record);

	Page<SpringRoleDTO> getAllRecordByPage(SpringRoleQuery springRoleQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);

	List<SpringRoleDTO> listByIds(List<String> ids);

	void delete(Map map);

	void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String userId);
	
	void deleteUserFromRole(List<String> userIds, String roleId);

	Page<SpringRoleDTO> ListRoleByUserId(String userId, Pageable pageable);
}
