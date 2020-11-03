package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringDictionary;
import cn.spring.domain.query.SpringDictionaryQuery;
import cn.spring.util.R;

public interface ISpringDictionaryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringDictionary record);

	SpringDictionary selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDictionary record);

	Page<SpringDictionary> getAllRecordByPage(SpringDictionaryQuery springDictionaryQuery,Pageable pageable);

	R setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringDictionary> listByIds(List<String> ids);
}
