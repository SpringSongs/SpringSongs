package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringAritlce;
import io.github.springsongs.domain.dto.SpringAritlceDTO;
import io.github.springsongs.domain.query.SpringAritlceQuery;
import io.github.springsongs.util.R;


public interface ISpringAritlceService {

	void deleteByPrimaryKey(String id);

	void insert(SpringAritlceDTO record);

	SpringAritlceDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAritlceDTO record);
	
	

	Page<SpringAritlceDTO> getAllRecordByPage(SpringAritlceQuery springAritlceQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	void audit(String id);
	
	void hotStatus(String id);
	
	void topStatus(String id);
	
	void featured(String id);
}