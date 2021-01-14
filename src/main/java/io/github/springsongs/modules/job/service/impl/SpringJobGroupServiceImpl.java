package io.github.springsongs.modules.job.service.impl;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.job.domain.SpringJobGroup;
import io.github.springsongs.modules.job.dto.SpringJobGroupDTO;
import io.github.springsongs.modules.job.query.SpringJobGroupQuery;
import io.github.springsongs.modules.job.repo.SpringJobGroupRepo;
import io.github.springsongs.modules.job.service.ISpringJobGroupService;
import io.github.springsongs.modules.sys.domain.SpringSystem;
import io.github.springsongs.modules.sys.dto.SpringSystemDTO;
import io.github.springsongs.util.Constant;

@Service
public class SpringJobGroupServiceImpl implements ISpringJobGroupService {

	static Logger logger = LoggerFactory.getLogger(SpringJobGroupServiceImpl.class);

	@Autowired
	private SpringJobGroupRepo springJobGroupRepo;

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
			springJobGroupRepo.deleteById(id);
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
	public void insert(SpringJobGroupDTO springJobGroupDTO) {
		SpringJobGroup springJobGroup =springJobGroupRepo.getByCode(springJobGroupDTO.getCode());
		if (null!=springJobGroup) {
			throw new SpringSongsException(ResultCode.DATA_EXIST);
		}
		SpringJobGroup addSpringJobGroup=new SpringJobGroup();
		BeanUtils.copyProperties(springJobGroupDTO, addSpringJobGroup);
		try {
			springJobGroupRepo.save(addSpringJobGroup);
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
	public SpringJobGroupDTO selectByPrimaryKey(String id) {
		SpringJobGroup springJobGroup = null;
		try {
			springJobGroup = springJobGroupRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringJobGroupDTO springJobGroupDTO = new SpringJobGroupDTO();
		BeanUtils.copyProperties(springJobGroup, springJobGroupDTO);
		return springJobGroupDTO;
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
	public void updateByPrimaryKey(SpringJobGroupDTO springJobGroupDTO) {
		SpringJobGroup entity = springJobGroupRepo.getOne(springJobGroupDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setTitle(springJobGroupDTO.getTitle());
			try {
				springJobGroupRepo.save(entity);
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
	 * @return Page<BaseSpringJobGroupEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringJobGroupDTO> getAllRecordByPage(SpringJobGroupQuery springJobGroupQuery, Pageable pageable) {
		if (pageable.getPageSize()<=0||pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		int page=pageable.getPageNumber()<=0?0:pageable.getPageNumber()-1;
		pageable = PageRequest.of(page, pageable.getPageSize());
		Specification<SpringJobGroup> specification = new Specification<SpringJobGroup>() {

			@Override
			public Predicate toPredicate(Root<SpringJobGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springJobGroupQuery.getCode())) {
					Predicate summary = cb.like(root.get("code").as(String.class),
							"%" + springJobGroupQuery.getCode());
					predicates.add(summary);
				}
				if (!StringUtils.isEmpty(springJobGroupQuery.getTitle())) {
					Predicate summary = cb.like(root.get("title").as(String.class),
							"%" + springJobGroupQuery.getTitle());
					predicates.add(summary);
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
		// return springJobGroupRepo.findAll(specification, pageable);
		Page<SpringJobGroup> springJobGroups = springJobGroupRepo.findAll(specification, pageable);
		List<SpringJobGroupDTO> springJobGroupDTOs = new ArrayList<>();
		springJobGroups.stream().forEach(springJobGroup -> {
			SpringJobGroupDTO springJobGroupDTO = new SpringJobGroupDTO();
			BeanUtils.copyProperties(springJobGroup, springJobGroupDTO);
			springJobGroupDTOs.add(springJobGroupDTO);
		});
		Page<SpringJobGroupDTO> pages = new PageImpl(springJobGroupDTOs, pageable, springJobGroups.getTotalElements());
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
			springJobGroupRepo.setDelete(ids);
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
	public List<SpringJobGroupDTO> listAll() {
		List<SpringJobGroup> springJobGroups = springJobGroupRepo.listAll();
		List<SpringJobGroupDTO> springJobGroupDTOs = new ArrayList<>();
		springJobGroups.stream().forEach(springSystem -> {
			SpringJobGroupDTO springJobGroupDTO = new SpringJobGroupDTO();
			BeanUtils.copyProperties(springSystem, springJobGroupDTO);
			springJobGroupDTOs.add(springJobGroupDTO);
		});
		return springJobGroupDTOs;
	}

}
