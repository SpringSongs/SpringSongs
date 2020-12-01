package io.github.springsongs.modules.job.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.job.dto.SpringJobDTO;
import io.github.springsongs.modules.job.query.SpringJobQuery;
import io.github.springsongs.modules.job.service.ISpringJobService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "定时任务管理")
@RestController
@RequestMapping(value = "/SpringJob")
public class SpringJobController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SpringJobController.class);

	@Autowired
	private ISpringJobService springJobService;

	@ApiOperation(value = "获取定时任务分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "springJobQuery", dataType = "SpringJobQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringJobDTO> listByPage(@RequestBody SpringJobQuery springJobQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringJobDTO> lists = springJobService.getAllRecordByPage(springJobQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取单一定时任务", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<SpringJobDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringJobDTO entity = springJobService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建定时任务", notes = "根据SpringJobDTO对象创建定时任务", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringJobDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringJobDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springJobService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改定时任务", notes = "根据SpringJobDTO对象修改定时任务", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringJobDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringJobDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springJobService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "删除定时任务", notes = "根据List<String>对象删除定时任务", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "工作流模型分类编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springJobService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "删除定时任务", notes = "根据List<String>对象删除定时任务", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "工作流模型分类编号", required = true)
	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springJobService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "添加定时任务", notes = "根据SpringJobDTO添加定时任务", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringJobDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/AddTask")
	public ResponseDTO<String> addTask(@RequestBody @Valid SpringJobDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springJobService.addTask(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "更新定时任务", notes = "根据SpringJobDTO更新定时任务", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringJobDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/UpdateTask")
	public ResponseDTO<String> updateTask(@RequestBody @Valid SpringJobDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springJobService.updateTask(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "暂停定时任务", notes = "根据taskClassName,groupCode暂停定时任务", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "taskClassName", dataType = "String"),
			@ApiImplicitParam(name = "request", dataType = "String"), })
	@PutMapping(value = "/groupCode")
	public ResponseDTO<String> pauseTask(@RequestParam(value = "taskClassName", required = true) String taskClassName,
			@RequestParam(value = "groupCode", required = true) String groupCode) {
		springJobService.pauseTask(taskClassName, groupCode);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "恢复定时任务", notes = "根据taskClassName,groupCode恢复定时任务", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "taskClassName", dataType = "String"),
			@ApiImplicitParam(name = "request", dataType = "String"), })
	@PutMapping(value = "/ResumeTask")
	public ResponseDTO<String> resumeTask(@RequestParam(value = "taskClassName", required = true) String taskClassName,
			@RequestParam(value = "groupCode", required = true) String groupCode) {
		springJobService.resumeTask(taskClassName, groupCode);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除定时任务", notes = "根据taskClassName,groupCode删除定时任务", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "taskClassName", dataType = "String"),
			@ApiImplicitParam(name = "request", dataType = "String"), })
	@PostMapping(value = "/DeleteTask")
	public ResponseDTO<String> deleteTask(@RequestParam(value = "taskClassName", required = true) String taskClassName,
			@RequestParam(value = "groupCode", required = true) String groupCode) {
		springJobService.deleteTask(taskClassName, groupCode);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);

	}
}
