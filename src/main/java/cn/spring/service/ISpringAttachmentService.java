package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringAttachment;
import cn.spring.util.R;

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
