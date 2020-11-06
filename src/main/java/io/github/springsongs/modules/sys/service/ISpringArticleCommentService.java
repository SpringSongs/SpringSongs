package io.github.springsongs.modules.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.sys.dto.SpringArticleCommentDTO;
import io.github.springsongs.modules.sys.dto.query.SpringArticleCommentQuery;

public interface ISpringArticleCommentService {

	void deleteByPrimaryKey(String id);

	void insert(SpringArticleCommentDTO record);

	SpringArticleCommentDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringArticleCommentDTO record);

	Page<SpringArticleCommentDTO> getAllRecordByPage(SpringArticleCommentQuery springArticleCommentQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	void audit(String id);
}
