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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.github.springsongs.domain.SpringRole;
import io.github.springsongs.domain.SpringUserRole;
import io.github.springsongs.domain.dto.SpringRoleDTO;
import io.github.springsongs.domain.query.SpringRoleQueryBO;
import io.github.springsongs.repo.SpringRoleRepo;
import io.github.springsongs.repo.SpringUserRoleRepo;
import io.github.springsongs.service.ISpringRoleService;
import io.github.springsongs.util.R;

@Service
public class SpringRoleServiceImpl implements ISpringRoleService {
	@Autowired
	private SpringRoleRepo springRoleDao;

	@Autowired
	private SpringUserRoleRepo springUserRoleDao;

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
	public void insert(SpringRoleDTO record) {
		SpringRole springRole = new SpringRole();
		BeanUtils.copyProperties(record, springRole);
		springRoleDao.save(springRole);
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
	public SpringRoleDTO selectByPrimaryKey(String id) {
		SpringRole springRole = springRoleDao.getOne(id);
		SpringRoleDTO springRoleDTO = new SpringRoleDTO();
		BeanUtils.copyProperties(springRole, springRoleDTO);
		return springRoleDTO;
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
	public void updateByPrimaryKey(SpringRoleDTO record) {
		SpringRole springRole = new SpringRole();
		BeanUtils.copyProperties(record, springRole);
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
	public Page<SpringRoleDTO> getAllRecordByPage(SpringRoleQueryBO springRoleQuery, Pageable pageable) {
		Specification<SpringRole> specification = new Specification<SpringRole>() {
			@Override
			public Predicate toPredicate(Root<SpringRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (!StringUtils.isEmpty(springRoleQuery.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class), "%" + springRoleQuery.getTitle());
					predicates.add(title);
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
		// return springRoleDao.findAll(specification, pageable);
		Page<SpringRole> springRoles = springRoleDao.findAll(specification, pageable);
		List<SpringRoleDTO> springRoleDTOs = new ArrayList<>();
		springRoles.stream().forEach(springRole -> {
			SpringRoleDTO springRoleDTO = new SpringRoleDTO();
			BeanUtils.copyProperties(springRole, springRoleDTO);
			springRoleDTOs.add(springRoleDTO);
		});
		Page<SpringRoleDTO> pages = new PageImpl(springRoleDTOs, pageable, springRoles.getTotalElements());
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
	public List<SpringRoleDTO> listByIds(List<String> ids) {
		List<SpringRole> springRoles = springRoleDao.findAllById(ids);
		List<SpringRoleDTO> springRoleDTOs = new ArrayList<>();
		springRoles.stream().forEach(springRole -> {
			SpringRoleDTO springRoleDTO = new SpringRoleDTO();
			BeanUtils.copyProperties(springRole, springRoleDTO);
			springRoleDTOs.add(springRoleDTO);
		});
		return springRoleDTOs;
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
	public void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String roleId) {
		springUserRoleDao.deleteByRoleId(roleId);
		springUserRoleDao.saveAll(baseUserRoleEntityList);
	}

	@Override
	public Page<SpringRoleDTO> ListRoleByUserId(String userId, Pageable pageable) {
		// Pageable pageable = PageRequest.of(page - 1, limit);
		Page<SpringRole> springRoles = springRoleDao.ListRoleByUserId(userId, pageable);
		List<SpringRoleDTO> springRoleDTOs = new ArrayList<>();
		springRoles.stream().forEach(springRole -> {
			SpringRoleDTO springRoleDTO = new SpringRoleDTO();
			BeanUtils.copyProperties(springRole, springRoleDTO);
			springRoleDTOs.add(springRoleDTO);
		});
		Page<SpringRoleDTO> pages = new PageImpl(springRoleDTOs, pageable, springRoles.getTotalElements());
		return pages;
	}
}
