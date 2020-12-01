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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import io.github.springsongs.modules.sys.dto.SpringAritlceDTO;
import io.github.springsongs.modules.sys.dto.query.SpringAritlceQuery;
import io.github.springsongs.modules.sys.service.ISpringAritlceService;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "内容管理")
@RestController
@RequestMapping(value = "/SpringArticle")
public class SpringAritlceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringAritlceController.class);

	@Autowired
	private ISpringAritlceService springAritlceService;

	@ApiOperation(value = "获取内容管理分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "springAritlceQuery", dataType = "SpringAritlceQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringAritlceDTO> listByPage(@RequestBody SpringAritlceQuery springAritlceQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringAritlceDTO> lists = springAritlceService.getAllRecordByPage(springAritlceQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取单一内容管理", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail/{id}")
	public ResponseDTO<SpringAritlceDTO> get(@PathVariable(value = "id", required = true)  @Valid  @NotEmpty(message = "id不能为空") String id) {
		SpringAritlceDTO entity = springAritlceService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建内容管理", notes = "根据SpringAritlceDTO内容管理", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringAritlceDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringAritlceDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springAritlceService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改内容管理", notes = "根据SpringJobDTO对象修改内容管理", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringAritlceDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringAritlceDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		viewEntity.setUpdatedOn(new Date());
		springAritlceService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除内容管理", notes = "根据List<String>对象删除内容管理", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "内容管理编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids") List<String> ids) {
		springAritlceService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "删除内容管理", notes = "根据List<String>对象删除内容管理", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "内容管理编号", required = true)
	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids") List<String> ids) {
		springAritlceService.delete(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "审核内容管理", notes = "根据String对象审核内容管理", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "String", name = "id", value = "内容管理编号", required = true)
	@PutMapping(value = "/Audit/{id}")
	public ResponseDTO<String> audit(
			@PathVariable(value = "id", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springAritlceService.audit(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "热门内容管理", notes = "根据String对象热门内容管理", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "String", name = "id", value = "内容管理编号", required = true)
	@PutMapping(value = "/HotStatus/{id}")
	public ResponseDTO<String> hotStatus(
			@PathVariable(value = "id", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springAritlceService.hotStatus(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "顶置内容管理", notes = "根据String对象顶置内容管理", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "String", name = "id", value = "内容管理编号", required = true)
	@PutMapping(value = "/TopStatus/{id}")
	public ResponseDTO<String> topStatus(
			@PathVariable(value = "id", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springAritlceService.topStatus(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "精华内容管理", notes = "根据String对象精华内容管理", response = ResponseDTO.class)
	@PutMapping(value = "/Featured/{id}")
	public ResponseDTO<String> featured(
			@PathVariable(value = "id", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springAritlceService.featured(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}
}
