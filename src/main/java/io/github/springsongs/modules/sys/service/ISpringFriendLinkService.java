package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.domain.SpringFriendLink;
import io.github.springsongs.modules.sys.dto.SpringFriendLinkDTO;

public interface ISpringFriendLinkService {

	void deleteByPrimaryKey(String id);

	void insert(SpringFriendLinkDTO record);

	SpringFriendLinkDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringFriendLinkDTO record);

	Page<SpringFriendLinkDTO> getAllRecordByPage(SpringFriendLink record, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);
}
