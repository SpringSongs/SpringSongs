package io.github.springsongs.modules.system.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.system.bo.SpringSystemQueryBO;
import io.github.springsongs.modules.system.dto.SpringSystemDTO;
import io.github.springsongs.modules.system.service.ISpringSystemService;
import io.github.springsongs.util.IpKit;

@RestController
@RequestMapping(value = "/SpringSystem")
public class SpringSystemController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringSystemController.class);

	@Autowired
	private ISpringSystemService springSystemService;

	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringSystemDTO> getPage(@RequestBody SpringSystemQueryBO springSystemQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {

		Page<SpringSystemDTO> lists = springSystemService.getAllRecordByPage(springSystemQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
	public ResponseDTO<SpringSystemDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringSystemDTO entity = springSystemService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/save")
	public ResponseDTO<String> save(@RequestBody SpringSystemDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springSystemService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody SpringSystemDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springSystemService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return ResponseDTO.successed(null, ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		springSystemService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/ListAll")
	public ResponseDTO<SpringSystemDTO> listAll() {
		List<SpringSystemDTO> springSystemList = springSystemService.ListAll();
		return ResponseDTO.successed(springSystemList, ResultCode.DELETE_SUCCESSED);
	}
}
