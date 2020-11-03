package cn.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringUser;
import cn.spring.domain.SpringUserRole;
import cn.spring.domain.SpringUserSecurity;
import cn.spring.domain.query.SpringUserQuery;
import cn.spring.util.R;

public interface ISpringUserService {

	void deleteByPrimaryKey(String id);

	void insert(SpringUser record);

	SpringUser selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringUser record);

	Page<SpringUser> getAllRecordByPage(SpringUserQuery springUserQuery,Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	List<SpringUser> listUserByIds(List<String> ids);

	SpringUser getByUserName(String username);

	R setPwd(SpringUserSecurity entity);

	void delete(List<String> ids);

	public Page<SpringUser> ListUsersByRoleId(String roleId,Pageable pageable);

	void delete(Map map);

	void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String userId);
}
