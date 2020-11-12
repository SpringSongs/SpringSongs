package io.github.springsongs.modules.activiti.service;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.activiti.dto.SpringActCategoryDTO;
import io.github.springsongs.modules.activiti.dto.SpringActModelDTO;
import io.github.springsongs.modules.activiti.query.SpringActModelQuery;

@Service
public class SpringActModelService {

	private static final Logger logger = LoggerFactory.getLogger(SpringActModelService.class);

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	ObjectMapper objectMapper;

	@Transactional
	public Model save(SpringActModelDTO springActModelDTO) {
		try {
			Model newModel = repositoryService.newModel();
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, springActModelDTO.getName());
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					StringUtils.defaultString(springActModelDTO.getDescription()));
			newModel.setMetaInfo(modelObjectNode.toString());
			newModel.setName(springActModelDTO.getName());
			newModel.setCategory(springActModelDTO.getCategoryCode());
			newModel.setKey(StringUtils.defaultString(springActModelDTO.getKey()));
			repositoryService.saveModel(newModel);
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			repositoryService.addModelEditorSource(newModel.getId(), editorNode.toString().getBytes("utf-8"));
			return newModel;
		} catch (UnsupportedEncodingException ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}

	}

	public Page<Model> getAllRecordByPage(SpringActModelQuery springActModelQuery, Pageable pageable) {
		ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
		if (StringUtils.isNotBlank(springActModelQuery.getCategoryCode())) {
			modelQuery.modelCategory(springActModelQuery.getCategoryCode());
		}
		if (StringUtils.isNotBlank(springActModelQuery.getKey())) {
			modelQuery.modelKey(springActModelQuery.getKey());
		}
		if (StringUtils.isNotBlank(springActModelQuery.getKey())) {
			modelQuery.modelName(springActModelQuery.getName());
		}
		Page<Model> pages = new PageImpl(modelQuery.listPage(pageable.getPageNumber(), pageable.getPageSize()),
				pageable, modelQuery.count());
		return pages;
	}

	@Transactional
	public void update(SpringActModelDTO viewEntity, String id) {
		Model modelData = repositoryService.getModel(id);
		modelData.setCategory(viewEntity.getCategoryCode());
		repositoryService.saveModel(modelData);
	}

	@Transactional
	public void delete(String id) {
		repositoryService.deleteModel(id);
	}
}
