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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.spring.domain.SpringAritlce;
import cn.spring.service.ISpringAritlceService;
import cn.spring.util.Constant;
import cn.spring.util.IpKit;
import cn.spring.util.R;

@RestController
@RequestMapping(value = "/SpringAritlce")
public class SpringAritlceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringAritlceController.class);
	
	@Autowired
	private ISpringAritlceService springAritlceService;

	@PostMapping(value = "/ListByPage")
	public R listByPage(@RequestBody SpringAritlce viewEntity, @PageableDefault(page = 1, size = 20) Pageable pageable) {
		R r = new R();
		try {
			Page<SpringAritlce> lists = springAritlceService.getAllRecordByPage(viewEntity, pageable);
			r.put("code", HttpServletResponse.SC_OK);
			r.put("msg", Constant.SELECT_SUCCESSED);
			r.put("data", lists.getContent());
			r.put("count", lists.getTotalElements());
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", 500);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/Detail")
	public R get(@NotEmpty(message = "id不能为空") String id) {
		R r = new R();
		try {
			SpringAritlce entity = springAritlceService.selectByPrimaryKey(id);
			r.put("msg", Constant.SELECT_SUCCESSED);
			r.put("code", HttpServletResponse.SC_OK);
			r.put("data", entity);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", 500);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/Create")
	public R save(@RequestBody @Valid SpringAritlce viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			viewEntity.setCreatedBy(this.getUser().getUserName());
			viewEntity.setCreatedUserId(this.getUser().getId());
			viewEntity.setCreatedIp(IpKit.getRealIp(request));
			viewEntity.setCreatedOn(new Date());
			springAritlceService.insert(viewEntity);
			r.put("msg", Constant.SAVE_SUCCESSED);
			r.put("code", 200);
		} catch (Exception e) {
			r.put("msg", Constant.SYSTEM_ERROR);
			r.put("code", 500);
			logger.error(e.getMessage());
		}
		return r;
	}

	@PostMapping(value = "/Edit")
	public R update(@RequestBody @Valid SpringAritlce viewEntity, HttpServletRequest request) {
		R r = new R();
		try {
			SpringAritlce entity = springAritlceService.selectByPrimaryKey(viewEntity.getId());
			if (null == entity) {
				r.put("msg", Constant.INFO_NOT_FOUND);
				r.put("code", HttpServletResponse.SC_BAD_REQUEST);
			} else {
				entity.setCategoryId(viewEntity.getCategoryId());
 				entity.setCategoryTitle(viewEntity.getCategoryTitle());
 				entity.setColor(viewEntity.getColor());
 				entity.setTag(viewEntity.getTag());
 				entity.setKeyword(viewEntity.getKeyword());
 				entity.setTitle(viewEntity.getTitle());
 				entity.setSummary(viewEntity.getSummary());
 				entity.setContents(viewEntity.getContents());
 				entity.setAuthorId(viewEntity.getAuthorId());
 				entity.setAuthor(viewEntity.getAuthor());
 				entity.setAuthorUrl(viewEntity.getAuthorUrl());
 				entity.setStatus(viewEntity.getStatus());
 				entity.setLink(viewEntity.getLink());
 				entity.setReadCount(viewEntity.getReadCount());
 				entity.setLikeCount(viewEntity.getLikeCount());
 				entity.setCollectCount(viewEntity.getCollectCount());
 				entity.setShareCount(viewEntity.getShareCount());
 				entity.setTopStatus(viewEntity.getTopStatus());
 				entity.setHotStatus(viewEntity.getHotStatus());
 				entity.setFeatured(viewEntity.getFeatured());
 				entity.setCommentCount(viewEntity.getCommentCount());
 				entity.setSortOrder(viewEntity.getSortOrder());
 				entity.setComeFrom(viewEntity.getComeFrom());
 				entity.setComeFromLink(viewEntity.getComeFromLink());
				entity.setUpdatedOn(new Date());
				entity.setUpdatedUserId(this.getUser().getId());
				entity.setUpdatedBy(this.getUser().getUserName());
				entity.setUpdatedIp(IpKit.getRealIp(request));
				springAritlceService.updateByPrimaryKey(entity);
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
				springAritlceService.setDeleted(ids);
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
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", 500);
		} else {
			try {
				springAritlceService.delete(ids);
				r.put("msg", Constant.DELETE_SUCCESSED);
				r.put("code", 200);
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", 500);
				logger.error(e.getMessage());
			}
		}
		return r;
	}

	@PostMapping(value = "/Audit/{id}")
	public R audit(@PathVariable(value = "id", required = true) String id) {
		R r = new R();
		if (StringUtils.isEmpty(id)) {
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", 500);
		} else {
			try {
				SpringAritlce entity = springAritlceService.selectByPrimaryKey(id);
				if (null == entity) {
					r.put("msg", Constant.INFO_CAN_NOT_DELETE);
					r.put("code", 500);
				} else {
					if (entity.getStatus()== true) {
						entity.setStatus(false);
					} else {
						entity.setStatus(true);
					}
					springAritlceService.updateByPrimaryKey(entity);
					r.put("msg", Constant.SAVE_SUCCESSED);
					r.put("code", 200);
				}
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", 500);
				logger.error(e.getMessage());
			}

		}
		return r;
	}
	
	@PostMapping(value = "/HotStatus/{id}")
	public R hotStatus(@PathVariable(value = "id", required = true) String id) {
		R r = new R();
		if (StringUtils.isEmpty(id)) {
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", 500);
		} else {
			try {
				SpringAritlce entity = springAritlceService.selectByPrimaryKey(id);
				if (null == entity) {
					r.put("msg", Constant.INFO_CAN_NOT_DELETE);
					r.put("code", 500);
				} else {
					if (entity.getHotStatus()== true) {
						entity.setHotStatus(false);
					} else {
						entity.setHotStatus(true);
					}
					springAritlceService.updateByPrimaryKey(entity);
					r.put("msg", Constant.SAVE_SUCCESSED);
					r.put("code", 200);
				}
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", 500);
				logger.error(e.getMessage());
			}

		}
		return r;
	}
	
	@PostMapping(value = "/TopStatus/{id}")
	public R topStatus(@PathVariable(value = "id", required = true) String id) {
		R r = new R();
		if (StringUtils.isEmpty(id)) {
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", 500);
		} else {
			try {
				SpringAritlce entity = springAritlceService.selectByPrimaryKey(id);
				if (null == entity) {
					r.put("msg", Constant.INFO_CAN_NOT_DELETE);
					r.put("code", 500);
				} else {
					if (entity.getTopStatus()== true) {
						entity.setTopStatus(false);
					} else {
						entity.setTopStatus(true);
					}
					springAritlceService.updateByPrimaryKey(entity);
					r.put("msg", Constant.SAVE_SUCCESSED);
					r.put("code", 200);
				}
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", 500);
				logger.error(e.getMessage());
			}

		}
		return r;
	}
	
	@PostMapping(value = "/Featured/{id}")
	public R featured(@PathVariable(value = "id", required = true) String id) {
		R r = new R();
		if (StringUtils.isEmpty(id)) {
			r.put("msg", Constant.PARAMETER_NOT_NULL_ERROR);
			r.put("code", 500);
		} else {
			try {
				SpringAritlce entity = springAritlceService.selectByPrimaryKey(id);
				if (null == entity) {
					r.put("msg", Constant.INFO_CAN_NOT_DELETE);
					r.put("code", 500);
				} else {
					if (entity.getFeatured()== true) {
						entity.setFeatured(false);
					} else {
						entity.setFeatured(true);
					}
					springAritlceService.updateByPrimaryKey(entity);
					r.put("msg", Constant.SAVE_SUCCESSED);
					r.put("code", 200);
				}
			} catch (Exception e) {
				r.put("msg", Constant.SYSTEM_ERROR);
				r.put("code", 500);
				logger.error(e.getMessage());
			}

		}
		return r;
	}
}
