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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringUser;
import io.github.springsongs.modules.sys.domain.SpringUserRole;
import io.github.springsongs.modules.sys.domain.SpringUserSecurity;
import io.github.springsongs.modules.sys.dto.SpringUserDTO;
import io.github.springsongs.modules.sys.dto.query.SpringUserQuery;
import io.github.springsongs.modules.sys.repo.SpringLogOnRepo;
import io.github.springsongs.modules.sys.repo.SpringUserRepo;
import io.github.springsongs.modules.sys.repo.SpringUserRoleRepo;
import io.github.springsongs.modules.sys.service.ISpringUserService;
import io.github.springsongs.util.Constant;

@Service
public class SpringUserServiceImpl implements ISpringUserService {

	static Logger logger = LoggerFactory.getLogger(SpringRoleServiceImpl.class);

	@Autowired
	private SpringUserRepo springUserRepo;

	@Autowired
	private SpringLogOnRepo springLogOnRepo;

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
			springUserRepo.deleteById(id);
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
	public void insert(SpringUserDTO record) {
		SpringUser springUserDo = springUserRepo.getByUserName(record.getUserName());
		if (null!=springUserDo) {
			throw new SpringSongsException(ResultCode.ACCOUNT_HAS_REGISTER);
		}
		SpringUser springUser = new SpringUser();
		BeanUtils.copyProperties(record, springUser);
		try {
			springUserRepo.save(springUser);
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
	public SpringUserDTO selectByPrimaryKey(String id) {
		SpringUser springUser = null;
		try {
			springUser = springUserRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
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
	public void updateByPrimaryKey(SpringUserDTO springUserDTO) {
		SpringUser entity = springUserRepo.getOne(springUserDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else if (!entity.getEnableEdit()) {
			throw new SpringSongsException(ResultCode.INFO_CAN_NOT_EDIT);
		} else {
			entity.setTrueName(springUserDTO.getTrueName());
			entity.setEmail(springUserDTO.getEmail());
			entity.setMobile(springUserDTO.getMobile());
			entity.setOrganizationId(springUserDTO.getOrganizationId());
			entity.setOrganizationName(springUserDTO.getOrganizationName());
			entity.setEnableEdit(springUserDTO.getEnableEdit());
			entity.setEnableDelete(springUserDTO.getEnableDelete());
			try {
				springUserRepo.save(entity);
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
	 * @return Page<BaseBuserEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringUserDTO> getAllRecordByPage(SpringUserQuery springUserQuery, Pageable pageable) {
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
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
		// return springUserRepo.findAll(specification, pageable);
		Page<SpringUser> springUsers = springUserRepo.findAll(specification, pageable);
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
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		List<SpringUser> entityList = springUserRepo.findAllById(ids);
		for (SpringUser entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springUserRepo.setDelete(ids);
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
	public List<SpringUserDTO> listUserByIds(List<String> ids) {
		List<SpringUser> springUsers = springUserRepo.listUserByIds(ids);
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
		SpringUser springUser = springUserRepo.getByUserName(username);
		if (null!=springUser) {
			throw new SpringSongsException(ResultCode.ACCOUNT_HAS_REGISTER);
		}
		SpringUserDTO springUserDTO = new SpringUserDTO();
		BeanUtils.copyProperties(springUser, springUserDTO);
		return springUserDTO;
	}

	@Override
	public void setPwd(SpringUserSecurity viewEntity) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		SpringUser entity = springUserRepo.getOne(viewEntity.getUserId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			SpringUserSecurity baseUserLogOnEntity = springLogOnRepo.findByUserId(viewEntity.getUserId());
			if (null != baseUserLogOnEntity) {
				viewEntity.setId(baseUserLogOnEntity.getId());
				viewEntity.setCreatedBy(baseUserLogOnEntity.getCreatedBy());
				viewEntity.setCreatedUserId(baseUserLogOnEntity.getCreatedUserId());
				viewEntity.setCreatedIp(baseUserLogOnEntity.getCreatedIp());
				viewEntity.setCreatedOn(baseUserLogOnEntity.getCreatedOn());
			}
			viewEntity.setPwd(passwordEncoder.encode(viewEntity.getPwd().trim()));
			try {
				springLogOnRepo.save(viewEntity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	@Override
	public void delete(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		List<SpringUser> entityList = springUserRepo.findAllById(ids);
		for (SpringUser entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springUserRepo.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public Page<SpringUserDTO> ListUsersByRoleId(String roleId, Pageable pageable) {
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		// return springUserRepo.ListUsersByRoleId(roleId, pageable);
		Page<SpringUser> springUsers = springUserRepo.ListUsersByRoleId(roleId, pageable);
		List<SpringUserDTO> springUserDTOs = new ArrayList<>();
		springUsers.stream().forEach(springUser -> {
			SpringUserDTO springUserDTO = new SpringUserDTO();
			BeanUtils.copyProperties(springUser, springUserDTO);
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
			springUserRoleRepo.delete(userId, roleId);
		}
	}

	@Override
	public void saveUserToRole(List<SpringUserRole> baseUserRoleEntityList, String userId) {
		springUserRoleRepo.deleteByUserId(userId);
		springUserRoleRepo.saveAll(baseUserRoleEntityList);
	}
}