package io.github.springsongs.modules.activiti.web;

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
import org.springframework.validation.annotation.Validated;
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
import io.github.springsongs.modules.activiti.dto.SpringActCategoryDTO;
import io.github.springsongs.modules.activiti.query.SpringActCategoryQuery;
import io.github.springsongs.modules.activiti.service.ISpringActCategoryService;
import io.github.springsongs.modules.job.web.SpringJobGroupController;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "工作流模型分类管理")
@RestController
@RequestMapping(value = "/SpringActCategory")
@Validated
public class SpringActCategoryController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SpringJobGroupController.class);

	@Autowired
	private ISpringActCategoryService springActCategoryService;

	@ApiOperation(value = "获取工作流模型分类分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "springActCategoryQuery", dataType = "SpringActCategoryQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringActCategoryDTO> listByPage(
			@RequestBody SpringActCategoryQuery springActCategoryQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringActCategoryDTO> lists = springActCategoryService.getAllRecordByPage(springActCategoryQuery,
				pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取单一工作流模型分类", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<SpringActCategoryDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringActCategoryDTO entity = springActCategoryService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建工作流模型分类", notes = "根据SpringActCategoryDTO对象创建工作流模型分类", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringActCategoryDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringActCategoryDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springActCategoryService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.EXPORT_SUCCESSED);
	}

	@ApiOperation(value = "修改工作流模型分类", notes = "根据SpringActCategoryDTO对象修改工作流模型分类", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringActCategoryDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringActCategoryDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springActCategoryService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除工作流模型分类", notes = "根据List<String>对象删除工作流模型分类", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "工作流模型分类编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(
			@RequestParam(value = "ids", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) List<String> ids) {
		springActCategoryService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "删除工作流模型分类", notes = "根据List<String>对象删除工作流模型分类", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "工作流模型分类编号", required = true)
	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(
			@RequestParam(value = "ids", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) List<String> ids) {
		springActCategoryService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "查询全部工作流模型分类", response = ResponseDTO.class)
	@GetMapping(value = "/ListAll")
	public ResponseDTO<List<SpringActCategoryDTO>> listAll() {
		List<SpringActCategoryDTO> SpringActCategoryDTOs = springActCategoryService.listAll();
		return ResponseDTO.successed(SpringActCategoryDTOs, ResultCode.SELECT_SUCCESSED);
	}
}
