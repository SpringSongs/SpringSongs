package cn.spring.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.spring.domain.SpringResourceRole;
import cn.spring.domain.SpringRole;
import cn.spring.domain.SpringUserRole;
import cn.spring.service.ISpringResourceService;
import cn.spring.service.ISpringRoleService;
import cn.spring.util.Constant;
import cn.spring.util.IpKit;
import cn.spring.util.R;

@RestController
@RequestMapping(value = "/SpringRole")
public class SpringRoleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringRoleController.class);

	@Autowired
	private ISpringRoleService springRoleService;

	@Autowired
	private ISpringResourceService springResourceService;

	@PostMapping(value = "ListByPage")
	public R listByPage(@RequestBody SpringRole viewEntity, @PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringRole> lists = springRoleService.getAllRecordByPage(viewEntity, pageable);
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

	@PostMapping(value = "ListByUserId/{userId}")
	public R listByUserId(@PathVariable(value = "userId", required = true) String userId,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringRole> lists = springRoleService.ListRoleByUserId(userId, pageable);
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
			SpringRole entity = springRoleService.selectByPrimaryKey(id);
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
	public R save(@RequestBody @Valid SpringRole viewEntity, HttpServletRequest request) {
		R r = new R();

		try {
			viewEntity.setCreatedBy(this.getUser().getUserName());
			viewEntity.setCreatedUserId(this.getUser().getId());
			viewEntity.setCreatedIp(IpKit.getRealIp(request));
			viewEntity.setCreatedOn(new Date());
			springRoleService.insert(viewEntity);
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
	public R update(@RequestBody @Valid SpringRole viewEntity, HttpServletRequest request) {
		R r = new R();

		try {
			SpringRole entity = springRoleService.selectByPrimaryKey(viewEntity.getId());
			if (null == entity) {
				r.put("msg", Constant.INFO_NOT_FOUND);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			} else if (!entity.getEnableEdit()) {
				r.put("msg", Constant.INFO_CAN_NOT_EDIT);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			} else {
				entity.setDesc(viewEntity.getDesc());
				entity.setEnableEdit(viewEntity.getEnableEdit());
				entity.setEnableDelete(viewEntity.getEnableDelete());
				entity.setUpdatedOn(new Date());
				entity.setUpdatedUserId(this.getUser().getId());
				entity.setUpdatedBy(this.getUser().getUserName());
				entity.setUpdatedIp(IpKit.getRealIp(request));
				springRoleService.updateByPrimaryKey(entity);
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
				List<SpringRole> entityList = springRoleService.listByIds(ids);
				for (SpringRole entity : entityList) {
					if (entity.getEnableDelete() == false) {
						r.put("msg", MessageFormat.format(Constant.INFO_CAN_NOT_DELETE, entity.getTitle()));
						r.put("code", HttpServletResponse.SC_BAD_REQUEST);
						break;
					}
				}
				if (r.get("code").toString().equals("HttpServletResponse.SC_OK")) {
					springRoleService.setDeleted(ids);
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
				List<SpringRole> entityList = springRoleService.listByIds(ids);
				for (SpringRole entity : entityList) {
					if (entity.getEnableDelete() == false) {
						r.put("msg", MessageFormat.format(Constant.INFO_CAN_NOT_DELETE, entity.getTitle()));
						r.put("code", HttpServletResponse.SC_BAD_REQUEST);
						break;
					}
				}
				if (r.get("code").toString().equals(String.valueOf(HttpServletResponse.SC_OK))) {
					springRoleService.delete(ids);
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

	@PostMapping(value = "/SetUsers/{roleId}")
	public R setUsers(@PathVariable(value = "roleId", required = true) String roleId,
			@RequestParam(value = "ids", required = true) List<String> userIds, HttpServletRequest request) {
		R r = new R();
		try {
			List<SpringUserRole> baseUserRoleEntityList = new ArrayList<SpringUserRole>();
			for (String str : userIds) {
				SpringUserRole entity = new SpringUserRole();
				entity.setRoleId(roleId);
				entity.setUserId(str);
				entity.setCreatedBy(this.getUser().getUserName());
				entity.setCreatedUserId(this.getUser().getId());
				entity.setCreatedIp(IpKit.getRealIp(request));
				entity.setCreatedOn(new Date());
				baseUserRoleEntityList.add(entity);
			}
			springRoleService.saveUserToRole(baseUserRoleEntityList, roleId);
			r.put("msg", Constant.USER_TO_ROLE_SETTING_SUCCESSED);
			r.put("code", HttpServletResponse.SC_OK);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/SetAuthority/{roleId}")
	public R setAuthority(@PathVariable(value = "roleId", required = true) String roleId,
			@RequestParam(value = "moduleIds", required = true) List<String> moduleIds, HttpServletRequest request) {
		R r = new R();
		try {
			List<SpringResourceRole> baseModuleRoleEntityList = new ArrayList<SpringResourceRole>();
			for (String str : moduleIds) {
				SpringResourceRole entity = new SpringResourceRole();
				entity.setRoleId(roleId);
				entity.setModuleId(str);
				entity.setCreatedBy(this.getUser().getUserName());
				entity.setCreatedUserId(this.getUser().getId());
				entity.setCreatedIp(IpKit.getRealIp(request));
				entity.setCreatedOn(new Date());
				baseModuleRoleEntityList.add(entity);
			}
			springResourceService.saveModuleToRole(baseModuleRoleEntityList, roleId);
			r.put("msg", Constant.AUTHORITY_SUCCESSED);
			r.put("code", HttpServletResponse.SC_OK);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/ListAuthority/{roleId}")
	public R listAuthority(@PathVariable(value = "roleId", required = true) String roleId) {
		R r = new R();
		try {
			List<SpringResourceRole> baseModuleRoleEntityList = springResourceService.listModulesByRoleId(roleId);
			List<String> moduleIds = new ArrayList<String>();
			baseModuleRoleEntityList.stream().forEach(item -> {
				moduleIds.add(item.getModuleId());
			});
			r.put("msg", Constant.AUTHORITY_SUCCESSED);
			r.put("code", HttpServletResponse.SC_OK);
			r.put("data", moduleIds);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}
}
