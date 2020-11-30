package io.github.springsongs.modules.activiti.web;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.activiti.dto.SpringActModelDTO;
import io.github.springsongs.modules.activiti.query.SpringActModelQuery;
import io.github.springsongs.modules.activiti.service.SpringActModelService;
import io.github.springsongs.modules.job.web.SpringJobGroupController;
import io.github.springsongs.util.Constant;
import io.swagger.annotations.Api;

@Api(tags = "工作流模型管理")
@RestController
@RequestMapping(value = "/SpringActModel")
@Validated
public class SpringActModelController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringJobGroupController.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private SpringActModelService springActModelService;

	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<Model> listByPage(@RequestBody SpringActModelQuery springActModelQuery,
			@PageableDefault(page = 0, size = 20) Pageable pageable) {
		Page<Model> lists = springActModelService.getAllRecordByPage(springActModelQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringActModelDTO viewEntity) {
		Model model = springActModelService.save(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Edit/{id}")
	public ResponseDTO<String> edit(@RequestBody @Valid SpringActModelDTO viewEntity,
			@PathVariable(value = "id", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springActModelService.update(viewEntity, id);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted/{id}")
	public ResponseDTO<String> delete(
			@PathVariable(value = "ids", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) List<String> ids) {
		springActModelService.delete(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PutMapping("/Deploy/{id}")
	public ResponseDTO<String> deploy(
			@PathVariable(value = "id", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springActModelService.deploy(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@GetMapping(value = "Export/{modelId}")
	public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("导出model的xml文件失败：modelId={}" + modelId, e);
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
		//return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}
}
