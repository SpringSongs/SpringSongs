package io.github.springsongs.modules.process.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.job.dto.SpringJobDTO;
import io.github.springsongs.modules.process.domain.SpringActVacation;
import io.github.springsongs.modules.process.dto.SpringActVacationDTO;
import io.github.springsongs.modules.process.service.ISpringActVacationService;
import io.github.springsongs.util.IpKit;

@RestController
@RequestMapping(value = "/SpringActVacation")
public class SpringActVacationController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SpringActVacationController.class);

	@Autowired
	private ISpringActVacationService springActVacationService;

	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringJobDTO> listByPage(@RequestBody SpringActVacation springJobQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringActVacationDTO> lists = springActVacationService.getAllRecordByPage(springJobQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
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
		viewEntity.setSubmittime(new Date());
		springActVacationService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

}
