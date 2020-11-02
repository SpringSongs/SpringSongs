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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.spring.dao.SpringLogOnDao;
import cn.spring.dao.SpringUserDao;
import cn.spring.dao.SpringUserRoleDao;
import cn.spring.domain.SpringUser;
import cn.spring.domain.SpringUserRole;
import cn.spring.domain.SpringUserSecurity;
import cn.spring.service.ISpringUserService;
import cn.spring.util.IpKit;
import cn.spring.util.R;

@Service
@Transactional
public class SpringUserServiceImpl implements ISpringUserService {

	@Autowired
	private SpringUserDao springUserDao;

	@Autowired
	private SpringLogOnDao springLogOnDao;
	
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
		springUserDao.deleteById(id);

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
	public void insert(SpringUser record) {
		springUserDao.save(record);

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
	public SpringUser selectByPrimaryKey(String id) {
		return springUserDao.getOne(id);
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
	public void updateByPrimaryKey(SpringUser record) {
		springUserDao.save(record);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseBuserEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringUser> getAllRecordByPage(SpringUser record,Pageable pageable) {
		Specification<SpringUser> specification = new Specification<SpringUser>() {

			@Override
			public Predicate toPredicate(Root<SpringUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getUserName())) {
					Predicate userName = cb.equal(root.get("userName").as(String.class), record.getUserName());
					predicates.add(userName);
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
		return springUserDao.findAll(specification, pageable);
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
		springUserDao.setDelete(ids);
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
	public List<SpringUser> listUserByIds(List<String> ids) {
		return springUserDao.listUserByIds(ids);
	}

	@Override
	public SpringUser getByUserName(String username) {
		return springUserDao.getByUserName(username);
	}

	@Override
	public R setPwd(SpringUserSecurity viewEntity) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		R r = new R();
		SpringUser entity = springUserDao.getOne(viewEntity.getUserId());
		if (null == entity) {
			r.put("code", 500);
			r.put("msg", "找不到用户!");
		} else {
			SpringUserSecurity baseUserLogOnEntity = springLogOnDao.findByUserId(viewEntity.getUserId());
			if (null != baseUserLogOnEntity) {
				viewEntity.setId(baseUserLogOnEntity.getId());
				viewEntity.setCreatedBy(baseUserLogOnEntity.getCreatedBy());
				viewEntity.setCreatedUserId(baseUserLogOnEntity.getCreatedUserId());
				viewEntity.setCreatedIp(baseUserLogOnEntity.getCreatedIp());
				viewEntity.setCreatedOn(baseUserLogOnEntity.getCreatedOn());
			}
			viewEntity.setPwd(passwordEncoder.encode(viewEntity.getPwd().trim()));
			springLogOnDao.saveAndFlush(viewEntity);
			r.put("code", 200);
			r.put("msg", "设置密码成功！");
		}
		return r;
	}

	@Override
	public void delete(List<String> ids) {
		springUserDao.delete(ids);

	}

	@Override
	public Page<SpringUser> ListUsersByRoleId(String roleId,Pageable pageable) {
		//Pageable pageable = PageRequest.of(currPage - 1, size);
		return springUserDao.ListUsersByRoleId(roleId, pageable);
	}

	@Override
	public void delete(Map map) {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String roleId = entry.getKey();
			String userId = entry.getValue();
			springUserRoleDao.delete(userId, roleId);
		}
	}

	@Override
	public void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList,String userId) {
		springUserRoleDao.deleteByUserId(userId);
		springUserRoleDao.saveAll(baseUserRoleEntityList);
	}
}