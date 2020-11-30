package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import io.github.springsongs.modules.sys.domain.SpringDistrict;
import io.github.springsongs.modules.sys.dto.SpringDistrictDTO;

public interface ISpringDistrictService {
	void deleteByPrimaryKey(String id);

	void insert(SpringDistrictDTO record);

	SpringDistrictDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDistrictDTO record);

	Page<SpringDistrictDTO> getAllRecordByPage(SpringDistrict record, int currPage,int size);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
}
