package io.github.springsongs.modules.sys.web;

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
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.sys.dto.SpringLoginLogDTO;
import io.github.springsongs.modules.sys.dto.query.SpringLoginLogQuery;
import io.github.springsongs.modules.sys.service.ISpringLoginLogService;
import io.swagger.annotations.Api;

@Api(tags = "定时登录日志管理")
@RestController
@RequestMapping(value = "/SpringLoginLog")
public class SpringLoginLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringLoginLogController.class);

	@Autowired
	private ISpringLoginLogService springLoginLogService;

	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringLoginLogDTO> listByPage(@RequestBody SpringLoginLogQuery springLoginLogQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringLoginLogDTO> lists = springLoginLogService.getAllRecordByPage(springLoginLogQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

}
