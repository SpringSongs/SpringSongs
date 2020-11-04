package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.SpringDictionaryDetail;
import io.github.springsongs.domain.dto.SpringDictionaryDetailDTO;
import io.github.springsongs.domain.query.SpringDictionaryDetailQuery;
import io.github.springsongs.util.R;

public interface ISpringDictionaryDetailService {

	void deleteByPrimaryKey(String id);

	void insert(SpringDictionaryDetailDTO record);

	SpringDictionaryDetailDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDictionaryDetailDTO record);

	Page<SpringDictionaryDetailDTO> getAllRecordByPage(SpringDictionaryDetailQuery springDictionaryDetailQuery,Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);

	public void setDeleteByCode(String code);
}
