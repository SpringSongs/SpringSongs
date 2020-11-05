package io.github.springsongs.modules.dictionary.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.dictionary.bo.SpringDictionaryDetailQueryBO;
import io.github.springsongs.modules.dictionary.dto.SpringDictionaryDetailDTO;

public interface ISpringDictionaryDetailService {

	void deleteByPrimaryKey(String id);

	void insert(SpringDictionaryDetailDTO record);

	SpringDictionaryDetailDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDictionaryDetailDTO record);

	Page<SpringDictionaryDetailDTO> getAllRecordByPage(SpringDictionaryDetailQueryBO springDictionaryDetailQuery,Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);

	public void setDeleteByCode(String code);
}
