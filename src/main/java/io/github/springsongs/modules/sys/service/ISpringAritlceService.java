package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.dto.SpringAritlceDTO;
import io.github.springsongs.modules.sys.query.SpringAritlceQuery;

public interface ISpringAritlceService {

	void deleteByPrimaryKey(String id);

	void insert(SpringAritlceDTO record);

	SpringAritlceDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAritlceDTO record);

	Page<SpringAritlceDTO> getAllRecordByPage(SpringAritlceQuery springAritlceQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);

	void audit(String id);

	void hotStatus(String id);

	void topStatus(String id);

	void featured(String id);
}