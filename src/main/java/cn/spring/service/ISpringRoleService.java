package cn.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringRole;
import cn.spring.domain.SpringUserRole;
import cn.spring.util.R;

public interface ISpringRoleService {
	void deleteByPrimaryKey(String id);

	void insert(SpringRole record);

	SpringRole selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringRole record);

	Page<SpringRole> getAllRecordByPage(SpringRole record,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);

	List<SpringRole> listByIds(List<String> ids);
	
	void delete(Map map);
	
	
	
    void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String userId);

	Page<SpringRole> ListRoleByUserId(String userId,Pageable pageable);
}
