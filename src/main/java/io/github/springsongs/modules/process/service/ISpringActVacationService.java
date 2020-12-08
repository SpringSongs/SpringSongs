package io.github.springsongs.modules.process.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.process.domain.SpringActVacation;
import io.github.springsongs.modules.process.dto.SpringActVacationDTO;

public interface ISpringActVacationService {
	void deleteByPrimaryKey(String id);

	void insert(SpringActVacationDTO record);

	SpringActVacationDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringActVacationDTO record);

	Page<SpringActVacationDTO> getAllRecordByPage(SpringActVacation record, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	public String submitSpringActVacation(SpringActVacationDTO vacation);


}
