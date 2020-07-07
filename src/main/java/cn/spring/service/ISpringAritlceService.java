package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringAritlce;
import cn.spring.util.R;


public interface ISpringAritlceService {

	void deleteByPrimaryKey(String id);

	void insert(SpringAritlce record);

	SpringAritlce selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAritlce record);

	Page<SpringAritlce> getAllRecordByPage(SpringAritlce record, Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}