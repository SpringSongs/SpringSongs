package cn.spring.controller;

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

import cn.spring.domain.SpringContact;
import cn.spring.service.ISpringContactService;
import cn.spring.util.Constant;
import cn.spring.util.IpKit;
import cn.spring.util.R;

@RestController
@RequestMapping(value = "/SpringBusinessCard")
public class SpringContactController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringContactController.class);

	@Autowired
	private ISpringContactService baseBusinessCardService;

	@PostMapping(value = "/ListByPage")
	public R listByPage(@RequestBody SpringContact viewEntity, @PageableDefault(page = 0, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringContact> lists = baseBusinessCardService.getAllRecordByPage(viewEntity, pageable);
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

	@PostMapping(value = "/Detail")
	public R get(@NotEmpty(message = "id不能为空") String id) {
		R r = new R();
		try {
			SpringContact entity = baseBusinessCardService.selectByPrimaryKey(id);
			r.put("msg", Constant.SELECT_SUCCESSED);
			r.put("code", HttpServletResponse.SC_OK);
			r.put("data", entity);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/Create")
	public R save(@RequestBody @Valid SpringContact viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			viewEntity.setCreatedBy(this.getUser().getUserName());
			viewEntity.setCreatedUserId(this.getUser().getId());
			viewEntity.setCreatedIp(IpKit.getRealIp(request));
			viewEntity.setCreatedOn(new Date());
			baseBusinessCardService.insert(viewEntity);
			r.put("msg", Constant.SAVE_SUCCESSED);
			r.put("code", HttpServletResponse.SC_OK);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/Edit")
	public R update(@RequestBody @Valid SpringContact viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			SpringContact entity = baseBusinessCardService.selectByPrimaryKey(viewEntity.getId());
			if (null == entity) {
				r.put("msg", Constant.INFO_NOT_FOUND);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			} else {
				entity.setCompany(viewEntity.getCompany());
				entity.setTitle(viewEntity.getTitle());
				entity.setUsername(viewEntity.getUsername());
				entity.setEmail(viewEntity.getEmail());
				entity.setWeb(viewEntity.getWeb());
				entity.setFax(viewEntity.getFax());
				entity.setQq(viewEntity.getQq());
				entity.setWebchat(viewEntity.getWebchat());
				entity.setMobile(viewEntity.getMobile());
				entity.setTel(viewEntity.getTel());
				entity.setSortCode(viewEntity.getSortCode());
				entity.setUpdatedOn(new Date());
				entity.setUpdatedUserId(this.getUser().getId());
				entity.setUpdatedBy(this.getUser().getUserName());
				entity.setUpdatedIp(IpKit.getRealIp(request));
				baseBusinessCardService.updateByPrimaryKey(entity);
				r.put("msg", Constant.SAVE_SUCCESSED);
				r.put("code", HttpServletResponse.SC_OK);
			}
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/SetDeleted")
	public R setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		R r = new R();
		if (CollectionUtils.isEmpty(ids)) {
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
		} else {
			try {
				baseBusinessCardService.setDeleted(ids);
				r.put("msg", Constant.DELETE_SUCCESSED);
				r.put("code", HttpServletResponse.SC_OK);
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
				logger.error(e.getMessage());
			}
		}
		return r;
	}

	@PostMapping(value = "/Deleted")
	public R deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		R r = new R();
		if (CollectionUtils.isEmpty(ids)) {
			r.put("msg", "参数不允许为空!");
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
		} else {
			try {
				baseBusinessCardService.delete(ids);
				r.put("msg", Constant.DELETE_SUCCESSED);
				r.put("code", HttpServletResponse.SC_OK);
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);

			}
		}
		return r;
	}
}
