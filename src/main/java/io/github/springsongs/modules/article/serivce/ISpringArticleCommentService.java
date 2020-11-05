package io.github.springsongs.modules.article.serivce;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.article.bo.SpringArticleCommentQueryBO;
import io.github.springsongs.modules.article.dto.SpringArticleCommentDTO;

public interface ISpringArticleCommentService {

	void deleteByPrimaryKey(String id);

	void insert(SpringArticleCommentDTO record);

	SpringArticleCommentDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringArticleCommentDTO record);

	Page<SpringArticleCommentDTO> getAllRecordByPage(SpringArticleCommentQueryBO springArticleCommentQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	void batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
	
	void audit(String id);
}
