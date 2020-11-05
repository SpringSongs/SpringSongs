package io.github.springsongs.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringUser;
import io.github.springsongs.domain.SpringUserRole;
import io.github.springsongs.domain.SpringUserSecurity;
import io.github.springsongs.domain.dto.SpringUserDTO;
import io.github.springsongs.domain.query.SpringUserQueryBO;
import io.github.springsongs.util.R;

public interface ISpringUserService {

	void deleteByPrimaryKey(String id);

	void insert(SpringUserDTO record);

	SpringUserDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringUserDTO record);

	Page<SpringUserDTO> getAllRecordByPage(SpringUserQueryBO springUserQuery,Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	List<SpringUserDTO> listUserByIds(List<String> ids);

	SpringUserDTO getByUserName(String username);

	void setPwd(SpringUserSecurity entity);

	void delete(List<String> ids);

	public Page<SpringUserDTO> ListUsersByRoleId(String roleId,Pageable pageable);

	void delete(Map map);

	void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String userId);
}
