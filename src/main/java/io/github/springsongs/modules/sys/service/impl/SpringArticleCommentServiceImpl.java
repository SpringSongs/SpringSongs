package io.github.springsongs.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringArticleComment;
import io.github.springsongs.modules.sys.dto.SpringArticleCommentDTO;
import io.github.springsongs.modules.sys.dto.query.SpringArticleCommentQuery;
import io.github.springsongs.modules.sys.repo.SpringArticleCommentRepo;
import io.github.springsongs.modules.sys.service.ISpringArticleCommentService;
import io.github.springsongs.util.Constant;

@Service
public class SpringArticleCommentServiceImpl implements ISpringArticleCommentService {

	static Logger logger = LoggerFactory.getLogger(SpringArticleCategoryServiceImpl.class);

	@Autowired
	private SpringArticleCommentRepo springCommentDao;

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
			springCommentDao.deleteById(id);
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
	public void insert(SpringArticleCommentDTO record) {
		SpringArticleComment springArticleComment = new SpringArticleComment();
		BeanUtils.copyProperties(record, springArticleComment);
		try {
			springCommentDao.save(springArticleComment);
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
	public SpringArticleCommentDTO selectByPrimaryKey(String id) {
		SpringArticleComment springArticleComment = null;
		try {
			springArticleComment = springCommentDao.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
		SpringArticleCommentDTO springArticleCommentDTO = new SpringArticleCommentDTO();
		BeanUtils.copyProperties(springArticleComment, springArticleCommentDTO);
		return springArticleCommentDTO;
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
	public void updateByPrimaryKey(SpringArticleCommentDTO springArticleCommentDTO) {
		SpringArticleComment entity = springCommentDao.getOne(springArticleCommentDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setContent(springArticleCommentDTO.getContent());
			entity.setArticleId(springArticleCommentDTO.getArticleId());
			entity.setAuditFlag(springArticleCommentDTO.getAuditFlag());
			entity.setSortCode(springArticleCommentDTO.getSortCode());
			entity.setDeletedStatus(springArticleCommentDTO.getDeletedStatus());
			try {
				springCommentDao.save(entity);
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
	 * @return Page<BaseCommentEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringArticleCommentDTO> getAllRecordByPage(SpringArticleCommentQuery springArticleCommentQuery,
			Pageable pageable) {
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		Specification<SpringArticleComment> specification = new Specification<SpringArticleComment>() {

			@Override
			public Predicate toPredicate(Root<SpringArticleComment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (!StringUtils.isEmpty(springArticleCommentQuery.getContent())) {
					Predicate content = cb.like(root.get("content").as(String.class),
							springArticleCommentQuery.getContent() + "%");
					predicates.add(content);
				}
				if (!StringUtils.isEmpty(springArticleCommentQuery.getCreatedBy())) {
					Predicate createdBy = cb.like(root.get("createdBy").as(String.class),
							springArticleCommentQuery.getCreatedBy() + "%");
					predicates.add(createdBy);
				}

				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		Page<SpringArticleComment> springArticleComments = springCommentDao.findAll(specification, pageable);
		List<SpringArticleCommentDTO> SpringArticleCommentDTOs = new ArrayList<>();
		springArticleComments.stream().forEach(springArticleComment -> {
			SpringArticleCommentDTO springArticleCommentDTO = new SpringArticleCommentDTO();
			BeanUtils.copyProperties(springArticleComment, springArticleCommentDTO);
			SpringArticleCommentDTOs.add(springArticleCommentDTO);
		});
		Page<SpringArticleCommentDTO> pages = new PageImpl(SpringArticleCommentDTOs, pageable,
				springArticleComments.getTotalElements());
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
	public void setDeleted(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springCommentDao.setDelete(ids);
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
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springCommentDao.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void audit(String id) {
		SpringArticleComment entity = springCommentDao.getOne(id);
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			if (entity.getAuditFlag() == true) {
				entity.setAuditFlag(false);
			} else {
				entity.setAuditFlag(true);
			}
			try {
				springCommentDao.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}

	}
}
