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
import io.github.springsongs.modules.sys.domain.SpringDictionaryDetail;
import io.github.springsongs.modules.sys.dto.SpringDictionaryDetailDTO;
import io.github.springsongs.modules.sys.query.SpringDictionaryDetailQuery;
import io.github.springsongs.modules.sys.service.ISpringDictionaryDetailService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "字典明细管理")
@RestController
@RequestMapping(value = "/SpringDictionaryDetail")
public class SpringDictionaryDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringDictionaryDetailController.class);

	@Autowired
	private ISpringDictionaryDetailService springDictionaryDetailService;

	@ApiOperation(value = "获取字典明细分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "springDictionaryDetailQuery", dataType = "SpringDictionaryDetailQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringDictionaryDetailDTO> listByPage(
			@RequestBody SpringDictionaryDetailQuery springDictionaryDetailQuery,
			@PageableDefault(page = 0, size = 20) Pageable pageable) {
		Page<SpringDictionaryDetailDTO> lists = springDictionaryDetailService
				.getAllRecordByPage(springDictionaryDetailQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取字典明细", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<String> get(@NotEmpty(message = "id不能为空") String id) {
		SpringDictionaryDetail entity = springDictionaryDetailService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建字典明细", notes = "根据SpringDictionaryDetailDTO创建字典明细", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringDictionaryDetailDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringDictionaryDetailDTO viewEntity,
			HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springDictionaryDetailService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改字典明细", notes = "根据SpringDictionaryDetailDTO修改字典明细", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringDictionaryDetailDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringDictionaryDetailDTO viewEntity,
			HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springDictionaryDetailService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除字典明细", notes = "根据List<String>对象删除字典明细", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "字典明细编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springDictionaryDetailService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "删除字典明细", notes = "根据List<String>对象删除字典明细", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "字典明细编号", required = true)
	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "根据DictionaryCode查询字典明细", notes = "根据DictionaryCode查询字典明细", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "String", name = "dictionaryCode", value = "字典编码", required = true)
	@GetMapping(value = "/ListSpringDictionaryDetailByDictionaryCode")
	public ResponseDTO<List<SpringDictionaryDetailDTO>> listSpringDictionaryDetailByDictionaryCode(
			@RequestParam(value = "dictionaryCode", required = true) String dictionaryCode) {
		List<SpringDictionaryDetailDTO> springDictionaryDetailDTOList = springDictionaryDetailService
				.listSpringDictionaryDetailByDictionaryCode(dictionaryCode);
		return ResponseDTO.successed(springDictionaryDetailDTOList, ResultCode.SELECT_SUCCESSED);
	}
}