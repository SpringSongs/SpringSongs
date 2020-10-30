package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringOrganization;
import cn.spring.util.R;

public interface ISpringOrganizationService {
	void deleteByPrimaryKey(String id);

	void insert(SpringOrganization record);

	SpringOrganization selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringOrganization record);

	Page<SpringOrganization> getAllRecordByPage(SpringOrganization record, Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);

	List<SpringOrganization> listOrganizationsByParent(String parentId);
}
