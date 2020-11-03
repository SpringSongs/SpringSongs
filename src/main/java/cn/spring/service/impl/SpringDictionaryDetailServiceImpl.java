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

import cn.spring.dao.SpringDictionaryDetailDao;
import cn.spring.domain.SpringDictionaryDetail;
import cn.spring.domain.query.SpringDictionaryDetailQuery;
import cn.spring.service.ISpringDictionaryDetailService;
import cn.spring.util.R;

@Service
@Transactional
public class SpringDictionaryDetailServiceImpl implements ISpringDictionaryDetailService {

	@Autowired
	private SpringDictionaryDetailDao springDictionaryDetailDao;

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
		springDictionaryDetailDao.deleteById(id);

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
	public void insert(SpringDictionaryDetail record) {
		springDictionaryDetailDao.save(record);

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
	public SpringDictionaryDetail selectByPrimaryKey(String id) {
		return springDictionaryDetailDao.getOne(id);
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
	public void updateByPrimaryKey(SpringDictionaryDetail record) {
		springDictionaryDetailDao.save(record);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseDictionaryDetailEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringDictionaryDetail> getAllRecordByPage(SpringDictionaryDetailQuery springDictionaryDetailQuery, Pageable pageable) {
		Specification<SpringDictionaryDetail> specification = new Specification<SpringDictionaryDetail>() {

			@Override
			public Predicate toPredicate(Root<SpringDictionaryDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				
				if (!StringUtils.isEmpty(springDictionaryDetailQuery.getDictionaryCode())) {
					Predicate dictionaryCode = cb.equal(root.get("dictionaryCode").as(String.class),
							springDictionaryDetailQuery.getDictionaryCode());
					predicates.add(dictionaryCode);
				}
				if (!StringUtils.isEmpty(springDictionaryDetailQuery.getDetailCode())) {
					Predicate detailCode = cb.like(root.get("detailCode").as(String.class),
							"%"+springDictionaryDetailQuery.getDetailCode());
					predicates.add(detailCode);
				}
				if (!StringUtils.isEmpty(springDictionaryDetailQuery.getDetailName())) {
					Predicate detailName = cb.like(root.get("detailName").as(String.class),
							"%"+springDictionaryDetailQuery.getDetailName());
					predicates.add(detailName);
				}
				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		//Pageable pageable = PageRequest.of(currPage - 1, size);
		return springDictionaryDetailDao.findAll(specification, pageable);
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
		springDictionaryDetailDao.setDelete(ids);
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
		springDictionaryDetailDao.delete(ids);
		
	}

	@Override
	public void setDeleteByCode(String code) {
		springDictionaryDetailDao.setDeleteByCode(code);
	}
}
