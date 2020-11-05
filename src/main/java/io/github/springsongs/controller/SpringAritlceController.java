package io.github.springsongs.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.domain.dto.ReponseResultPageDTO;
import io.github.springsongs.domain.dto.ResponseDTO;
import io.github.springsongs.domain.dto.SpringAritlceDTO;
import io.github.springsongs.domain.query.SpringAritlceQueryBO;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.service.ISpringAritlceService;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.IpKit;

@RestController
@RequestMapping(value = "/SpringAritlce")
public class SpringAritlceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringAritlceController.class);

	@Autowired
	private ISpringAritlceService springAritlceService;

	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringAritlceDTO> listByPage(@RequestBody SpringAritlceQueryBO springAritlceQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringAritlceDTO> lists = springAritlceService.getAllRecordByPage(springAritlceQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
	public ResponseDTO<SpringAritlceDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringAritlceDTO entity = springAritlceService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringAritlceDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springAritlceService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringAritlceDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		viewEntity.setUpdatedOn(new Date());
		springAritlceService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids") List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		springAritlceService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids") List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		springAritlceService.delete(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/Audit/{id}")
	public ResponseDTO<String> audit(
			@PathVariable(value = "id", required = true) @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springAritlceService.audit(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/HotStatus/{id}")
	public ResponseDTO<String> hotStatus(@PathVariable(value = "id", required = true)  @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springAritlceService.hotStatus(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/TopStatus/{id}")
	public ResponseDTO<String> topStatus(@PathVariable(value = "id", required = true)  @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springAritlceService.topStatus(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Featured/{id}")
	public ResponseDTO<String> featured(@PathVariable(value = "id", required = true)  @Valid @NotEmpty(message = Constant.PARAMETER_NOT_NULL_ERROR) String id) {
		springAritlceService.featured(id);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}
}
