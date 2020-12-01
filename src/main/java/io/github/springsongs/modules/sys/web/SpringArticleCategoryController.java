package io.github.springsongs.modules.sys.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;
import io.github.springsongs.modules.sys.dto.SpringArticleCategoryDTO;
import io.github.springsongs.modules.sys.dto.query.SpringArticleCategoryQuery;
import io.github.springsongs.modules.sys.service.ISpringArticleCategoryService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "内容分类管理")
@RestController
@RequestMapping(value = "/SpringArticleCategory")
public class SpringArticleCategoryController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringArticleCategoryController.class);

	@Autowired
	private ISpringArticleCategoryService springArticleCategoryService;

	@ApiOperation(value = "获取内容分类管理分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "springArticleCategoryQuery", dataType = "SpringArticleCategoryQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringArticleCategoryDTO> getPage(
			@RequestBody SpringArticleCategoryQuery springArticleCategoryQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringArticleCategoryDTO> lists = springArticleCategoryService
				.getAllRecordByPage(springArticleCategoryQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取单一内容分类管理", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<String> get(@RequestParam(value = "id", required = true) String id) {
		SpringArticleCategoryDTO entity = springArticleCategoryService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建内容分类", notes = "根据SpringArticleCategoryDTO内容分类", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringAritlceDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody SpringArticleCategoryDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springArticleCategoryService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改内容分类", notes = "根据SpringArticleCategoryDTO对象修改内容分类", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringAritlceDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody SpringArticleCategoryDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springArticleCategoryService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除内容分类", notes = "根据List<String>对象删除内容分类", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "内容分类编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springArticleCategoryService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "根据上级获取内容分类", notes = "根据String上级获取内容分类", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "parentId", name = "ids", value = "内容分类编号", required = true)
	@GetMapping(value = "/GetCategorysByParent")
	public ResponseDTO<ElementUiTreeDTO> getModuleByParentId(
			@RequestParam(value = "parentId", required = true) String parentId) {
		List<ElementUiTreeDTO> elementUiTreeDtoList = springArticleCategoryService.getCategoryByParentId(parentId);
		return ResponseDTO.successed(elementUiTreeDtoList, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取全部内容分类", notes = "全部内容分类", response = ResponseDTO.class)
	@GetMapping(value = "/listAllRecord")
	public ResponseDTO<SpringArticleCategoryDTO> listAllRecord() {
		List<SpringArticleCategoryDTO> entitys = springArticleCategoryService.listAll();
		return ResponseDTO.successed(entitys, ResultCode.SELECT_SUCCESSED);
	}
}
