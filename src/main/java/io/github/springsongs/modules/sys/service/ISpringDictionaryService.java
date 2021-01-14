package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.dto.SpringDictionaryDTO;
import io.github.springsongs.modules.sys.query.SpringDictionaryQuery;

public interface ISpringDictionaryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringDictionaryDTO record);

	SpringDictionaryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDictionaryDTO record);

	Page<SpringDictionaryDTO> getAllRecordByPage(SpringDictionaryQuery springDictionaryQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringDictionaryDTO> listByIds(List<String> ids);
}
