package io.github.springsongs.modules.process.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.process.dto.SpringActVacationApproveDTO;
import io.github.springsongs.modules.process.service.ISpringActVacationApproveService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;

@Api(tags = "请假流程审批管理")
@RestController
@RequestMapping(value = "/SpringActVacationApprove")
public class SpringActVacationApproveController extends BaseController {
	@Autowired
	private ISpringActVacationApproveService springActVacationApproveService;

	@PostMapping(value = "/CompleteSpringActVacationApprove")
	public ResponseDTO<String> completeSpringActVacationApprove(@RequestBody @Valid SpringActVacationApproveDTO viewEntity, @RequestParam(value = "taskId") String taskId,
			HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		viewEntity.setTrueName(this.getUser().getTrueName());
		viewEntity.setUserId(this.getUser().getId());
		springActVacationApproveService.completeSpringActVacationApprove(viewEntity, taskId);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}
}
