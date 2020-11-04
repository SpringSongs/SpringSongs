package io.github.springsongs.service.impl;

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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.github.springsongs.dao.SpringLogOnDao;
import io.github.springsongs.dao.SpringUserDao;
import io.github.springsongs.dao.SpringUserRoleDao;
import io.github.springsongs.domain.SpringSystem;
import io.github.springsongs.domain.SpringUser;
import io.github.springsongs.domain.SpringUserDTO;
import io.github.springsongs.domain.SpringUserRole;
import io.github.springsongs.domain.SpringUserSecurity;
import io.github.springsongs.domain.dto.SpringSystemDTO;
import io.github.springsongs.domain.query.SpringUserQuery;
import io.github.springsongs.service.ISpringUserService;
import io.github.springsongs.util.R;

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
	public void insert(SpringUserDTO record) {
		SpringUser springUser = new SpringUser();
		BeanUtils.copyProperties(record, springUser);
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
	public SpringUserDTO selectByPrimaryKey(String id) {
		SpringUser springUser = springUserDao.getOne(id);
		SpringUserDTO springUserDTO = new SpringUserDTO();
		BeanUtils.copyProperties(springUser, springUserDTO);
		return springUserDTO;
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
	public void updateByPrimaryKey(SpringUserDTO record) {
		SpringUser springUser = new SpringUser();
		BeanUtils.copyProperties(record, springUser);
		springUserDao.save(springUser);
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
	public Page<SpringUserDTO> getAllRecordByPage(SpringUserQuery springUserQuery, Pageable pageable) {
		Specification<SpringUser> specification = new Specification<SpringUser>() {

			@Override
			public Predicate toPredicate(Root<SpringUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springUserQuery.getUserName())) {
					Predicate userName = cb.equal(root.get("userName").as(String.class), springUserQuery.getUserName());
					predicates.add(userName);
				}
				if (!StringUtils.isEmpty(springUserQuery.getTrueName())) {
					Predicate trueName = cb.equal(root.get("trueName").as(String.class), springUserQuery.getTrueName());
					predicates.add(trueName);
				}
				if (!StringUtils.isEmpty(springUserQuery.getEmail())) {
					Predicate email = cb.equal(root.get("email").as(String.class), springUserQuery.getEmail());
					predicates.add(email);
				}
				if (!StringUtils.isEmpty(springUserQuery.getMobile())) {
					Predicate mobile = cb.equal(root.get("mobile").as(String.class), springUserQuery.getMobile());
					predicates.add(mobile);
				}
				if (!StringUtils.isEmpty(springUserQuery.getOrganizationId())) {
					Predicate organizationId = cb.equal(root.get("organizationId").as(String.class),
							springUserQuery.getOrganizationId());
					predicates.add(organizationId);
				}
				if (!StringUtils.isEmpty(springUserQuery.getTitleId())) {
					Predicate titleId = cb.equal(root.get("titleId").as(String.class), springUserQuery.getTitleId());
					predicates.add(titleId);
				}
				if (!StringUtils.isEmpty(springUserQuery.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							springUserQuery.getCreatedUserId());
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
		// return springUserDao.findAll(specification, pageable);
		Page<SpringUser> springUsers = springUserDao.findAll(specification, pageable);
		List<SpringUserDTO> springUserDTOs = new ArrayList<>();
		springUsers.stream().forEach(springUser -> {
			SpringUserDTO springUserDTO = new SpringUserDTO();
			BeanUtils.copyProperties(springUser, springUserDTO);
			springUserDTOs.add(springUserDTO);
		});
		Page<SpringUserDTO> pages = new PageImpl(springUserDTOs, pageable, springUsers.getTotalElements());
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
	public List<SpringUserDTO> listUserByIds(List<String> ids) {
		List<SpringUser> springUsers = springUserDao.listUserByIds(ids);
		List<SpringUserDTO> springUserDTOs = new ArrayList<>();
		springUsers.stream().forEach(springSystem -> {
			SpringUserDTO springUserDTO = new SpringUserDTO();
			BeanUtils.copyProperties(springSystem, springUserDTO);
			springUserDTOs.add(springUserDTO);
		});
		return springUserDTOs;
	}

	@Override
	public SpringUserDTO getByUserName(String username) {
		SpringUser springUser = springUserDao.getByUserName(username);
		SpringUserDTO springUserDTO = new SpringUserDTO();
		BeanUtils.copyProperties(springUser, springUserDTO);
		return springUserDTO;
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
	public Page<SpringUserDTO> ListUsersByRoleId(String roleId, Pageable pageable) {
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		// return springUserDao.ListUsersByRoleId(roleId, pageable);
		Page<SpringUser> springUsers = springUserDao.ListUsersByRoleId(roleId, pageable);
		List<SpringUserDTO> springUserDTOs = new ArrayList<>();
		springUsers.stream().forEach(springUser -> {
			SpringUserDTO springUserDTO = new SpringUserDTO();
			BeanUtils.copyProperties(springUsers, springUserDTO);
			springUserDTOs.add(springUserDTO);
		});
		Page<SpringUserDTO> pages = new PageImpl(springUserDTOs, pageable, springUsers.getTotalElements());
		return pages;
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
	public void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String userId) {
		springUserRoleDao.deleteByUserId(userId);
		springUserRoleDao.saveAll(baseUserRoleEntityList);
	}
}