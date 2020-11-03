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

import cn.spring.domain.SpringSystem;
import cn.spring.domain.query.SpringSystemQuery;
import cn.spring.service.ISpringSystemService;
import cn.spring.util.Constant;
import cn.spring.util.IpKit;
import cn.spring.util.R;

@RestController
@RequestMapping(value = "/SpringSystem")
public class SpringSystemController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringSystemController.class);

	@Autowired
	private ISpringSystemService springSystemService;

	@PostMapping(value = "/ListByPage")
	public R getPage(@RequestBody SpringSystemQuery springSystemQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringSystem> lists = springSystemService.getAllRecordByPage(springSystemQuery, pageable);
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
		R r = new R();
		if (StringUtils.isEmpty(id)) {
			r.put("msg", "参数不允许为空");
			r.put("code", 500);
		} else {
			try {
				SpringSystem entity = springSystemService.selectByPrimaryKey(id);
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

	@PostMapping(value = "/save")
	public R save(@RequestBody SpringSystem viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			viewEntity.setCreatedBy(this.getUser().getUserName());
			viewEntity.setCreatedUserId(this.getUser().getId());
			viewEntity.setCreatedIp(IpKit.getRealIp(request));
			viewEntity.setCreatedOn(new Date());
			springSystemService.insert(viewEntity);
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
	public R update(@RequestBody SpringSystem viewEntity, HttpServletRequest request) {
		R r = new R();

		try {
			SpringSystem entity = springSystemService.selectByPrimaryKey(viewEntity.getId());
			if (null == entity) {
				r.put("msg", "信息不存在或者已经被删除!");
				r.put("code", 500);
			} else {
				entity.setCode(viewEntity.getCode());
				entity.setTitle(viewEntity.getTitle());
				entity.setDescription(viewEntity.getDescription());
				entity.setEnableDelete(viewEntity.getEnableDelete());
				entity.setEnableEdit(viewEntity.getEnableEdit());
				entity.setUpdatedUserId(viewEntity.getUpdatedUserId());
				entity.setUpdatedBy(viewEntity.getUpdatedBy());
				entity.setUpdatedOn(viewEntity.getUpdatedOn());
				entity.setUpdatedIp(viewEntity.getUpdatedIp());
				springSystemService.updateByPrimaryKey(entity);
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
		R r = new R();
		if (CollectionUtils.isEmpty(ids)) {
			r.put("msg", "参数不允许为空!");
			r.put("code", 500);
		} else {
			try {
				List<SpringSystem> springSystemList = springSystemService.findInIds(ids);
				for (SpringSystem entity : springSystemList) {
					if (entity.getEnableDelete() == false) {
						r.put("msg", entity.getTitle() + "不允许删除!");
						r.put("code", 500);
						break;
					}
				}
				if (r.get("code").toString().equals("200")) {
					springSystemService.setDeleted(ids);
					r.put("msg", "删除成功!");
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

	@PostMapping(value = "/ListAll")
	public R listAll() {
		R r = new R();
		List<SpringSystem> springSystemList = null;
		try {
			springSystemList = springSystemService.ListAll();
			r.put("msg", "删除成功!");
			r.put("data", springSystemList);
			r.put("code", 200);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			r.put("msg", "系统错误!");
			r.put("code", 500);
		}
		return r;
	}
}
