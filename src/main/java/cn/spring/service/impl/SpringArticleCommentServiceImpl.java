package cn.spring.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.spring.dao.SpringCommentDao;
import cn.spring.domain.SpringComment;
import cn.spring.service.ISpringArticleCommentService;
import cn.spring.util.R;
@Service
@Transactional
public class SpringArticleCommentServiceImpl implements ISpringArticleCommentService {

	@Autowired
	private SpringCommentDao baseCommentDao;

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
		baseCommentDao.deleteById(id);

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
	public void insert(SpringComment record) {
		baseCommentDao.save(record);

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
	public SpringComment selectByPrimaryKey(String id) {
		return baseCommentDao.getOne(id);
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
	public void updateByPrimaryKey(SpringComment record) {
		baseCommentDao.save(record);
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
	public Page<SpringComment> getAllRecordByPage(SpringComment record,Pageable pageable) {
		Specification<SpringComment> specification = new Specification<SpringComment>() {

			@Override
			public Predicate toPredicate(Root<SpringComment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate receiverId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
					predicates.add(receiverId);
				}
				if (!StringUtils.isEmpty(record.getContent())) {
					Predicate title = cb.like(root.get("content").as(String.class),
							record.getContent() + "%");
					predicates.add(title);
				}
				if (record.getAuditFlag()) {
					Predicate receiverId = cb.equal(root.get("auditFlag").as(Boolean.class),
							record.getAuditFlag());
					predicates.add(receiverId);
				}
				Predicate deletionStateCode = cb.equal(root.get("deletedFlag").as(Boolean.class), false);
				predicates.add(deletionStateCode);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		//Pageable pageable = PageRequest.of(currPage - 1, size);
		return baseCommentDao.findAll(specification, pageable);
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
		baseCommentDao.setDelete(ids);
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
		baseCommentDao.delete(ids);
		
	}
}
