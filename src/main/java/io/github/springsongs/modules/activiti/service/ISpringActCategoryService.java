package io.github.springsongs.modules.activiti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.activiti.dto.SpringActCategoryDTO;
import io.github.springsongs.modules.activiti.query.SpringActCategoryQuery;

public interface ISpringActCategoryService {

	void deleteByPrimaryKey(String id);

	void insert(SpringActCategoryDTO record);
	


	SpringActCategoryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringActCategoryDTO record);

	Page<SpringActCategoryDTO> getAllRecordByPage(SpringActCategoryQuery record, Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);

	List<SpringActCategoryDTO> listAll();
}

