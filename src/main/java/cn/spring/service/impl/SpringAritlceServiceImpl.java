package cn.spring.service.impl;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.spring.dao.SpringAritlceDao;
import cn.spring.domain.SpringAritlce;
import cn.spring.domain.query.SpringAritlceQuery;
import cn.spring.service.ISpringAritlceService;
import cn.spring.util.R;

@Service
@Transactional
public class SpringAritlceServiceImpl implements ISpringAritlceService {

	@Autowired
	private SpringAritlceDao springAritlceDao;

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
		springAritlceDao.deleteById(id);

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
	public void insert(SpringAritlce record) {
		springAritlceDao.save(record);

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
	public SpringAritlce selectByPrimaryKey(String id) {
		return springAritlceDao.getOne(id);
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
	public void updateByPrimaryKey(SpringAritlce record) {
		springAritlceDao.save(record);
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
	public Page<SpringAritlce> getAllRecordByPage(SpringAritlceQuery springAritlceQuery, Pageable pageable) {
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
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		return springAritlceDao.findAll(specification, pageable);
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
		springAritlceDao.setDelete(ids);
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
		springAritlceDao.delete(ids);

	}
}
