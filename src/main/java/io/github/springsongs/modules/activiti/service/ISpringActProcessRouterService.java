package io.github.springsongs.modules.activiti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.activiti.domain.SpringActProcessRouter;
import io.github.springsongs.modules.activiti.dto.SpringActProcessRouterDTO;

public interface ISpringActProcessRouterService {
	void deleteByPrimaryKey(String id);

	void insert(SpringActProcessRouterDTO record);

	SpringActProcessRouterDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringActProcessRouterDTO record);

	Page<SpringActProcessRouterDTO> getAllRecordByPage(SpringActProcessRouter record, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	SpringActProcessRouterDTO findSpringActProcessRouterByProcDefKey(String procDefKey);
}
