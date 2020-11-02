package cn.spring.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import cn.spring.dao.SpringRoleDao;
import cn.spring.dao.SpringUserRoleDao;
import cn.spring.domain.SpringRole;
import cn.spring.domain.SpringUserRole;
import cn.spring.service.ISpringRoleService;
import cn.spring.util.R;

@Service
public class SpringRoleServiceImpl implements ISpringRoleService {
	@Autowired
	private SpringRoleDao springRoleDao;

	@Autowired
	private SpringUserRoleDao springUserRoleDao;

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
		springRoleDao.deleteById(id);

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
	public void insert(SpringRole record) {
		springRoleDao.save(record);
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
	public SpringRole selectByPrimaryKey(String id) {
		return springRoleDao.getOne(id);
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
	public void updateByPrimaryKey(SpringRole record) {
		springRoleDao.save(record);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseRoleEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringRole> getAllRecordByPage(SpringRole record, Pageable pageable) {
		Specification<SpringRole> specification = new Specification<SpringRole>() {
			@Override
			public Predicate toPredicate(Root<SpringRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (!StringUtils.isEmpty(record.getTitle())) {
					Predicate title = cb.equal(root.get("title").as(String.class), record.getTitle());
					predicates.add(title);
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
		return springRoleDao.findAll(specification, pageable);
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
		springRoleDao.setDelete(ids);
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
		springRoleDao.deleteAll();

	}

	@Override
	public List<SpringRole> listByIds(List<String> ids) {
		return springRoleDao.findAllById(ids);
	}

	@Override
	public void delete(Map map) {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String userId = entry.getKey();
			String roleId = entry.getValue();
			springUserRoleDao.delete(userId, roleId);
		}
	}

	@Override
	@Transactional
	public void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList,String roleId) {
		springUserRoleDao.deleteByRoleId(roleId);
		springUserRoleDao.saveAll(baseUserRoleEntityList);
	}

	@Override
	public Page<SpringRole> ListRoleByUserId(String userId,Pageable pageable) {
		//Pageable pageable = PageRequest.of(page - 1, limit);
		return springRoleDao.ListRoleByUserId(userId, pageable);
	}
}
