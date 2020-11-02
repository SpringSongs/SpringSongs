package cn.spring.controller;

import java.text.MessageFormat;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.spring.domain.SpringResource;
import cn.spring.service.ISpringResourceService;
import cn.spring.util.Constant;
import cn.spring.util.IpKit;
import cn.spring.util.R;
import cn.spring.vo.ElementUiTreeVo;
import cn.spring.vo.MenuVo;

@RestController
@RequestMapping(value = "/SpringResource")
public class SpringResourceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringResourceController.class);

	@Autowired
	private ISpringResourceService springResourceService;

	@PostMapping(value = "ListByPage")
	public R listByPage(@RequestBody SpringResource viewEntity, @PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringResource> lists = springResourceService.getAllRecordByPage(viewEntity, pageable);
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
			SpringResource entity = springResourceService.selectByPrimaryKey(id);
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
	public R save(@RequestBody @Valid SpringResource viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			viewEntity.setCreatedBy(this.getUser().getUserName());
			viewEntity.setCreatedUserId(this.getUser().getId());
			viewEntity.setCreatedIp(IpKit.getRealIp(request));
			viewEntity.setCreatedOn(new Date());
			springResourceService.insert(viewEntity);
			r.put("msg", "保存成功!");
			r.put("code", HttpServletResponse.SC_OK);
		} catch (Exception e) {
			r.put("msg", "系统错误!");
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/Edit")
	public R update(@RequestBody @Valid SpringResource viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			SpringResource entity = springResourceService.selectByPrimaryKey(viewEntity.getId());
			if (null == entity) {
				r.put("msg", "信息不存在或者已经被删除!");
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			} else if (!entity.getEnableEdit()) {
				r.put("msg", "信息不允许编辑!");
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			} else {
				entity.setCode(viewEntity.getCode());
				entity.setTitle(viewEntity.getTitle());
				entity.setMenuFlag(viewEntity.getMenuFlag());
				entity.setVueUrl(viewEntity.getVueUrl());
				entity.setAngularUrl(viewEntity.getAngularUrl());
				entity.setParentId(viewEntity.getParentId());
				entity.setParentName(viewEntity.getParentName());
				entity.setSortCode(viewEntity.getSortCode());
				entity.setEnableEdit(viewEntity.getEnableEdit());
				entity.setEnableDelete(viewEntity.getEnableDelete());
				entity.setUpdatedOn(new Date());
				entity.setUpdatedUserId(this.getUser().getId());
				entity.setUpdatedBy(this.getUser().getUserName());
				entity.setUpdatedIp(IpKit.getRealIp(request));
				springResourceService.updateByPrimaryKey(entity);
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
				List<SpringResource> entityList = springResourceService.listByIds(ids);
				for (SpringResource entity : entityList) {
					if (entity.getEnableDelete() == false) {
						r.put("msg", MessageFormat.format(Constant.INFO_CAN_NOT_DELETE, entity.getTitle()));
						r.put("code", HttpServletResponse.SC_BAD_REQUEST);
						break;
					}
				}
				if (r.get("code").toString().equals(String.valueOf(HttpServletResponse.SC_OK))) {
					springResourceService.setDeleted(ids);
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

	@PostMapping(value = "/GetMenus")
	public R getMenus() {
		R r = new R();
		try {
			List<MenuVo> menuList = springResourceService.ListModuleByUserId(this.getUser().getId());
			r.put("msg", Constant.SELECT_SUCCESSED);
			r.put("data", menuList);
			r.put("code", HttpServletResponse.SC_OK);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/GetMenusByParent")
	public R getModuleByParentId(@RequestParam(value = "parentId", required = true) String parentId,
			@RequestParam(value = "systemId", required = true) String systemId) {
		R r = new R();
		if (StringUtils.isEmpty(parentId)) {
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
		} else {
			try {
				List<ElementUiTreeVo> elementUiTreeDtoList = springResourceService.getModulesByParentId(parentId, systemId);
				r.put("code", HttpServletResponse.SC_OK);
				r.put("data", elementUiTreeDtoList);
				r.put("msg", Constant.SELECT_SUCCESSED);
			} catch (Exception e) {

				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
				r.put("msg", Constant.SYSTEM_ERROR);
				logger.error(e.getMessage());
			}
		}
		return r;
	}

}
