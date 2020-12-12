package io.github.springsongs.modules.sys.service.impl;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringRole;
import io.github.springsongs.modules.sys.domain.SpringUserRole;
import io.github.springsongs.modules.sys.dto.SpringRoleDTO;
import io.github.springsongs.modules.sys.dto.query.SpringRoleQuery;
import io.github.springsongs.modules.sys.repo.SpringRoleRepo;
import io.github.springsongs.modules.sys.repo.SpringUserRoleRepo;
import io.github.springsongs.modules.sys.service.ISpringRoleService;
import io.github.springsongs.util.Constant;

@Service
public class SpringRoleServiceImpl implements ISpringRoleService {
	static Logger logger = LoggerFactory.getLogger(SpringRoleServiceImpl.class);

	@Autowired
	private SpringRoleRepo springRoleRepo;

	@Autowired
	private SpringUserRoleRepo springUserRoleRepo;

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
		try {
			springRoleRepo.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
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
		try {
			springRoleRepo.save(springRole);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
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
		SpringRole springRole = null;
		try {
			springRole = springRoleRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
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
	public void updateByPrimaryKey(SpringRoleDTO springRoleDTO) {
		SpringRole entity = springRoleRepo.getOne(springRoleDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else if (!entity.getEnableEdit()) {
			throw new SpringSongsException(ResultCode.INFO_CAN_NOT_EDIT);
		} else {
			entity.setTitle(springRoleDTO.getTitle());
			entity.setDesc(springRoleDTO.getDesc());
			entity.setEnableEdit(springRoleDTO.getEnableEdit());
			entity.setEnableDelete(springRoleDTO.getEnableDelete());
			try {
				springRoleRepo.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
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
	public Page<SpringRoleDTO> getAllRecordByPage(SpringRoleQuery springRoleQuery, Pageable pageable) {
		if (pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
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
		// return springRoleRepo.findAll(specification, pageable);
		Page<SpringRole> springRoles = springRoleRepo.findAll(specification, pageable);
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
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		List<SpringRole> entityList = springRoleRepo.findAllById(ids);
		for (SpringRole entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springRoleRepo.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
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
	public void batchSaveExcel(List<String[]> list) {

	}

	@Override
	public void delete(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		List<SpringRole> entityList = springRoleRepo.findAllById(ids);
		for (SpringRole entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springRoleRepo.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}

	}

	@Override
	public List<SpringRoleDTO> listByIds(List<String> ids) {
		List<SpringRole> springRoles = springRoleRepo.findAllById(ids);
		List<SpringRoleDTO> springRoleDTOs = new ArrayList<>();
		springRoles.stream().forEach(springRole -> {
			SpringRoleDTO springRoleDTO = new SpringRoleDTO();
			BeanUtils.copyProperties(springRole, springRoleDTO);
			springRoleDTOs.add(springRoleDTO);
		});
		return springRoleDTOs;
	}

	@Transactional
	@Override
	public void delete(Map map) {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String userId = entry.getKey();
			String roleId = entry.getValue();
			springUserRoleRepo.deleteByUserIdAndRoleId(userId, roleId);
		}
	}

	@Override
	@Transactional
	public void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String roleId) {
		springUserRoleRepo.deleteByRoleId(roleId);
		springUserRoleRepo.saveAll(baseUserRoleEntityList);
	}

	@Override
	public Page<SpringRoleDTO> ListRoleByUserId(String userId, Pageable pageable) {
		// Pageable pageable = PageRequest.of(page - 1, limit);
		Page<SpringRole> springRoles = springRoleRepo.ListRoleByUserId(userId, pageable);
		List<SpringRoleDTO> springRoleDTOs = new ArrayList<>();
		springRoles.stream().forEach(springRole -> {
			SpringRoleDTO springRoleDTO = new SpringRoleDTO();
			BeanUtils.copyProperties(springRole, springRoleDTO);
			springRoleDTOs.add(springRoleDTO);
		});
		Page<SpringRoleDTO> pages = new PageImpl(springRoleDTOs, pageable, springRoles.getTotalElements());
		return pages;
	}

	@Transactional
	@Override
	public void deleteUserFromRole(List<String> userIds, String roleId) {
		for (String userId : userIds) {
			springUserRoleRepo.deleteByUserIdAndRoleId(userId, roleId);
		}
	}
}
