package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringContact;
import cn.spring.util.R;

public interface ISpringContactService {

	void deleteByPrimaryKey(String id);

	void insert(SpringContact record);

	SpringContact selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringContact record);

	Page<SpringContact> getAllRecordByPage(SpringContact record,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}
