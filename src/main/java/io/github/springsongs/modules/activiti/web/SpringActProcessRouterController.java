package io.github.springsongs.modules.activiti.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.activiti.dto.SpringActProcessRouterDTO;
import io.github.springsongs.modules.activiti.service.ISpringActProcessRouterService;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "工作流路由配置管理")
@RestController
@RequestMapping(value = "/SpringActProcessRouter")
@Validated
public class SpringActProcessRouterController extends BaseController {

	@Autowired
	private ISpringActProcessRouterService springActProcessRouterService;

	@ApiOperation(value = "创建工作流路由", notes = "根据SpringActProcessRouterDTO对象创建工作流路由")
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringActProcessRouterDTO"),
		@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringActProcessRouterDTO viewEntity,HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springActProcessRouterService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改工作流路由", notes = "根据SpringActProcessRouterDTO对象修改工作流路由", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringActProcessRouterDTO"),
		@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> edit(@RequestBody @Valid SpringActProcessRouterDTO viewEntity,HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springActProcessRouterService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "获取工作流路由", notes = "根据procDefKey获取工作流路由", response = ResponseDTO.class)
	@ApiImplicitParam(name = "procDefKey", dataType = "String")
	@GetMapping(value = "/FindSpringActProcessRouterByProcDefKey")
	public ResponseDTO<SpringActProcessRouterDTO> findSpringActProcessRouterByProcDefKey(
			@RequestParam(value = "procDefKey", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String procDefKey) {
		SpringActProcessRouterDTO springActProcessRouterDTO = springActProcessRouterService
				.findSpringActProcessRouterByProcDefKey(procDefKey);
		return ResponseDTO.successed(springActProcessRouterDTO, ResultCode.SELECT_SUCCESSED);
	}
}
