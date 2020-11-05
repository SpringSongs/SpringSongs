package io.github.springsongs.modules.contact.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.contact.bo.SpringContactQueryBO;
import io.github.springsongs.modules.contact.dto.SpringContactDTO;

public interface ISpringContactService {

	void deleteByPrimaryKey(String id);

	void insert(SpringContactDTO record);

	SpringContactDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringContactDTO record);

	Page<SpringContactDTO> getAllRecordByPage(SpringContactQueryBO springContactQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);
}
