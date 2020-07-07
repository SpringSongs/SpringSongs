package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringDictionaryDetail;
import cn.spring.util.R;

public interface ISpringDictionaryDetailService {

	void deleteByPrimaryKey(String id);

	void insert(SpringDictionaryDetail record);

	SpringDictionaryDetail selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringDictionaryDetail record);

	Page<SpringDictionaryDetail> getAllRecordByPage(SpringDictionaryDetail record,Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);

	public void setDeleteByCode(String code);
}
