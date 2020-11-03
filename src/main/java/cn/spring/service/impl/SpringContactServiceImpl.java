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
import cn.spring.domain.query.SpringContactQuery;
import cn.spring.service.ISpringContactService;
import cn.spring.util.R;

@Service
@Transactional
public class SpringContactServiceImpl implements ISpringContactService {

	@Autowired
	private SpringContactDao springContactDao;

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
		springContactDao.deleteById(id);

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
		springContactDao.save(record);

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
		return springContactDao.getOne(id);
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
		springContactDao.save(record);
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
	public Page<SpringContact> getAllRecordByPage(SpringContactQuery springContactQuery, Pageable pageable) {
		Specification<SpringContact> specification = new Specification<SpringContact>() {

			@Override
			public Predicate toPredicate(Root<SpringContact> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springContactQuery.getCompany())) {
					Predicate company = cb.like(root.get("company").as(String.class),
							springContactQuery.getCompany() + "%");
					predicates.add(company);
				}
				if (!StringUtils.isEmpty(springContactQuery.getUsername())) {
					Predicate username = cb.like(root.get("username").as(String.class),
							springContactQuery.getUsername() + "%");
					predicates.add(username);
				}
				if (!StringUtils.isEmpty(springContactQuery.getMobile())) {
					Predicate mobile = cb.like(root.get("mobile").as(String.class),
							springContactQuery.getMobile() + "%");
					predicates.add(mobile);
				}
				if (!StringUtils.isEmpty(springContactQuery.getQq())) {
					Predicate qq = cb.like(root.get("qq").as(String.class),
							springContactQuery.getQq() + "%");
					predicates.add(qq);
				}
				if (!StringUtils.isEmpty(springContactQuery.getWebchat())) {
					Predicate webchat = cb.like(root.get("webchat").as(String.class),
							springContactQuery.getWebchat() + "%");
					predicates.add(webchat);
				}
				if (!StringUtils.isEmpty(springContactQuery.getFax())) {
					Predicate fax = cb.like(root.get("fax").as(String.class),
							springContactQuery.getFax() + "%");
					predicates.add(fax);
				}
				if (!StringUtils.isEmpty(springContactQuery.getTel())) {
					Predicate tel = cb.like(root.get("tel").as(String.class),
							springContactQuery.getTel() + "%");
					predicates.add(tel);
				}
				if (!StringUtils.isEmpty(springContactQuery.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class), springContactQuery.getTitle() + "%");
					predicates.add(title);
				}
				if (!StringUtils.isEmpty(springContactQuery.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							springContactQuery.getCreatedUserId());
					predicates.add(createdUserId);
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
		return springContactDao.findAll(specification, pageable);
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
		springContactDao.setDelete(ids);
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
		springContactDao.delete(ids);

	}
}
