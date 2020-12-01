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
import io.github.springsongs.modules.job.dto.SpringJobGroupDTO;
import io.github.springsongs.modules.job.query.SpringJobGroupQuery;
import io.github.springsongs.modules.job.service.ISpringJobGroupService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "定时任务组管理")
@RestController
@RequestMapping(value = "/SpringJobGroup")
public class SpringJobGroupController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SpringJobGroupController.class);

	@Autowired
	private ISpringJobGroupService springJobGroupService;

	@ApiOperation(value = "获取定时任务组分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "springJobGroupQuery", dataType = "SpringJobGroupQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringJobGroupDTO> listByPage(@RequestBody SpringJobGroupQuery springJobGroupQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringJobGroupDTO> lists = springJobGroupService.getAllRecordByPage(springJobGroupQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取单一定时任务组", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<SpringJobGroupDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringJobGroupDTO entity = springJobGroupService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建定时任务组", notes = "根据SpringJobDTO定时任务组", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringJobDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringJobGroupDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springJobGroupService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改定时任务组", notes = "根据SpringJobDTO对象修改定时任务组", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringJobGroupDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringJobGroupDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springJobGroupService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "删除定时任务组", notes = "根据List<String>对象删除定时任务", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "工作流模型分类编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springJobGroupService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "删除定时任务组", notes = "根据List<String>对象删除定时任务", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "工作流模型分类编号", required = true)
	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springJobGroupService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "查询全部定时任务组", response = ResponseDTO.class)
	@GetMapping(value = "/ListAll")
	public ResponseDTO<List<SpringJobGroupDTO>> listAll() {
		List<SpringJobGroupDTO> springJobGroupDTOs = springJobGroupService.listAll();
		return ResponseDTO.successed(springJobGroupDTOs, ResultCode.SELECT_SUCCESSED);
	}
}
