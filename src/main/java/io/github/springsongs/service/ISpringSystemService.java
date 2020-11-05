package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringSystem;
import io.github.springsongs.domain.dto.SpringSystemDTO;
import io.github.springsongs.domain.query.SpringSystemQueryBO;
import io.github.springsongs.util.R;

public interface ISpringSystemService {
	void deleteByPrimaryKey(String id);

	void insert(SpringSystemDTO record);

	SpringSystemDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringSystemDTO record);

	Page<SpringSystemDTO> getAllRecordByPage(SpringSystemQueryBO springSystemQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	SpringSystem findByCode(String itemCode);

	List<SpringSystemDTO> findInIds(List<String> ids);

	int batchSaveLog(List<SpringSystem> logList);

	List<SpringSystemDTO> ListAll();

}
