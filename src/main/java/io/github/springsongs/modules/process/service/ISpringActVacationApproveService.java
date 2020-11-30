package io.github.springsongs.modules.process.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.process.domain.SpringActVacationApprove;
import io.github.springsongs.modules.process.dto.SpringActVacationApproveDTO;

public interface ISpringActVacationApproveService {
	void deleteByPrimaryKey(String id);

	void insert(SpringActVacationApproveDTO record);

	SpringActVacationApproveDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringActVacationApproveDTO record);

	Page<SpringActVacationApproveDTO> getAllRecordByPage(SpringActVacationApprove record, Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	public void completeSpringActVacationApprove(SpringActVacationApproveDTO record,String taskId);
	
	public void completeTask(String taskId, String auditStr);
}
