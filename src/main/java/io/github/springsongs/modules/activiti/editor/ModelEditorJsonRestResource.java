package io.github.springsongs.modules.activiti.editor;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "工作流模型图管理")
@RestController
@RequestMapping("service")
public class ModelEditorJsonRestResource implements ModelDataJsonConstants {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ModelEditorJsonRestResource.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 获取流程json信息
	 * 
	 * @param modelId
	 * @return
	 */
	@ApiOperation(value = "获取流程json信息", notes = "获取流程json信息", response = ObjectNode.class)
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
	public ObjectNode getEditorJson(@PathVariable String modelId) {
		ObjectNode modelNode = null;
		Model model = repositoryService.getModel(modelId);
		if (model != null) {
			try {
				if (StringUtils.isNotEmpty(model.getMetaInfo())) {
					modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
				} else {
					modelNode = objectMapper.createObjectNode();
					modelNode.put(MODEL_NAME, model.getName());
				}
				modelNode.put(MODEL_ID, model.getId());
				ObjectNode editorJsonNode = (ObjectNode) objectMapper
						.readTree(new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
				modelNode.put("model", editorJsonNode);
			} catch (Exception e) {
				LOGGER.error("Error creating model JSON", e);
				throw new ActivitiException("Error creating model JSON", e);
			}
		}
		return modelNode;
	}
}
