package io.github.springsongs.modules.activiti.web;

import javax.validation.Valid;

import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

@RestController
@RequestMapping(value = "/SpringActModel")
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
		return ResponseDTO.successed("modeler.html?modelId=" + model.getId());
	}
}
