package io.github.springsongs.modules.sys.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import io.github.springsongs.modules.sys.domain.SpringDistrict;
import io.github.springsongs.modules.sys.dto.SpringDistrictDTO;
import io.github.springsongs.modules.sys.service.ISpringDistrictService;

@Service
public class SpringDistrictServiceImpl implements ISpringDistrictService {

	@Override
	public void deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(SpringDistrictDTO record) {
		// TODO Auto-generated method stub

	}

	@Override
	public SpringDistrictDTO selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByPrimaryKey(SpringDistrictDTO record) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<SpringDistrictDTO> getAllRecordByPage(SpringDistrict record, int currPage, int size) {
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
