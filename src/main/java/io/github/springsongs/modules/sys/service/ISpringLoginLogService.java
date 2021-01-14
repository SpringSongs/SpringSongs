package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.dto.SpringLoginLogDTO;
import io.github.springsongs.modules.sys.query.SpringLoginLogQuery;

public interface ISpringLoginLogService {

	void deleteByPrimaryKey(String id);

	void insert(SpringLoginLogDTO record);

	SpringLoginLogDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringLoginLogDTO record);

	Page<SpringLoginLogDTO> getAllRecordByPage(SpringLoginLogQuery springLoginLogQuery,Pageable pageable);

	
	void batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}
