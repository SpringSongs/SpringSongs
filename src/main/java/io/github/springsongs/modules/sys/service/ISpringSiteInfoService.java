package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringSiteInfo;
import io.github.springsongs.modules.sys.dto.SpringSiteInfoDTO;

public interface ISpringSiteInfoService {
	void deleteByPrimaryKey(String id);

	void insert(SpringSiteInfoDTO record);

	SpringSiteInfoDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringSiteInfoDTO record);

	Page<SpringSiteInfoDTO> getAllRecordByPage(SpringSiteInfo record, Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
}
