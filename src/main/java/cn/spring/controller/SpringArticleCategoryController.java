package cn.spring.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import cn.spring.domain.SpringArticleCategory;
import cn.spring.service.ISpringArticleCategoryService;
import cn.spring.util.Constant;
import cn.spring.util.IpKit;
import cn.spring.util.R;
import cn.spring.vo.ElementUiTreeVo;

@RestController
@RequestMapping(value = "/SpringArticleCategory")
public class SpringArticleCategoryController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringArticleCategoryController.class);

	@Autowired
	private ISpringArticleCategoryService baseSpringArticleCategoryService;


	@PostMapping(value = "/ListByPage")
	public R getPage(@RequestBody SpringArticleCategory viewEntity,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		long startTime = System.currentTimeMillis();
		R r = new R();
		try {
			Page<SpringArticleCategory> lists = baseSpringArticleCategoryService.getAllRecordByPage(viewEntity,
					pageable);
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
	public R get(String id) {
		long startTime = System.currentTimeMillis();
		R r = new R();
		if (StringUtils.isEmpty(id)) {
			r.put("msg", "参数不允许为空");
			r.put("code", 500);
		} else {
			try {
				SpringArticleCategory entity = baseSpringArticleCategoryService.selectByPrimaryKey(id);
				r.put("msg", "返回数据!");
				r.put("code", 200);
				r.put("data", entity);
			} catch (Exception e) {
				logger.error(e.getMessage());
				r.put("msg", "系统错误!");
				r.put("code", 500);
			}
		}
		return r;
	}

	@PostMapping(value = "/Create")
	public R save(@RequestBody SpringArticleCategory viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			viewEntity.setCreatedBy(this.getUser().getUserName());
			viewEntity.setCreatedUserId(this.getUser().getId());
			viewEntity.setCreatedIp(IpKit.getRealIp(request));
			viewEntity.setCreatedOn(new Date());
			baseSpringArticleCategoryService.insert(viewEntity);
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
	public R update(@RequestBody SpringArticleCategory viewEntity, HttpServletRequest request) {
		R r = new R();
		{
			try {
				SpringArticleCategory entity = baseSpringArticleCategoryService.selectByPrimaryKey(viewEntity.getId());
				if (null == entity) {
					r.put("msg", "信息不存在或者已经被删除!");
					r.put("code", 500);
				} else {
					entity.setParentId(viewEntity.getParentId());
					entity.setCode(viewEntity.getCode());
					entity.setTitle(viewEntity.getTitle());
					entity.setKeywords(viewEntity.getKeywords());
					entity.setDescription(viewEntity.getDescription());
					entity.setSortOrder(viewEntity.getSortOrder());
					entity.setUpdatedOn(new Date());
					entity.setUpdatedUserId(this.getUser().getId());
					entity.setUpdatedBy(this.getUser().getUserName());
					entity.setUpdatedIp(IpKit.getRealIp(request));
					entity.setVersion(viewEntity.getVersion());
					baseSpringArticleCategoryService.updateByPrimaryKey(entity);
					r.put("msg", "保存成功!");
					r.put("code", 200);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				r.put("msg", "系统错误!");
				r.put("code", 500);
			}
		}
		return r;
	}

	@PostMapping(value = "/SetDeleted")
	public R setDeleted(@RequestParam(value = "ids", required = true)List<String> ids) {
		R r = R.ok("OK");
		if (CollectionUtils.isEmpty(ids)) {
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
		} else {
			try {
				for(String id : ids ) {
					List<SpringArticleCategory> entitys=baseSpringArticleCategoryService.getByParentId(id);
					if (entitys.size()>0) {
						r.put("msg", Constant.HASED_CHILD_IDS);
						r.put("code", HttpServletResponse.SC_BAD_REQUEST);
						break;
					}
				}
				if (r.get("code").toString().equals(String.valueOf(HttpServletResponse.SC_OK))) {
					baseSpringArticleCategoryService.setDeleted(ids);
					r.put("msg", Constant.DELETE_SUCCESSED);
					r.put("code", HttpServletResponse.SC_OK);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				r.put("msg", "系统错误!");
				r.put("code", 500);
			}
		}
		return r;
	}
	
	@PostMapping(value = "/GetCategorysByParent")
	public R getModuleByParentId(@RequestParam(value = "parentId", required = true) String parentId) {
		R r = new R();
		if (StringUtils.isEmpty(parentId)) {
			r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
		} else {
			try {
				List<ElementUiTreeVo> elementUiTreeDtoList = baseSpringArticleCategoryService.getCategoryByParentId(parentId);
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
			List<SpringArticleCategory> entitys = baseSpringArticleCategoryService.listAll();
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
