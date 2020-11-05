package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.dto.ElementUiTreeDTO;
import io.github.springsongs.domain.dto.SpringArticleCategoryDTO;
import io.github.springsongs.domain.query.SpringArticleCategoryQueryBO;
import io.github.springsongs.util.R;

public interface ISpringArticleCategoryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringArticleCategoryDTO record);

	SpringArticleCategoryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringArticleCategoryDTO record);

	Page<SpringArticleCategoryDTO> getAllRecordByPage(SpringArticleCategoryQueryBO record,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	List<ElementUiTreeDTO> getCategoryByParentId(String parentId);
   
	List<SpringArticleCategoryDTO> getByParentId(String parentId);
	
	List<SpringArticleCategoryDTO> listAll();
}
