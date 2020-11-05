package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringParameter;
import io.github.springsongs.domain.dto.SpringParameterDTO;
import io.github.springsongs.domain.query.SpringParameterQueryBO;
import io.github.springsongs.util.R;

public interface ISpringParameterService {
	void deleteByPrimaryKey(String id);

	void insert(SpringParameterDTO record);

	SpringParameterDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringParameterDTO record);

	Page<SpringParameterDTO> getAllRecordByPage(SpringParameterQueryBO springParameterQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringParameterDTO> listByIds(List<String> ids);
}
