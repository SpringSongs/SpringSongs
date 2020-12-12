package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;
import io.github.springsongs.modules.sys.dto.SpringArticleCategoryDTO;
import io.github.springsongs.modules.sys.dto.query.SpringArticleCategoryQuery;

public interface ISpringArticleCategoryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringArticleCategoryDTO record);

	SpringArticleCategoryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringArticleCategoryDTO record);

	Page<SpringArticleCategoryDTO> getAllRecordByPage(SpringArticleCategoryQuery record,Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	List<ElementUiTreeDTO> getCategoryByParentId(String parentId);
   
	List<SpringArticleCategoryDTO> getByParentId(String parentId);
	
	List<SpringArticleCategoryDTO> listAll();
	
	List<SpringArticleCategoryDTO> ListAllToTree();
}
