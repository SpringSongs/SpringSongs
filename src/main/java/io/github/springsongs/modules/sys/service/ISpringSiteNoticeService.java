package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringSiteNotice;
import io.github.springsongs.modules.sys.dto.SpringSiteNoticeDTO;

public interface ISpringSiteNoticeService {
	void deleteByPrimaryKey(String id);

	void insert(SpringSiteNoticeDTO record);

	SpringSiteNoticeDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringSiteNoticeDTO record);

	Page<SpringSiteNoticeDTO> getAllRecordByPage(SpringSiteNotice record, Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
}
