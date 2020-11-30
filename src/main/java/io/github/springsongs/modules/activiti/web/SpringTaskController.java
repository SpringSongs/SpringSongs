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

@Api(tags = "工作流任务管理")
@RestController
@RequestMapping(value = "/SpringTask")
@Validated
public class SpringTaskController extends BaseController {

	@Autowired
	private SpringTaskService springTaskService;

	@GetMapping(value = "/GetTodoTasks")
	public ReponseResultPageDTO<SpringTaskDTO> getTodoTasks(String title, String category,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringTaskDTO> lists = springTaskService.getTodoTasks(this.getUser().getId(), title, category, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@GetMapping(value = "/GetTasksByStarter")
	public ReponseResultPageDTO<SpringTaskDTO> getTasksByStarter(String title, String category,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringTaskDTO> lists = springTaskService.getTasksByStarter(this.getUser().getId(), title, category,
				pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@GetMapping(value = "/GetFinishTasks")
	public ReponseResultPageDTO<SpringTaskDTO> getFinishTasks(String title, String category,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringTaskDTO> lists = springTaskService.getFinishTasks(this.getUser().getId(), title, category, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}
}
