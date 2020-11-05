package io.github.springsongs.modules.parameter.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.parameter.bo.SpringParameterQueryBO;
import io.github.springsongs.modules.parameter.dto.SpringParameterDTO;

public interface ISpringParameterService {
	void deleteByPrimaryKey(String id);

	void insert(SpringParameterDTO record);

	SpringParameterDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringParameterDTO record);

	Page<SpringParameterDTO> getAllRecordByPage(SpringParameterQueryBO springParameterQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringParameterDTO> listByIds(List<String> ids);
}
