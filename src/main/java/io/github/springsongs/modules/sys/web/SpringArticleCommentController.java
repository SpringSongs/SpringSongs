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
import org.springframework.util.StringUtils;
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
import io.github.springsongs.modules.sys.dto.SpringArticleCommentDTO;
import io.github.springsongs.modules.sys.dto.query.SpringArticleCommentQuery;
import io.github.springsongs.modules.sys.service.ISpringArticleCommentService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "内容评论管理")
@RestController
@RequestMapping(value = "/SpringArticleComment")
public class SpringArticleCommentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringArticleCommentController.class);

	@Autowired
	private ISpringArticleCommentService springArticleCommentService;

	@ApiOperation(value = "获取内容评论分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "springArticleCommentQuery", dataType = "springArticleCommentQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringArticleCommentDTO> listByPage(
			@RequestBody SpringArticleCommentQuery springArticleCommentQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {

		Page<SpringArticleCommentDTO> lists = springArticleCommentService.getAllRecordByPage(springArticleCommentQuery,
				pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取单一内容评论", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<String> get(@NotEmpty(message = "id不能为空") String id) {
		SpringArticleCommentDTO entity = springArticleCommentService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建内容评论", notes = "根据SpringArticleCommentDTO创建内容评论", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringArticleCommentDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringArticleCommentDTO viewEntity,
			HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springArticleCommentService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "修改内容评论", notes = "根据SpringJobDTO对象修改内容评论", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringArticleCommentDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringArticleCommentDTO viewEntity,
			HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springArticleCommentService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除内容评论", notes = "根据List<String>对象删除内容评论", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "内容评论编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springArticleCommentService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "删除内容评论", notes = "根据List<String>对象删除内容评论", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "内容评论编号", required = true)
	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springArticleCommentService.delete(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "审核内容评论", notes = "根据String对象审核内容评论", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "String", name = "id", value = "内容评论编号", required = true)
	@PutMapping(value = "/Audit/{id}")
	public ResponseDTO<String> audit(@PathVariable(value = "id", required = true) String id) {
		if (StringUtils.isEmpty(id)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		springArticleCommentService.audit(id);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}
}
