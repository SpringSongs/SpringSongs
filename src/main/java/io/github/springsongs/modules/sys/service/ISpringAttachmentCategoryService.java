package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringAttachmentCategory;
import io.github.springsongs.modules.sys.dto.SpringAttachmentCategoryDTO;

public interface ISpringAttachmentCategoryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringAttachmentCategoryDTO record);

	SpringAttachmentCategoryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAttachmentCategoryDTO record);

	Page<SpringAttachmentCategoryDTO> getAllRecordByPage(SpringAttachmentCategory record,Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringAttachmentCategoryDTO> listSpringAttachmentCategoryByParentId(String parentId);
}
