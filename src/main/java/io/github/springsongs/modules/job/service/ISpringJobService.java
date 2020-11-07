package io.github.springsongs.modules.job.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.job.dto.SpringJobDTO;
import io.github.springsongs.modules.job.query.SpringJobQuery;

public interface ISpringJobService {

	void deleteByPrimaryKey(String id);

	void insert(SpringJobDTO record);

	SpringJobDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringJobDTO record);

	Page<SpringJobDTO> getAllRecordByPage(SpringJobQuery record, Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);

}
