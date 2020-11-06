package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringAttachment;
import io.github.springsongs.modules.sys.dto.SpringAttachmentDTO;

public interface ISpringAttachmentService {

	void deleteByPrimaryKey(String id);

	void insert(SpringAttachmentDTO record);

	SpringAttachmentDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAttachmentDTO record);

	Page<SpringAttachmentDTO> getAllRecordByPage(SpringAttachment record,Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}
