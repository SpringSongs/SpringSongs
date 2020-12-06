package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringDistrict;
import io.github.springsongs.modules.sys.dto.SpringDistrictDTO;

public interface ISpringDistrictService {
	void deleteByPrimaryKey(Long id);

	void insert(SpringDistrictDTO record);

	SpringDistrictDTO selectByPrimaryKey(Long id);

	void updateByPrimaryKey(SpringDistrictDTO record);

	Page<SpringDistrictDTO> getAllRecordByPage(SpringDistrict record, Pageable pageable);

	void setDeleted(List<Long> ids);

	void batchSaveExcel(List<String[]> list);
	
	List<SpringDistrictDTO> listSpringDistrictByParentId(Long parentId);
}
