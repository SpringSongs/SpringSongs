package io.github.springsongs.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import io.github.springsongs.domain.SpringOrganization;
import io.github.springsongs.domain.dto.ReponseResultPageDTO;
import io.github.springsongs.domain.dto.ResponseDTO;
import io.github.springsongs.domain.dto.SpringOrganizationDTO;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.service.ISpringOrganizationService;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.IpKit;
import io.github.springsongs.util.R;

@RestController
@RequestMapping(value = "/SpringOrganization")
public class SpringOrganizationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringOrganizationController.class);

	@Autowired
	private ISpringOrganizationService springOrganizationService;

	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringOrganizationDTO> getPage(@RequestBody SpringOrganization viewEntity,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringOrganizationDTO> lists = springOrganizationService.getAllRecordByPage(viewEntity, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
	public ResponseDTO<String> get(@NotEmpty(message = "id不能为空") String id) {
		SpringOrganization entity = springOrganizationService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringOrganizationDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		springOrganizationService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringOrganizationDTO viewEntity,
			HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springOrganizationService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		springOrganizationService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/listOrganizationsByParent")
	public ResponseDTO<SpringOrganizationDTO> getOrganizationsByParent(
			@RequestParam(value = "parentId", required = true) @Valid @NotEmpty(message = "id不能为空") String parentId) {
		List<SpringOrganizationDTO> elementUiTreeDtoList = springOrganizationService
				.listOrganizationsByParent(parentId);
		return ResponseDTO.successed(elementUiTreeDtoList, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/listAllRecord")
	public ResponseDTO<SpringOrganizationDTO> listAllRecord() {
		List<SpringOrganizationDTO> entitys = springOrganizationService.listAll();
		return ResponseDTO.successed(entitys, ResultCode.DELETE_SUCCESSED);
	}
}
