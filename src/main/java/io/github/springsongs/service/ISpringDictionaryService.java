package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringDictionary;
import io.github.springsongs.domain.dto.SpringDictionaryDTO;
import io.github.springsongs.domain.query.SpringDictionaryQuery;
import io.github.springsongs.util.R;

public interface ISpringDictionaryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringDictionaryDTO record);

	SpringDictionaryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDictionaryDTO record);

	Page<SpringDictionaryDTO> getAllRecordByPage(SpringDictionaryQuery springDictionaryQuery,Pageable pageable);

	R setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringDictionary> listByIds(List<String> ids);
}
