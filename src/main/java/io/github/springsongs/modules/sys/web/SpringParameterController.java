package io.github.springsongs.modules.sys.web;

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
import org.springframework.util.CollectionUtils;
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
import io.github.springsongs.modules.sys.dto.SpringParameterDTO;
import io.github.springsongs.modules.sys.dto.query.SpringParameterQuery;
import io.github.springsongs.modules.sys.service.ISpringParameterService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "参数管理")
@RestController
@RequestMapping(value = "/SpringParameter")
public class SpringParameterController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringParameterController.class);

	@Autowired
	private ISpringParameterService springParameterService;

	@ApiOperation(value = "获取参数分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "springParameterQuery", dataType = "SpringParameterQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringParameterDTO> listByPage(@RequestBody SpringParameterQuery springParameterQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringParameterDTO> lists = springParameterService.getAllRecordByPage(springParameterQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取参数", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<SpringParameterDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringParameterDTO entity = springParameterService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建参数", notes = "根据SpringParameterDTO创建参数", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringAttachmentDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringParameterDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springParameterService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改参数", notes = "根据SpringParameterDTO修改参数", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringAttachmentDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringParameterDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springParameterService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除参数", notes = "根据List<String>对象删除参数", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "参数编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springParameterService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "删除参数", notes = "根据List<String>对象删除参数", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "参数编号", required = true)
	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springParameterService.delete(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}
}
