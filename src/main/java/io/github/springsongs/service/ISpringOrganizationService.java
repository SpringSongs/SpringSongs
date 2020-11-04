package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringOrganization;
import io.github.springsongs.domain.dto.SpringOrganizationDTO;
import io.github.springsongs.util.R;

public interface ISpringOrganizationService {
	void deleteByPrimaryKey(String id);

	void insert(SpringOrganizationDTO record);

	SpringOrganizationDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringOrganizationDTO record);

	Page<SpringOrganizationDTO> getAllRecordByPage(SpringOrganization record, Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);

	List<SpringOrganizationDTO> listOrganizationsByParent(String parentId);

	List<SpringOrganizationDTO> listAll();
}
