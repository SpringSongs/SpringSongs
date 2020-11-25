package io.github.springsongs.modules.job.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.job.domain.SpringJobHistory;
import io.github.springsongs.modules.job.dto.SpringJobHistoryDTO;

public interface ISpringJobHistoryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringJobHistoryDTO record);

	SpringJobHistoryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringJobHistoryDTO record);

	Page<SpringJobHistoryDTO> getAllRecordByPage(SpringJobHistory record, Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
}
