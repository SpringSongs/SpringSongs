package io.github.springsongs.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.domain.SpringUser;
import io.github.springsongs.domain.SpringUserDTO;
import io.github.springsongs.domain.SpringUserRole;
import io.github.springsongs.domain.SpringUserSecurity;
import io.github.springsongs.domain.query.SpringUserQuery;
import io.github.springsongs.service.ISpringUserService;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.HttpUtils;
import io.github.springsongs.util.IpKit;
import io.github.springsongs.util.R;

@RestController
@RequestMapping(value = "/SpringUser")
public class SpringUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringUserController.class);

	@Autowired
	private ISpringUserService springUserService;

	@PostMapping(value = "Invalidate")
	public R invalidateSession(HttpServletRequest reqeust, HttpServletResponse response) {
		R r = new R();
		if (HttpUtils.isAjaxRequest(reqeust)) {
			r.put("code", HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED);
			r.put("msg", Constant.SESSION_HAS_GONE);
		} else {
			try {
				response.sendRedirect("/login");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return r;
	}

	@PostMapping(value = "/ListByPage")
	public R listByPage(@RequestBody SpringUserQuery springUserQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringUserDTO> lists = springUserService.getAllRecordByPage(springUserQuery, pageable);
			r.put("code", HttpServletResponse.SC_OK);
			r.put("msg", Constant.SELECT_SUCCESSED);
			r.put("data", lists.getContent());
			r.put("count", lists.getTotalElements());
		} catch (Exception e) {
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			r.put("msg", Constant.SYSTEM_ERROR);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/ListByRoleId/{roleId}")
	public R listByRoleId(@PathVariable(value = "roleId", required = true) String roleId,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringUserDTO> lists = springUserService.ListUsersByRoleId(roleId, pageable);
			r.put("code", HttpServletResponse.SC_OK);
			r.put("msg", Constant.SELECT_SUCCESSED);
			r.put("data", lists.getContent());
			r.put("count", lists.getTotalElements());

		} catch (Exception e) {
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			r.put("msg", Constant.SYSTEM_ERROR);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/Detail")
	public R get(@NotEmpty(message = "id不能为空") String id) {
		R r = new R();
		try {
			SpringUser entity = springUserService.selectByPrimaryKey(id);
			r.put("msg", Constant.SELECT_SUCCESSED);
			r.put("code", HttpServletResponse.SC_OK);
			r.put("data", entity);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
		}
		return r;
	}

	@PostMapping(value = "/Create")
	public R save(@RequestBody @Valid SpringUserDTO viewEntity, HttpServletRequest request) {
		R r = new R();

		try {
			SpringUserDTO entity = springUserService.getByUserName(viewEntity.getUserName().trim());
			if (null != entity) {
				r.put("msg", Constant.ACCOUNT_HAS_REGISTER);
				r.put("code", HttpServletResponse.SC_OK);
			} else {
				viewEntity.setCreatedBy(this.getUser().getUserName());
				viewEntity.setCreatedUserId(this.getUser().getId());
				viewEntity.setCreatedIp(IpKit.getRealIp(request));
				viewEntity.setCreatedOn(new Date());
				springUserService.insert(viewEntity);
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

	@PostMapping(value = "/Edit")
	public R update(@RequestBody @Valid SpringUserDTO viewEntity, HttpServletRequest request) {
		R r = new R();

		try {
			SpringUserDTO entity = springUserService.selectByPrimaryKey(viewEntity.getId());
			if (null == entity) {
				r.put("msg", Constant.INFO_NOT_FOUND);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			} else if (!entity.getEnableEdit()) {
				r.put("msg", Constant.INFO_CAN_NOT_EDIT);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			} else {
				entity.setTrueName(viewEntity.getTrueName());
				entity.setEmail(viewEntity.getEmail());
				entity.setMobile(viewEntity.getMobile());
				entity.setOrganizationId(viewEntity.getOrganizationId());
				entity.setOrganizationName(viewEntity.getOrganizationName());
				entity.setEnableEdit(viewEntity.getEnableEdit());
				entity.setEnableDelete(viewEntity.getEnableDelete());
				entity.setUpdatedOn(new Date());
				entity.setUpdatedUserId(this.getUser().getId());
				entity.setUpdatedBy(this.getUser().getUserName());
				entity.setUpdatedIp(IpKit.getRealIp(request));
				springUserService.updateByPrimaryKey(entity);
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
				List<SpringUserDTO> entityList = springUserService.listUserByIds(ids);
				for (SpringUserDTO entity : entityList) {
					if (entity.getEnableDelete() == false) {
						r.put("msg", MessageFormat.format(Constant.INFO_CAN_NOT_DELETE, entity.getUserName()));
						r.put("code", HttpServletResponse.SC_BAD_REQUEST);
						break;
					}
				}
				if (r.get("code").toString().equals(String.valueOf(HttpServletResponse.SC_OK))) {
					springUserService.setDeleted(ids);
					r.put("msg", Constant.DELETE_SUCCESSED);
					r.put("code", HttpServletResponse.SC_OK);
				}
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
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
		} else {
			try {
				List<SpringUserDTO> entityList = springUserService.listUserByIds(ids);
				for (SpringUserDTO entity : entityList) {
					if (entity.getEnableDelete() == false) {
						r.put("msg", MessageFormat.format(Constant.INFO_CAN_NOT_DELETE, entity.getUserName()));
						r.put("code", HttpServletResponse.SC_BAD_REQUEST);
						break;
					}
				}
				if (r.get("code").toString().equals(String.valueOf(HttpServletResponse.SC_OK))) {
					springUserService.delete(ids);
					r.put("msg", Constant.DELETE_SUCCESSED);
					r.put("code", HttpServletResponse.SC_OK);
				}
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
				logger.error(e.getMessage());
			}
		}
		return r;
	}

	@PostMapping(value = "/SetPwd")
	public R SetPwd(@RequestBody SpringUserSecurity viewEntity, HttpServletRequest request) {
		R r = new R();
		if (StringUtils.isEmpty(viewEntity.getUserId())) {
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
		} else if (StringUtils.isEmpty(viewEntity.getPwd())) {
			r.put("msg", Constant.PASSWORD_CAN_NOT_EMPTY);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
		} else {
			try {
				viewEntity.setCreatedBy(this.getUser().getUserName());
				viewEntity.setCreatedUserId(this.getUser().getId());
				viewEntity.setCreatedIp(IpKit.getRealIp(request));
				viewEntity.setCreatedOn(new Date());
				viewEntity.setUpdatedOn(new Date());
				viewEntity.setUpdatedUserId(this.getUser().getId());
				viewEntity.setUpdatedBy(this.getUser().getUserName());
				viewEntity.setUpdatedIp(IpKit.getRealIp(request));
				r = springUserService.setPwd(viewEntity);
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
				logger.error(e.getMessage());
			}
		}
		return r;
	}

	@PostMapping(value = "/SetRoles/{userId}")
	public R setUsers(@PathVariable(value = "userId", required = true) String userId,
			@RequestParam(value = "ids", required = true) List<String> roleIds, HttpServletRequest request) {
		R r = new R();
		try {
			List<SpringUserRole> baseUserRoleEntityList = new ArrayList<SpringUserRole>();
			for (String str : roleIds) {
				SpringUserRole entity = new SpringUserRole();
				entity.setRoleId(str);
				entity.setUserId(userId);
				entity.setCreatedBy(this.getUser().getUserName());
				entity.setCreatedUserId(this.getUser().getId());
				entity.setCreatedIp(IpKit.getRealIp(request));
				entity.setCreatedOn(new Date());
				baseUserRoleEntityList.add(entity);
			}
			springUserService.saveUserToRole(baseUserRoleEntityList, userId);
			r.put("msg", Constant.ROLE_TO_USER_SETTING_SUCCESSED);
			r.put("code", HttpServletResponse.SC_OK);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

}