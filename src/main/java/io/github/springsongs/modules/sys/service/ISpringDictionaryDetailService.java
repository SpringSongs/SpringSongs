package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.dto.SpringDictionaryDetailDTO;
import io.github.springsongs.modules.sys.query.SpringDictionaryDetailQuery;

public interface ISpringDictionaryDetailService {

	void deleteByPrimaryKey(String id);

	void insert(SpringDictionaryDetailDTO record);

	SpringDictionaryDetailDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDictionaryDetailDTO record);

	Page<SpringDictionaryDetailDTO> getAllRecordByPage(SpringDictionaryDetailQuery springDictionaryDetailQuery,
			Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);

	public void setDeleteByCode(String code);

	List<SpringDictionaryDetailDTO> listSpringDictionaryDetailByDictionaryCode(String dictionaryCode);
}
