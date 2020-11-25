package io.github.springsongs.modules.activiti.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.activiti.domain.SpringActUseTask;
import io.github.springsongs.modules.activiti.dto.SpringActUseTaskDTO;

public interface ISpringActUseTaskService {
	void deleteByPrimaryKey(String id);

	void insert(SpringActUseTaskDTO record);
	
	void insert(List<SpringActUseTaskDTO> record);

	SpringActUseTaskDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringActUseTaskDTO record);

	Page<SpringActUseTaskDTO> getAllRecordByPage(SpringActUseTask record, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);
	
	List<SpringActUseTaskDTO> listUserTaskByProcDefKey(String procDefKey);

	void initSingleDefinition(String processDefinitionId,String procDefKey );

	void initAllDefinition();

	void setUserToTask(String procDefKey, HttpServletRequest request);
}
