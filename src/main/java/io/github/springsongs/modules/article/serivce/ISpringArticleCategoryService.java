package io.github.springsongs.modules.article.serivce;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.article.bo.SpringArticleCategoryQueryBO;
import io.github.springsongs.modules.article.dto.SpringArticleCategoryDTO;
import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;

public interface ISpringArticleCategoryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringArticleCategoryDTO record);

	SpringArticleCategoryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringArticleCategoryDTO record);

	Page<SpringArticleCategoryDTO> getAllRecordByPage(SpringArticleCategoryQueryBO record,Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	List<ElementUiTreeDTO> getCategoryByParentId(String parentId);
   
	List<SpringArticleCategoryDTO> getByParentId(String parentId);
	
	List<SpringArticleCategoryDTO> listAll();
}
