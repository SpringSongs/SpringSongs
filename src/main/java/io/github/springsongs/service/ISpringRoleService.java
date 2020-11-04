package io.github.springsongs.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringRole;
import io.github.springsongs.domain.SpringUserRole;
import io.github.springsongs.domain.dto.SpringRoleDTO;
import io.github.springsongs.domain.query.SpringRoleQuery;
import io.github.springsongs.util.R;

public interface ISpringRoleService {
	void deleteByPrimaryKey(String id);

	void insert(SpringRoleDTO record);

	SpringRoleDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringRoleDTO record);

	Page<SpringRoleDTO> getAllRecordByPage(SpringRoleQuery springRoleQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);

	List<SpringRoleDTO> listByIds(List<String> ids);

	void delete(Map map);

	void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String userId);

	Page<SpringRoleDTO> ListRoleByUserId(String userId, Pageable pageable);
}
