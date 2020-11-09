package io.github.springsongs.modules.activiti.service.impl;

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
import io.github.springsongs.modules.activiti.domain.SpringActCategory;
import io.github.springsongs.modules.activiti.dto.SpringActCategoryDTO;
import io.github.springsongs.modules.activiti.query.SpringActCategoryQuery;
import io.github.springsongs.modules.activiti.repo.SpringActCategoryRepo;
import io.github.springsongs.modules.activiti.service.ISpringActCategoryService;

@Service
public class SpringActCategoryServiceImpl implements ISpringActCategoryService {

	static Logger logger = LoggerFactory.getLogger(SpringActCategoryServiceImpl.class);

	@Autowired
	private SpringActCategoryRepo springActCategoryRepo;

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
			springActCategoryRepo.deleteById(id);
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
	public void insert(SpringActCategoryDTO springActCategoryDTO) {
		SpringActCategory springActCategory = springActCategoryRepo.getByCode(springActCategoryDTO.getCategoryCode());
		if (null != springActCategory) {
			throw new SpringSongsException(ResultCode.DATA_EXIST);
		}
		SpringActCategory addSpringActCategory = new SpringActCategory();
		BeanUtils.copyProperties(springActCategoryDTO, addSpringActCategory);
		try {
			springActCategoryRepo.save(addSpringActCategory);
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
	public SpringActCategoryDTO selectByPrimaryKey(String id) {
		SpringActCategory springActCategory = null;
		try {
			springActCategory = springActCategoryRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringActCategoryDTO springActCategoryDTO = new SpringActCategoryDTO();
		BeanUtils.copyProperties(springActCategory, springActCategoryDTO);
		return springActCategoryDTO;
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
	public void updateByPrimaryKey(SpringActCategoryDTO springActCategoryDTO) {
		SpringActCategory entity = springActCategoryRepo.getOne(springActCategoryDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setCategoryTitle(springActCategoryDTO.getCategoryTitle());
			try {
				springActCategoryRepo.save(entity);
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
	 * @return Page<BaseSpringActCategoryEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringActCategoryDTO> getAllRecordByPage(SpringActCategoryQuery record, Pageable pageable) {
		Specification<SpringActCategory> specification = new Specification<SpringActCategory>() {

			@Override
			public Predicate toPredicate(Root<SpringActCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getCategoryCode())) {
					Predicate categoryCode = cb.like(root.get("categoryCode").as(String.class),
							"%"+record.getCategoryCode());
					predicates.add(categoryCode);
				}
				if (!StringUtils.isEmpty(record.getCategoryTitle())) {
					Predicate categoryTitle = cb.like(root.get("categoryTitle").as(String.class),
							"%"+record.getCategoryTitle());
					predicates.add(categoryTitle);
				}
				Predicate deletionStateCode = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletionStateCode);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = new PageRequest(currPage - 1, size);
		// return springActCategoryRepo.findAll(specification, pageable);
		Page<SpringActCategory> springActCategorys = springActCategoryRepo.findAll(specification, pageable);
		List<SpringActCategoryDTO> springJobGroupDTOs = new ArrayList<>();
		springActCategorys.stream().forEach(springActCategory -> {
			SpringActCategoryDTO springActCategoryDTO = new SpringActCategoryDTO();
			BeanUtils.copyProperties(springActCategory, springActCategoryDTO);
			springJobGroupDTOs.add(springActCategoryDTO);
		});
		Page<SpringActCategoryDTO> pages = new PageImpl(springJobGroupDTOs, pageable,
				springActCategorys.getTotalElements());
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
		try {
			springActCategoryRepo.setDelete(ids);
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
	public List<SpringActCategoryDTO> listAll() {
		List<SpringActCategory> springActCategorys = springActCategoryRepo.listAll();
		List<SpringActCategoryDTO> springActCategoryDTOs = new ArrayList<>();
		springActCategorys.stream().forEach(springActCategory -> {
			SpringActCategoryDTO springActCategoryDTO = new SpringActCategoryDTO();
			BeanUtils.copyProperties(springActCategory, springActCategoryDTO);
			springActCategoryDTOs.add(springActCategoryDTO);
		});
		return springActCategoryDTOs;
	}

}
