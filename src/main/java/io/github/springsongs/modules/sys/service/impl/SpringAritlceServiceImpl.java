package io.github.springsongs.modules.sys.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringAritlce;
import io.github.springsongs.modules.sys.dto.SpringAritlceDTO;
import io.github.springsongs.modules.sys.query.SpringAritlceQuery;
import io.github.springsongs.modules.sys.repo.SpringAritlceRepo;
import io.github.springsongs.modules.sys.service.ISpringAritlceService;
import io.github.springsongs.util.Constant;

@Service
public class SpringAritlceServiceImpl implements ISpringAritlceService {

	static Logger logger = LoggerFactory.getLogger(SpringAritlceServiceImpl.class);

	@Autowired
	private SpringAritlceRepo springAritlceDao;

	/**
	 *
	 * 物理删除
	 * 
	 * @param id
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void deleteByPrimaryKey(String id) {
		try {
			springAritlceDao.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 *
	 * 保存
	 * 
	 * @param record
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void insert(SpringAritlceDTO record) {
		SpringAritlce springAritlce = new SpringAritlce();
		BeanUtils.copyProperties(record, springAritlce);
		try {
			springAritlceDao.save(springAritlce);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 *
	 * 获取单项
	 * 
	 * @param id
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public SpringAritlceDTO selectByPrimaryKey(String id) {
		SpringAritlce springAritlce;
		try {
			springAritlce = springAritlceDao.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringAritlceDTO springAritlceDTO = new SpringAritlceDTO();
		BeanUtils.copyProperties(springAritlce, springAritlceDTO);
		return springAritlceDTO;
	}

	/**
	 *
	 * 更新
	 * 
	 * @param record
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void updateByPrimaryKey(SpringAritlceDTO springAritlceDTO) {
		SpringAritlce entity = springAritlceDao.getOne(springAritlceDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setCategoryId(springAritlceDTO.getCategoryId());
			entity.setCategoryTitle(springAritlceDTO.getCategoryTitle());
			entity.setColor(springAritlceDTO.getColor());
			entity.setTag(springAritlceDTO.getTag());
			entity.setKeyword(springAritlceDTO.getKeyword());
			entity.setTitle(springAritlceDTO.getTitle());
			entity.setSummary(springAritlceDTO.getSummary());
			entity.setContents(springAritlceDTO.getContents());
			entity.setAuthorId(springAritlceDTO.getAuthorId());
			entity.setAuthor(springAritlceDTO.getAuthor());
			entity.setAuthorUrl(springAritlceDTO.getAuthorUrl());
			entity.setStatus(springAritlceDTO.getStatus());
			entity.setLink(springAritlceDTO.getLink());
			entity.setReadCount(springAritlceDTO.getReadCount());
			entity.setLikeCount(springAritlceDTO.getLikeCount());
			entity.setCollectCount(springAritlceDTO.getCollectCount());
			entity.setShareCount(springAritlceDTO.getShareCount());
			entity.setTopStatus(springAritlceDTO.getTopStatus());
			entity.setHotStatus(springAritlceDTO.getHotStatus());
			entity.setFeatured(springAritlceDTO.getFeatured());
			entity.setCommentCount(springAritlceDTO.getCommentCount());
			entity.setSortOrder(springAritlceDTO.getSortOrder());
			entity.setComeFrom(springAritlceDTO.getComeFrom());
			entity.setComeFromLink(springAritlceDTO.getComeFromLink());
			try {
				springAritlceDao.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseAritlceEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringAritlceDTO> getAllRecordByPage(SpringAritlceQuery springAritlceQuery, Pageable pageable) {
		if (pageable.getPageSize()<=0||pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		int page=pageable.getPageNumber()<=0?0:pageable.getPageNumber()-1;
		pageable = PageRequest.of(page, pageable.getPageSize());

		Specification<SpringAritlce> specification = new Specification<SpringAritlce>() {

			@Override
			public Predicate toPredicate(Root<SpringAritlce> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springAritlceQuery.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							springAritlceQuery.getCreatedUserId());
					predicates.add(createdUserId);
				}
				if (!StringUtils.isEmpty(springAritlceQuery.getCategoryId())) {
					Predicate categoryId = cb.equal(root.get("categoryId").as(String.class),
							springAritlceQuery.getCategoryId());
					predicates.add(categoryId);
				}

				if (null != springAritlceQuery.getSearchDate()) {
					if (!StringUtils.isEmpty(springAritlceQuery.getSearchDate().getCreateTimeStart())
							&& !StringUtils.isEmpty(springAritlceQuery.getSearchDate().getCreateTimeEnd())) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							Predicate createdOn;
							createdOn = cb.between(root.get("createdOn"),
									sdf.parse(springAritlceQuery.getSearchDate().getCreateTimeStart()),
									sdf.parse(springAritlceQuery.getSearchDate().getCreateTimeEnd()));
							predicates.add(createdOn);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}

				if (!StringUtils.isEmpty(springAritlceQuery.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class), springAritlceQuery.getTitle() + "%");
					predicates.add(title);
				}
				if (!StringUtils.isEmpty(springAritlceQuery.getAuthor())) {
					Predicate author = cb.like(root.get("author").as(String.class),
							"%" + springAritlceQuery.getAuthor());
					predicates.add(author);
				}
				if (!StringUtils.isEmpty(springAritlceQuery.getKeyword())) {
					Predicate keyword = cb.like(root.get("keyword").as(String.class),
							"%" + springAritlceQuery.getKeyword());
					predicates.add(keyword);
				}
				if (!StringUtils.isEmpty(springAritlceQuery.getTag())) {
					Predicate tag = cb.like(root.get("tag").as(String.class), "%" + springAritlceQuery.getTag());
					predicates.add(tag);
				}
				if (!StringUtils.isEmpty(springAritlceQuery.getSummary())) {
					Predicate summary = cb.like(root.get("summary").as(String.class),
							"%" + springAritlceQuery.getSummary());
					predicates.add(summary);
				}
				Predicate deletionStateCode = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletionStateCode);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.asc(root.get("sortOrder").as(Integer.class)),
						cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		Page<SpringAritlce> pringAritlces = springAritlceDao.findAll(specification, pageable);
		List<SpringAritlceDTO> springAritlceDTOs = new ArrayList<>();
		pringAritlces.stream().forEach(springAritlce -> {
			SpringAritlceDTO springAritlceDTO = new SpringAritlceDTO();
			BeanUtils.copyProperties(springAritlce, springAritlceDTO);
			springAritlceDTOs.add(springAritlceDTO);
		});
		Page<SpringAritlceDTO> pages = new PageImpl(springAritlceDTOs, pageable, pringAritlces.getTotalElements());
		return pages;
	}

	/**
	 *
	 * 逻辑删除
	 * 
	 * @param record
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	@Transactional
	public void setDeleted(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springAritlceDao.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 *
	 * Excel批量保存
	 * 
	 * @param list
	 * @return R
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void batchSaveExcel(List<String[]> list) {

	}

	@Override
	public void delete(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springAritlceDao.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void audit(String id) {
		SpringAritlce entity = springAritlceDao.getOne(id);
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			if (entity.getStatus() == true) {
				entity.setStatus(false);
			} else {
				entity.setStatus(true);
			}
			try {
				springAritlceDao.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	@Override
	public void hotStatus(String id) {
		SpringAritlce entity = springAritlceDao.getOne(id);
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			if (entity.getHotStatus() == true) {
				entity.setHotStatus(false);
			} else {
				entity.setHotStatus(true);
			}
			try {
				springAritlceDao.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	@Override
	public void topStatus(String id) {
		SpringAritlce entity = springAritlceDao.getOne(id);
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			if (entity.getTopStatus() == true) {
				entity.setTopStatus(false);
			} else {
				entity.setTopStatus(true);
			}
			try {
				springAritlceDao.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	@Override
	public void featured(String id) {
		SpringAritlce entity = springAritlceDao.getOne(id);
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			if (entity.getFeatured() == true) {
				entity.setFeatured(false);
			} else {
				entity.setFeatured(true);
			}
			try {
				springAritlceDao.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}
}
