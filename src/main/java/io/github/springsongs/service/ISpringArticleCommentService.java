package io.github.springsongs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.domain.dto.SpringArticleCommentDTO;
import io.github.springsongs.domain.query.SpringArticleCommentQueryBO;
import io.github.springsongs.util.R;

public interface ISpringArticleCommentService {

	void deleteByPrimaryKey(String id);

	void insert(SpringArticleCommentDTO record);

	SpringArticleCommentDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringArticleCommentDTO record);

	Page<SpringArticleCommentDTO> getAllRecordByPage(SpringArticleCommentQueryBO springArticleCommentQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	void audit(String id);
}
