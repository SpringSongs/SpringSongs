package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringSiteMessage;
import io.github.springsongs.modules.sys.dto.SpringSiteMessageDTO;

public interface ISpringSiteMessageService {
	void deleteByPrimaryKey(String id);

	void insert(SpringSiteMessageDTO record);

	SpringSiteMessageDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringSiteMessageDTO record);

	Page<SpringSiteMessageDTO> getAllRecordByPage(SpringSiteMessage record, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	int countNotReadMessageByUserId(String toUserId);
}
