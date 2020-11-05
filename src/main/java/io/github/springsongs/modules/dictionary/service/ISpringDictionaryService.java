package io.github.springsongs.modules.dictionary.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.dictionary.bo.SpringDictionaryQueryBO;
import io.github.springsongs.modules.dictionary.dto.SpringDictionaryDTO;

public interface ISpringDictionaryService {
	void deleteByPrimaryKey(String id);

	void insert(SpringDictionaryDTO record);

	SpringDictionaryDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDictionaryDTO record);

	Page<SpringDictionaryDTO> getAllRecordByPage(SpringDictionaryQueryBO springDictionaryQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	List<SpringDictionaryDTO> listByIds(List<String> ids);
}
