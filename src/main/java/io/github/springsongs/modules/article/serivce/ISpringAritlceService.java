package io.github.springsongs.modules.article.serivce;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.springsongs.modules.article.bo.SpringAritlceQueryBO;
import io.github.springsongs.modules.article.dto.SpringAritlceDTO;

public interface ISpringAritlceService {

	void deleteByPrimaryKey(String id);

	void insert(SpringAritlceDTO record);

	SpringAritlceDTO selectByPrimaryKey(String id);

	void updateByPrimaryKey(SpringAritlceDTO record);

	Page<SpringAritlceDTO> getAllRecordByPage(SpringAritlceQueryBO springAritlceQuery, Pageable pageable);

	void setDeleted(List<String> ids);

	void batchSaveExcel(List<String[]> list);

	void delete(List<String> ids);

	void audit(String id);

	void hotStatus(String id);

	void topStatus(String id);

	void featured(String id);
}