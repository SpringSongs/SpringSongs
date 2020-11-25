package io.github.springsongs.modules.process.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.springsongs.modules.process.domain.SpringActVacationApprove;
import io.github.springsongs.modules.process.dto.SpringActVacationApproveDTO;
import io.github.springsongs.modules.process.service.ISpringActVacationApproveService;

@Service
public class SpringActVacationApproveServiceImpl implements ISpringActVacationApproveService {

	@Override
	public void deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(SpringActVacationApproveDTO record) {
		// TODO Auto-generated method stub

	}

	@Override
	public SpringActVacationApproveDTO selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByPrimaryKey(SpringActVacationApproveDTO record) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<SpringActVacationApproveDTO> getAllRecordByPage(SpringActVacationApprove record, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeleted(List<String> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void batchSaveExcel(List<String[]> list) {
		// TODO Auto-generated method stub

	}

}
