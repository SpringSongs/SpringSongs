package io.github.springsongs.controller;

import javax.servlet.http.HttpServletResponse;

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

import io.github.springsongs.domain.dto.SpringLoginLogDTO;
import io.github.springsongs.domain.query.SpringLoginLogQueryBO;
import io.github.springsongs.service.ISpringLoginLogService;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.R;

@RestController
@RequestMapping(value = "/SpringLoginLog")
public class SpringLoginLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringLoginLogController.class);

	@Autowired
	private ISpringLoginLogService springLoginLogService;

	@PostMapping(value = "ListByPage")
	public R listByPage(@RequestBody SpringLoginLogQueryBO springLoginLogQuery, @PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringLoginLogDTO> lists = springLoginLogService.getAllRecordByPage(springLoginLogQuery, pageable);
			r.put("code", HttpServletResponse.SC_OK);
			r.put("msg", Constant.SELECT_SUCCESSED);
			r.put("data", lists.getContent());
			r.put("count", lists.getTotalElements());
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

}
