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
	private SpringUserDao baseBuserDao;

	@Autowired
	private SpringLogOnDao baseBuserLogOnDao;
	
	@Autowired
	private SpringUserRoleDao baseBuserRoleDao;

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
		baseBuserDao.deleteById(id);

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
		baseBuserDao.save(record);

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
		return baseBuserDao.getOne(id);
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
		baseBuserDao.save(record);
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
					Predicate receiverId = cb.equal(root.get("username").as(String.class), record.getUserName());
					predicates.add(receiverId);
				}
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate receiverId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
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
		return baseBuserDao.findAll(specification, pageable);
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
		baseBuserDao.setDelete(ids);
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
		return baseBuserDao.listUserByIds(ids);
	}

	@Override
	public SpringUser getByUserName(String username) {
		return baseBuserDao.getByUserName(username);
	}

	@Override
	public R setPwd(SpringUserSecurity viewEntity) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		R r = new R();
		SpringUser entity = baseBuserDao.getOne(viewEntity.getUserId());
		if (null == entity) {
			r.put("code", 500);
			r.put("msg", "找不到用户!");
		} else {
			SpringUserSecurity baseUserLogOnEntity = baseBuserLogOnDao.findByUserId(viewEntity.getUserId());
			if (null != baseUserLogOnEntity) {
				viewEntity.setId(baseUserLogOnEntity.getId());
				viewEntity.setCreatedBy(baseUserLogOnEntity.getCreatedBy());
				viewEntity.setCreatedUserId(baseUserLogOnEntity.getCreatedUserId());
				viewEntity.setCreatedIp(baseUserLogOnEntity.getCreatedIp());
				viewEntity.setCreatedOn(baseUserLogOnEntity.getCreatedOn());
			}
			viewEntity.setPwd(passwordEncoder.encode(viewEntity.getPwd().trim()));
			baseBuserLogOnDao.saveAndFlush(viewEntity);
			r.put("code", 200);
			r.put("msg", "设置密码成功！");
		}
		return r;
	}

	@Override
	public void delete(List<String> ids) {
		baseBuserDao.delete(ids);

	}

	@Override
	public Page<SpringUser> ListUsersByRoleId(String roleId,Pageable pageable) {
		//Pageable pageable = PageRequest.of(currPage - 1, size);
		return baseBuserDao.ListUsersByRoleId(roleId, pageable);
	}

	@Override
	public void delete(Map map) {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String roleId = entry.getKey();
			String userId = entry.getValue();
			baseBuserRoleDao.delete(userId, roleId);
		}
	}

	@Override
	public void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList,String userId) {
		baseBuserRoleDao.deleteByUserId(userId);
		baseBuserRoleDao.saveAll(baseUserRoleEntityList);
	}
}