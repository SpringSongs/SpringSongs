package io.github.springsongs.controller;

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

import io.github.springsongs.domain.SpringOrganization;
import io.github.springsongs.domain.dto.SpringOrganizationDTO;
import io.github.springsongs.service.ISpringOrganizationService;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.IpKit;
import io.github.springsongs.util.R;

@RestController
@RequestMapping(value = "/SpringOrganization")
public class SpringOrganizationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringOrganizationController.class);

	@Autowired
	private ISpringOrganizationService springOrganizationService;

	@PostMapping(value = "ListByPage")
	public R getPage(@RequestBody SpringOrganization viewEntity,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringOrganizationDTO> lists = springOrganizationService.getAllRecordByPage(viewEntity, pageable);
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
			SpringOrganization entity = springOrganizationService.selectByPrimaryKey(id);
			r.put("msg", "返回数据!");
			r.put("code", 200);
			r.put("data", entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			r.put("msg", "系统错误!");
			r.put("code", 500);
		}

		return r;
	}

	@PostMapping(value = "/Create")
	public R save(@RequestBody @Valid SpringOrganizationDTO viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			viewEntity.setCreatedBy(this.getUser().getUserName());
			viewEntity.setCreatedUserId(this.getUser().getId());
			viewEntity.setCreatedIp(IpKit.getRealIp(request));
			springOrganizationService.insert(viewEntity);
			r.put("msg", "保存成功!");
			r.put("code", 200);
		} catch (Exception e) {
			logger.error(e.getMessage());
			r.put("msg", "系统错误!");
			r.put("code", 500);
		}
		return r;
	}

	@PostMapping(value = "/Edit")
	public R update(@RequestBody @Valid SpringOrganizationDTO viewEntity, HttpServletRequest request) {
		R r = new R();

		try {
			SpringOrganizationDTO entity = springOrganizationService.selectByPrimaryKey(viewEntity.getId());
			if (null == entity) {
				r.put("msg", "信息不存在或者已经被删除!");
				r.put("code", 500);
			} else {
				entity.setId(viewEntity.getId());
				entity.setParentId(viewEntity.getParentId());
				entity.setCode(viewEntity.getCode());
				entity.setTitle(viewEntity.getTitle());
				entity.setDeletedStatus(viewEntity.getDeletedStatus());
				entity.setCreatedUserId(viewEntity.getCreatedUserId());
				entity.setCreatedBy(viewEntity.getCreatedBy());
				entity.setCreatedOn(viewEntity.getCreatedOn());
				entity.setCreatedIp(viewEntity.getCreatedIp());
				entity.setUpdatedUserId(viewEntity.getUpdatedUserId());
				entity.setUpdatedBy(viewEntity.getUpdatedBy());
				entity.setUpdatedOn(viewEntity.getUpdatedOn());
				entity.setUpdatedIp(viewEntity.getUpdatedIp());
				springOrganizationService.updateByPrimaryKey(entity);
				r.put("msg", "保存成功!");
				r.put("code", 200);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			r.put("msg", "系统错误!");
			r.put("code", 500);
		}
		return r;
	}

	@PostMapping(value = "/SetDeleted")
	public R setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		R r = R.succeed();
		if (CollectionUtils.isEmpty(ids)) {
			r.put("msg", "参数不允许为空!");
			r.put("code", 500);
		} else {
			try {
				for (String id : ids) {
					List<SpringOrganizationDTO> entitis = springOrganizationService.listOrganizationsByParent(id);
					if (entitis.size() > 0) {
						r.put("msg", Constant.HASED_CHILD_IDS_CANNOT_DELETE);
						r.put("code", 500);
						break;
					}
				}
				if (r.get("code").toString().equals(String.valueOf(HttpServletResponse.SC_OK))) {
					springOrganizationService.setDeleted(ids);
					r.put("msg", "删除成功!");
					r.put("code", 200);
				}
			} catch (Exception e) {

				r.put("msg", "系统错误!");
				r.put("code", 500);
			}
		}

		return r;
	}

	@PostMapping(value = "/listOrganizationsByParent")
	public R getOrganizationsByParent(@RequestParam(value = "parentId", required = true) String parentId) {
		R r = new R();
		if (StringUtils.isEmpty(parentId)) {
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
		} else {
			try {
				List<SpringOrganizationDTO> elementUiTreeDtoList = springOrganizationService
						.listOrganizationsByParent(parentId);
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

	@PostMapping(value = "/listAllRecord")
	public R listAllRecord() {
		R r = new R();
		try {
			List<SpringOrganizationDTO> entitys = springOrganizationService.listAll();
			r.put("code", HttpServletResponse.SC_OK);
			r.put("data", entitys);
			r.put("msg", Constant.SELECT_SUCCESSED);
		} catch (Exception e) {

			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			r.put("msg", Constant.SYSTEM_ERROR);
			logger.error(e.getMessage());
		}
		return r;
	}
}
