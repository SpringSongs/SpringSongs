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

import io.github.springsongs.domain.SpringDictionaryDetail;
import io.github.springsongs.domain.dto.ReponseResultPageDTO;
import io.github.springsongs.domain.dto.ResponseDTO;
import io.github.springsongs.domain.dto.SpringDictionaryDetailDTO;
import io.github.springsongs.domain.query.SpringDictionaryDetailQueryBO;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.service.ISpringDictionaryDetailService;
import io.github.springsongs.util.IpKit;
import io.github.springsongs.util.R;

@RestController
@RequestMapping(value = "/SpringDictionaryDetail")
public class SpringDictionaryDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringDictionaryDetailController.class);

	@Autowired
	private ISpringDictionaryDetailService springDictionaryDetailService;

	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringDictionaryDetailDTO> listByPage(
			@RequestBody SpringDictionaryDetailQueryBO springDictionaryDetailQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringDictionaryDetailDTO> lists = springDictionaryDetailService
				.getAllRecordByPage(springDictionaryDetailQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
	public ResponseDTO<String> get(@NotEmpty(message = "id不能为空") String id) {
		SpringDictionaryDetail entity = springDictionaryDetailService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

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

	@PostMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringDictionaryDetailDTO viewEntity,
			HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springDictionaryDetailService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		springDictionaryDetailService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		R r = new R();
		if (CollectionUtils.isEmpty(ids)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}
}