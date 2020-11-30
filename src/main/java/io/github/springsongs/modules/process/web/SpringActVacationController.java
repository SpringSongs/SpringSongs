package io.github.springsongs.modules.process.web;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.job.dto.SpringJobDTO;
import io.github.springsongs.modules.process.domain.SpringActVacation;
import io.github.springsongs.modules.process.dto.SpringActVacationDTO;
import io.github.springsongs.modules.process.service.ISpringActVacationService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;

@Api(tags = "请假流程管理")
@RestController
@RequestMapping(value = "/SpringActVacation")
public class SpringActVacationController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SpringActVacationController.class);

	@Autowired
	private ISpringActVacationService springActVacationService;

	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringJobDTO> listByPage(@RequestBody SpringActVacation springJobQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		springJobQuery.setCreatedUserId(this.getUser().getId());
		Page<SpringActVacationDTO> lists = springActVacationService.getAllRecordByPage(springJobQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@GetMapping(value = "/Detail")
	public ResponseDTO<SpringActVacationDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringActVacationDTO entity = springActVacationService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringActVacationDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUserId(this.getUser().getId());
		viewEntity.setTrueName(this.getUser().getTrueName());
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());

		springActVacationService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}
	
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids") List<String> ids) {
		springActVacationService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/SubmitSpringActVacation")
	public ResponseDTO<String> submitSpringActVacation(@RequestBody @Valid SpringActVacationDTO viewEntity) {
		viewEntity.setSubmittime(new Date());
		try {
			springActVacationService.submitSpringActVacation(viewEntity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

}
