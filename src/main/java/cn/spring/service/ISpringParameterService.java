package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringParameter;
import cn.spring.domain.query.SpringParameterQuery;
import cn.spring.util.R;

public interface ISpringParameterService {
	void deleteByPrimaryKey(String id);

	void insert(SpringParameter record);

	SpringParameter selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringParameter record);

	Page<SpringParameter> getAllRecordByPage(SpringParameterQuery springParameterQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringParameter> listByIds(List<String> ids);
}
