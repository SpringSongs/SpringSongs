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

import cn.spring.dao.SpringContactDao;
import cn.spring.domain.SpringContact;
import cn.spring.service.ISpringContactService;
import cn.spring.util.R;
@Service
@Transactional
public class SpringContactServiceImpl implements ISpringContactService {

	@Autowired
	private SpringContactDao baseBusinessCardDao;

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
		baseBusinessCardDao.deleteById(id);

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
	public void insert(SpringContact record) {
		baseBusinessCardDao.save(record);

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
	public SpringContact selectByPrimaryKey(String id) {
		return baseBusinessCardDao.getOne(id);
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
	public void updateByPrimaryKey(SpringContact record) {
		baseBusinessCardDao.save(record);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseBusinessCardEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringContact> getAllRecordByPage(SpringContact record,Pageable pageable) {
		Specification<SpringContact> specification = new Specification<SpringContact>() {

			@Override
			public Predicate toPredicate(Root<SpringContact> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getCompany())) {
					Predicate company = cb.like(root.get("company").as(String.class),
							record.getCompany() + "%");
					predicates.add(company);
				}
				if (!StringUtils.isEmpty(record.getUsername())) {
					Predicate username = cb.like(root.get("username").as(String.class),
							record.getUsername() + "%");
					predicates.add(username);
				}
				if (!StringUtils.isEmpty(record.getMobile())) {
					Predicate mobile = cb.like(root.get("mobile").as(String.class),
							record.getMobile() + "%");
					predicates.add(mobile);
				}
				if (!StringUtils.isEmpty(record.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class),
							record.getTitle() + "%");
					predicates.add(title);
				}
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
					predicates.add(createdUserId);
				}
				Predicate deletedFlag = cb.equal(root.get("deletedFlag").as(Boolean.class), false);
				predicates.add(deletedFlag);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		//Pageable pageable = PageRequest.of(currPage - 1, size);
		return baseBusinessCardDao.findAll(specification, pageable);
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
		baseBusinessCardDao.setDelete(ids);
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
		baseBusinessCardDao.delete(ids);
		
	}
}
