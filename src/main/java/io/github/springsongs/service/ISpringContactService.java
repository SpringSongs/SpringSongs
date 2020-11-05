package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.dto.SpringContactDTO;
import io.github.springsongs.domain.query.SpringContactQueryBO;
import io.github.springsongs.util.R;

public interface ISpringContactService {

	void deleteByPrimaryKey(String id);

	void insert(SpringContactDTO record);

	SpringContactDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringContactDTO record);

	Page<SpringContactDTO> getAllRecordByPage(SpringContactQueryBO springContactQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);
}
