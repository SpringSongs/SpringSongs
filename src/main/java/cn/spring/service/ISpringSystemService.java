package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringSystem;
import cn.spring.util.R;

public interface ISpringSystemService {
	void deleteByPrimaryKey(String id);

	void insert(SpringSystem record);

	SpringSystem selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringSystem record);

	Page<SpringSystem> getAllRecordByPage(SpringSystem record, Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	SpringSystem findByCode(String itemCode);

	List<SpringSystem> findInIds(List<String> ids);

	int batchSaveLog(List<SpringSystem> logList);
	
	List<SpringSystem> ListAll();

}
