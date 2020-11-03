package cn.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.spring.domain.SpringArticleComment;
import cn.spring.domain.query.SpringArticleCommentQuery;
import cn.spring.util.R;

public interface ISpringArticleCommentService {

	void deleteByPrimaryKey(String id);

	void insert(SpringArticleComment record);

	SpringArticleComment selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringArticleComment record);

	Page<SpringArticleComment> getAllRecordByPage(SpringArticleCommentQuery springArticleCommentQuery,Pageable pageable);

	void setDeleted(List<String> ids);
	
	R batchSaveExcel(List<String[]> list);
	
	void delete(List<String> ids);
}
