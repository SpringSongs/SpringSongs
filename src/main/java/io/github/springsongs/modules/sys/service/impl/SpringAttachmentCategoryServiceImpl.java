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
import io.github.springsongs.modules.sys.domain.SpringAttachmentCategory;
import io.github.springsongs.modules.sys.dto.SpringAttachmentCategoryDTO;
import io.github.springsongs.modules.sys.repo.SpringAttachmentCategoryRepo;
import io.github.springsongs.modules.sys.service.ISpringAttachmentCategoryService;
import io.github.springsongs.util.Constant;

@Service
public class SpringAttachmentCategoryServiceImpl implements ISpringAttachmentCategoryService {

	static Logger logger = LoggerFactory.getLogger(SpringAttachmentCategoryServiceImpl.class);

	@Autowired
	private SpringAttachmentCategoryRepo springAttachmentCategoryRepo;

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
			springAttachmentCategoryRepo.deleteById(id);
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
	public void insert(SpringAttachmentCategoryDTO record) {
		SpringAttachmentCategory SpringAttachmentCategory = new SpringAttachmentCategory();
		BeanUtils.copyProperties(record, SpringAttachmentCategory);
		try {
			springAttachmentCategoryRepo.save(SpringAttachmentCategory);
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
	public SpringAttachmentCategoryDTO selectByPrimaryKey(String id) {
		SpringAttachmentCategoryDTO springAttachmentCategoryDTO = new SpringAttachmentCategoryDTO();
		SpringAttachmentCategory springAttachmentCategory = springAttachmentCategoryRepo.getOne(id);
		BeanUtils.copyProperties(springAttachmentCategory, springAttachmentCategoryDTO);
		return springAttachmentCategoryDTO;
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
	public void updateByPrimaryKey(SpringAttachmentCategoryDTO springAttachmentCategoryDTO) {
		SpringAttachmentCategory entity = springAttachmentCategoryRepo.getOne(springAttachmentCategoryDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setTitle(springAttachmentCategoryDTO.getTitle());
			entity.setDescription(springAttachmentCategoryDTO.getDescription());
			entity.setDictionaryCode(springAttachmentCategoryDTO.getDictionaryCode());
			entity.setDictionaryName(springAttachmentCategoryDTO.getDictionaryName());
			try {
				springAttachmentCategoryRepo.save(entity);
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
	 * @return Page<BaseFolderEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringAttachmentCategoryDTO> getAllRecordByPage(SpringAttachmentCategory record, Pageable pageable) {
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		Specification<SpringAttachmentCategory> specification = new Specification<SpringAttachmentCategory>() {

			@Override
			public Predicate toPredicate(Root<SpringAttachmentCategory> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class), record.getTitle() + "%");
					predicates.add(title);
				}
				if (!StringUtils.isEmpty(record.getDescription())) {
					Predicate description = cb.like(root.get("description").as(String.class),
							record.getDescription() + "%");
					predicates.add(description);
				}
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
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
		// return springAttachmentCategoryRepo.findAll(specification, pageable);
		Page<SpringAttachmentCategory> springAttachmentCategorys = springAttachmentCategoryRepo.findAll(specification,
				pageable);
		List<SpringAttachmentCategoryDTO> springAttachmentCategoryDTOs = new ArrayList<>();
		springAttachmentCategorys.stream().forEach(springAttachmentCategory -> {
			SpringAttachmentCategoryDTO springAttachmentCategoryDTO = new SpringAttachmentCategoryDTO();
			BeanUtils.copyProperties(springAttachmentCategory, springAttachmentCategoryDTO);
			springAttachmentCategoryDTOs.add(springAttachmentCategoryDTO);
		});
		Page<SpringAttachmentCategoryDTO> pages = new PageImpl(springAttachmentCategoryDTOs, pageable,
				springAttachmentCategorys.getTotalElements());
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
			List<SpringAttachmentCategory> springAttachmentCategorys = springAttachmentCategoryRepo
					.listSpringAttachmentCategoryByParentId(id);
			if (!springAttachmentCategorys.isEmpty()) {
				throw new SpringSongsException(ResultCode.HASED_CHILD_IDS_CANNOT_DELETE);
			}
		}
		try {
			springAttachmentCategoryRepo.setDelete(ids);
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
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		for (String id : ids) {
			List<SpringAttachmentCategory> springAttachmentCategorys = springAttachmentCategoryRepo
					.listSpringAttachmentCategoryByParentId(id);
			if (springAttachmentCategorys.isEmpty()) {
				throw new SpringSongsException(ResultCode.HASED_CHILD_IDS_CANNOT_DELETE);
			}
		}
		try {
			springAttachmentCategoryRepo.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public List<SpringAttachmentCategoryDTO> listSpringAttachmentCategoryByParentId(String parentId) {
		List<SpringAttachmentCategory> springAttachmentCategorys = springAttachmentCategoryRepo
				.listSpringAttachmentCategoryByParentId(parentId);
		List<SpringAttachmentCategoryDTO> springAttachmentCategoryDTOs = new ArrayList<>();
		springAttachmentCategoryDTOs.stream().forEach(springSystem -> {
			SpringAttachmentCategoryDTO springAttachmentCategoryDTO = new SpringAttachmentCategoryDTO();
			BeanUtils.copyProperties(springSystem, springAttachmentCategoryDTO);
			springAttachmentCategoryDTOs.add(springAttachmentCategoryDTO);
		});
		return springAttachmentCategoryDTOs;
	}

}
