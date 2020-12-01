package io.github.springsongs.modules.activiti.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.activiti.dto.SpringTaskDTO;
import io.github.springsongs.modules.activiti.service.impl.SpringTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "工作流任务管理")
@RestController
@RequestMapping(value = "/SpringTask")
@Validated
public class SpringTaskController extends BaseController {

	@Autowired
	private SpringTaskService springTaskService;

	@ApiOperation(value = "获取待办分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "title", dataType = "String"),
			@ApiImplicitParam(name = "category", dataType = "String"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@GetMapping(value = "/GetTodoTasks")
	public ReponseResultPageDTO<SpringTaskDTO> getTodoTasks(String title, String category,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringTaskDTO> lists = springTaskService.getTodoTasks(this.getUser().getId(), title, category, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取我的申请分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "title", dataType = "String"),
			@ApiImplicitParam(name = "category", dataType = "String"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@GetMapping(value = "/GetTasksByStarter")
	public ReponseResultPageDTO<SpringTaskDTO> getTasksByStarter(String title, String category,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringTaskDTO> lists = springTaskService.getTasksByStarter(this.getUser().getId(), title, category,
				pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取已办分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "title", dataType = "String"),
			@ApiImplicitParam(name = "category", dataType = "String"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@GetMapping(value = "/GetFinishTasks")
	public ReponseResultPageDTO<SpringTaskDTO> getFinishTasks(String title, String category,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringTaskDTO> lists = springTaskService.getFinishTasks(this.getUser().getId(), title, category, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}
}
