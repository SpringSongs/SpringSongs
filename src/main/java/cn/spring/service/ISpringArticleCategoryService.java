package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringArticleCategory;
import cn.spring.util.R;
import cn.spring.vo.ElementUiTreeVo;

public interface ISpringArticleCategoryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringArticleCategory record);

	SpringArticleCategory selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringArticleCategory record);

	Page<SpringArticleCategory> getAllRecordByPage(SpringArticleCategory record,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	List<ElementUiTreeVo> getCategoryByParentId(String parentId);
}
