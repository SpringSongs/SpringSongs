package io.github.springsongs.modules.job.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.job.dto.SpringJobGroupDTO;
import io.github.springsongs.modules.job.query.SpringJobGroupQuery;

public interface ISpringJobGroupService {
	void deleteByPrimaryKey(String id);

	void insert(SpringJobGroupDTO record);

	SpringJobGroupDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringJobGroupDTO record);

	Page<SpringJobGroupDTO> getAllRecordByPage(SpringJobGroupQuery record, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);
	
	List<SpringJobGroupDTO> listAll();
}
