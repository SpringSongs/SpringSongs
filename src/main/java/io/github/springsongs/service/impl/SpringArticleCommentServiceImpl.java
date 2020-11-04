package io.github.springsongs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.github.springsongs.dao.SpringArticleCommentDao;
import io.github.springsongs.domain.SpringArticleCategory;
import io.github.springsongs.domain.SpringArticleComment;
import io.github.springsongs.domain.dto.SpringArticleCategoryDTO;
import io.github.springsongs.domain.dto.SpringArticleCommentDTO;
import io.github.springsongs.domain.query.SpringArticleCommentQuery;
import io.github.springsongs.service.ISpringArticleCommentService;
import io.github.springsongs.util.R;

@Service
@Transactional
public class SpringArticleCommentServiceImpl implements ISpringArticleCommentService {

	@Autowired
	private SpringArticleCommentDao springCommentDao;

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
		springCommentDao.deleteById(id);

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
		springCommentDao.save(springArticleComment);

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
		SpringArticleComment springArticleComment = springCommentDao.getOne(id);
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
	public void updateByPrimaryKey(SpringArticleCommentDTO record) {
		SpringArticleComment springArticleComment = new SpringArticleComment();
		BeanUtils.copyProperties(record, springArticleComment);
		springCommentDao.save(springArticleComment);
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
		springCommentDao.setDelete(ids);
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
	public R batchSaveExcel(List<String[]> list) {
		return new R();
	}

	@Override
	public void delete(List<String> ids) {
		springCommentDao.delete(ids);

	}
}
