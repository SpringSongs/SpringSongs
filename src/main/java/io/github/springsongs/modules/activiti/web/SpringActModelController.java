package io.github.springsongs.modules.activiti.web;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.activiti.dto.SpringActModelDTO;
import io.github.springsongs.modules.activiti.query.SpringActModelQuery;
import io.github.springsongs.modules.activiti.service.SpringActModelService;
import io.github.springsongs.util.Constant;

@RestController
@RequestMapping(value = "/SpringActModel")
@Validated
public class SpringActModelController {

	@Autowired

	private SpringActModelService springActModelService;

	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<Model> listByPage(@RequestBody SpringActModelQuery springActModelQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
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
			@PathVariable(value = "id", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springActModelService.delete(id);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}
}
