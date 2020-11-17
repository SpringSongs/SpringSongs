package io.github.springsongs.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import io.github.springsongs.modules.sys.domain.SpringOrganization;
import io.github.springsongs.modules.sys.dto.SpringOrganizationDTO;
import io.github.springsongs.modules.sys.dto.SpringOrganizationTreeDTO;
import io.github.springsongs.modules.sys.repo.SpringOrganizationRepo;
import io.github.springsongs.modules.sys.service.ISpringOrganizationService;
import io.github.springsongs.util.Constant;

@Service
public class SpringOrganizationServiceImpl implements ISpringOrganizationService {

	static Logger logger = LoggerFactory.getLogger(SpringDictionaryServiceImpl.class);

	@Autowired
	private SpringOrganizationRepo springOrganizationRepo;

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
			springOrganizationRepo.deleteById(id);
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
	public void insert(SpringOrganizationDTO record) {
		SpringOrganization SpringOrganization = new SpringOrganization();
		BeanUtils.copyProperties(record, SpringOrganization);
		try {
			springOrganizationRepo.save(SpringOrganization);
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
	public SpringOrganizationDTO selectByPrimaryKey(String id) {
		SpringOrganization SpringOrganization = null;
		try {
			SpringOrganization = springOrganizationRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
		BeanUtils.copyProperties(SpringOrganization, springOrganizationDTO);
		return springOrganizationDTO;
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
	public void updateByPrimaryKey(SpringOrganizationDTO springOrganizationDTO) {
		SpringOrganization entity = springOrganizationRepo.getOne(springOrganizationDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setId(springOrganizationDTO.getId());
			entity.setParentId(springOrganizationDTO.getParentId());
			entity.setCode(springOrganizationDTO.getCode());
			entity.setTitle(springOrganizationDTO.getTitle());
			entity.setDeletedStatus(springOrganizationDTO.getDeletedStatus());
			try {
				springOrganizationRepo.save(entity);
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
	 * @return Page<BaseSpringOrganizationEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringOrganizationDTO> getAllRecordByPage(SpringOrganization record, Pageable pageable) {
		if (pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}

		Specification<SpringOrganization> specification = new Specification<SpringOrganization>() {

			@Override
			public Predicate toPredicate(Root<SpringOrganization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate receiverId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
					predicates.add(receiverId);
				}
				if (!StringUtils.isEmpty(record.getParentId())) {
					Predicate parentId = cb.equal(root.get("parentId").as(String.class), record.getParentId());
					predicates.add(parentId);
				}
				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = new PageRequest(currPage - 1, size);
		Page<SpringOrganization> springOrganizations = springOrganizationRepo.findAll(specification, pageable);
		List<SpringOrganizationDTO> springOrganizationDTOs = new ArrayList<>();
		springOrganizations.stream().forEach(springOrganization -> {
			SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
			BeanUtils.copyProperties(springOrganization, springOrganizationDTO);
			springOrganizationDTOs.add(springOrganizationDTO);
		});
		Page<SpringOrganizationDTO> pages = new PageImpl(springOrganizationDTOs, pageable,
				springOrganizations.getTotalElements());
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
		for (String id : ids) {
			List<SpringOrganization> entitis = springOrganizationRepo.listOrganizationByParentId(id);
			if (entitis.size() > 0) {
				throw new SpringSongsException(ResultCode.HASED_CHILD_IDS_CANNOT_DELETE);
			}
		}
		try {
			springOrganizationRepo.setDelete(ids);
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

	/**
	 * 根据上级主键查询组织机构
	 * 
	 * @param parentId
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public List<SpringOrganizationDTO> listOrganizationsByParent(String parentId) {
		List<SpringOrganization> springOrganizations = springOrganizationRepo.listOrganizationByParentId(parentId);
		List<SpringOrganizationDTO> springOrganizationDTOs = new ArrayList<>();
		springOrganizations.stream().forEach(springOrganization -> {
			SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
			BeanUtils.copyProperties(springOrganization, springOrganizationDTO);
			springOrganizationDTOs.add(springOrganizationDTO);
		});
		return springOrganizationDTOs;
	}

	@Override
	public List<SpringOrganizationDTO> listAll() {
		List<SpringOrganization> springOrganizations = springOrganizationRepo.listAllRecord();
		List<SpringOrganizationDTO> springOrganizationDTOs = new ArrayList<>();
		springOrganizations.stream().forEach(springOrganization -> {
			SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
			BeanUtils.copyProperties(springOrganization, springOrganizationDTO);
			springOrganizationDTOs.add(springOrganizationDTO);
		});
		return springOrganizationDTOs;
	}

	@Override
	public List<SpringOrganizationDTO> ListAllToTree() {
		List<SpringOrganization> springOrganizations = springOrganizationRepo.listAllRecord();
		final List<SpringOrganizationDTO> springOrganizationDTOs = new ArrayList<>();
		springOrganizations.stream().forEach(springOrganization -> {
			SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
			BeanUtils.copyProperties(springOrganization, springOrganizationDTO);
			springOrganizationDTOs.add(springOrganizationDTO);
		});
		SpringOrganizationTreeDTO springOrganizationTreeDTO = new SpringOrganizationTreeDTO(springOrganizationDTOs);
		List<SpringOrganizationDTO> springOrganizationTreeDTOs = springOrganizationTreeDTO.builTree();
		return springOrganizationTreeDTOs;
	}

}
