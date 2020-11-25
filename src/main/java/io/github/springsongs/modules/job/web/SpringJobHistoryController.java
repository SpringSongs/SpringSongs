package io.github.springsongs.modules.job.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.job.domain.SpringJobHistory;
import io.github.springsongs.modules.job.dto.SpringJobDTO;
import io.github.springsongs.modules.job.dto.SpringJobHistoryDTO;
import io.github.springsongs.modules.job.service.ISpringJobHistoryService;

@RestController
@RequestMapping(value = "/SpringJobHistory")
public class SpringJobHistoryController extends BaseController {

	@Autowired
	private ISpringJobHistoryService springJobHistoryService;

	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringJobDTO> listByPage(@RequestBody SpringJobHistory springJobHistory,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringJobHistoryDTO> lists = springJobHistoryService.getAllRecordByPage(springJobHistory, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springJobHistoryService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}
}
