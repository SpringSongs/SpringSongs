package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.dto.SpringLoginLogDTO;
import io.github.springsongs.domain.query.SpringLoginLogQueryBO;
import io.github.springsongs.util.R;

public interface ISpringLoginLogService {

	void deleteByPrimaryKey(String id);

	void insert(SpringLoginLogDTO record);

	SpringLoginLogDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringLoginLogDTO record);

	Page<SpringLoginLogDTO> getAllRecordByPage(SpringLoginLogQueryBO springLoginLogQuery,Pageable pageable);

	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}
