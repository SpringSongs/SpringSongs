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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.domain.dto.ReponseResultPageDTO;
import io.github.springsongs.domain.dto.ResponseDTO;
import io.github.springsongs.domain.dto.SpringParameterDTO;
import io.github.springsongs.domain.query.SpringParameterQueryBO;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.service.ISpringParameterService;
import io.github.springsongs.util.IpKit;

@RestController
@RequestMapping(value = "/SpringParameter")
public class SpringParameterController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringParameterController.class);

	@Autowired
	private ISpringParameterService springParameterService;

	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringParameterDTO> listByPage(@RequestBody SpringParameterQueryBO springParameterQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringParameterDTO> lists = springParameterService.getAllRecordByPage(springParameterQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
	public ResponseDTO<SpringParameterDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringParameterDTO entity = springParameterService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringParameterDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springParameterService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringParameterDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springParameterService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		springParameterService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		springParameterService.delete(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}
}
