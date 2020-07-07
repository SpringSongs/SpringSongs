package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringLoginLog;
import cn.spring.util.R;

public interface ISpringLoginLogService {

	void deleteByPrimaryKey(String id);

	void insert(SpringLoginLog record);

	SpringLoginLog selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringLoginLog record);

	Page<SpringLoginLog> getAllRecordByPage(SpringLoginLog record,Pageable pageable);

	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}
