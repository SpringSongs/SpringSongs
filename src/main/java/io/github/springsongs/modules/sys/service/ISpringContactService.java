package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.dto.SpringContactDTO;
import io.github.springsongs.modules.sys.dto.query.SpringContactQuery;

public interface ISpringContactService {

	void deleteByPrimaryKey(String id);

	void insert(SpringContactDTO record);

	SpringContactDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringContactDTO record);

	Page<SpringContactDTO> getAllRecordByPage(SpringContactQuery springContactQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);
}
