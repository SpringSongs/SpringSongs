package io.github.springsongs.modules.activiti.web;

import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.activiti.dto.SpringProcessDTO;
import io.github.springsongs.modules.activiti.service.impl.SpringProcessService;

@RestController
@RequestMapping(value = "/SpringProcess")
@Validated
public class SpringProcessController {
	
	@Autowired
	private SpringProcessService springProcessService; 

	@GetMapping(value = "/ListByPage")
	public ReponseResultPageDTO<Model> listByPage(@RequestParam(required = false) String category,
			@PageableDefault(page = 0, size = 20) Pageable pageable) {
		Page<SpringProcessDTO> lists = springProcessService.getAllRecordByPage(category, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}
	
	@PutMapping("/ActiveOrSuspend/{state}")
	public ResponseDTO<String> updateState(@PathVariable("state") String state, @RequestParam String procDefId) {
        String status = springProcessService.updateState(state, procDefId);
        return ResponseDTO.successed(null,ResultCode.SAVE_SUCCESSED);
	}
}
