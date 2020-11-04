package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringAttachment;
import io.github.springsongs.util.R;

public interface ISpringAttachmentService {

	void deleteByPrimaryKey(String id);

	void insert(SpringAttachment record);

	SpringAttachment selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAttachment record);

	Page<SpringAttachment> getAllRecordByPage(SpringAttachment record,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}
