package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringAttachmentCategory;
import io.github.springsongs.domain.dto.SpringAttachmentCategoryDTO;
import io.github.springsongs.util.R;

public interface ISpringAttachmentCategoryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringAttachmentCategoryDTO record);

	SpringAttachmentCategoryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAttachmentCategoryDTO record);

	Page<SpringAttachmentCategoryDTO> getAllRecordByPage(SpringAttachmentCategory record,Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringAttachmentCategoryDTO> listSpringAttachmentCategoryByParentId(String parentId);
}
