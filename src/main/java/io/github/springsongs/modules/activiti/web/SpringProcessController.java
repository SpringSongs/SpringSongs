package io.github.springsongs.modules.activiti.web;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

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
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.activiti.dto.SpringProcessDTO;
import io.github.springsongs.modules.activiti.service.impl.SpringProcessService;
import io.swagger.annotations.Api;

@Api(tags = "流程管理")
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
	
	@GetMapping(value = "/Resource")
	public void resourceRead(String procDefId, String proInsId, String resType, HttpServletResponse response) {
	    if ("xml".equals(resType)) {
            response.setContentType("application/xml");
        } else {
	        response.setContentType("image/png");
        }

	    try {
            InputStream resourceAsStream = springProcessService.resourceRead(procDefId, proInsId, resType);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (Exception e) {
	        throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
        }
	}
	
	@PutMapping(value = "/Convert/{procDefId}")
	public ResponseDTO<String> convertToModel(@PathVariable("procDefId") String procDefId) {
		Model model = springProcessService.convertToModel(procDefId);
		return ResponseDTO.successed(null,ResultCode.SAVE_SUCCESSED);
	}
}
