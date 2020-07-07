package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringAlbum;
import cn.spring.util.R;

public interface ISpringAlbumService {
	void deleteByPrimaryKey(String id);

	void insert(SpringAlbum record);

	SpringAlbum selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAlbum record);

	Page<SpringAlbum> getAllRecordByPage(SpringAlbum record,Pageable pageable);

	void setDeleted(List<String> ids);

	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}
