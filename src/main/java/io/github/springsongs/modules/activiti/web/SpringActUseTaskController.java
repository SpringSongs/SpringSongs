package io.github.springsongs.modules.activiti.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.activiti.dto.SpringActUseTaskDTO;
import io.github.springsongs.modules.activiti.service.ISpringActUseTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "人员审批管理")
@RestController
@RequestMapping(value = "/SpringActUseTask")
@Validated
public class SpringActUseTaskController extends BaseController {

	@Autowired
	private ISpringActUseTaskService springActUseTaskService;

	@ApiOperation(value = "获取用户任务节点", notes = "根据procDefKey获取用户任务节点")
	@ApiImplicitParam(name = "procDefKey", dataType = "String")
	@GetMapping(value = "/listUserTaskByProcDefKey")
	public ResponseDTO<SpringActUseTaskDTO> listUserTaskByProcDefKey(
			@RequestParam(value = "procDefKey", required = true) String procDefKey) {
		List<SpringActUseTaskDTO> springActUseTaskDTOList = springActUseTaskService
				.listUserTaskByProcDefKey(procDefKey);
		return ResponseDTO.successed(springActUseTaskDTOList, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "初始化用户任务节点", notes = "根据processDefinitionId,procDefKey初始化用户任务节点", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "processDefinitionId", dataType = "String"),
		@ApiImplicitParam(name = "procDefKey", dataType = "String"), })
	@PostMapping(value = "/initSingleDefinition")
	public ResponseDTO<String> initSingleDefinition(
			@RequestParam(value = "processDefinitionId", required = true) String processDefinitionId,@RequestParam(value = "procDefKey", required = true) String procDefKey) {
		springActUseTaskService.initSingleDefinition(processDefinitionId,procDefKey);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}
	
	@ApiOperation(value = "初始化所有任务节点", notes = "初始化所有任务节点", response = ResponseDTO.class)
	@PostMapping(value = "/initAllDefinition")
	@ApiImplicitParam(name = "request", dataType = "HttpServletRequest")
	public ResponseDTO<String> initAllDefinition(HttpServletRequest request) {
		springActUseTaskService.initAllDefinition();
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}
	
	@ApiOperation(value = "添加审批用户给任务节点", notes = "根据procDefKey添加审批用户给任务节点", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "processDefinitionId", dataType = "String"),
		@ApiImplicitParam(name = "procDefKey", dataType = "String"), })
	@PostMapping(value = "/setUserToTask")
	public ResponseDTO<String> setUserToTask(@RequestParam(value = "procDefKey", required = true) String procDefKey,HttpServletRequest request) {
		springActUseTaskService.setUserToTask(procDefKey,request);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}
}
