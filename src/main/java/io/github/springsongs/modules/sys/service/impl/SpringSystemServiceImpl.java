package io.github.springsongs.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import io.github.springsongs.modules.sys.domain.SpringSystem;
import io.github.springsongs.modules.sys.dto.SpringSystemDTO;
import io.github.springsongs.modules.sys.dto.query.SpringSystemQuery;
import io.github.springsongs.modules.sys.repo.SpringSystemRepo;
import io.github.springsongs.modules.sys.service.ISpringSystemService;
import io.github.springsongs.util.Constant;

@Service
public class SpringSystemServiceImpl implements ISpringSystemService {

	static Logger logger = LoggerFactory.getLogger(SpringRoleServiceImpl.class);

	@Autowired
	private SpringSystemRepo springSystemRepo;

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
			springSystemRepo.deleteById(id);
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
	public void insert(SpringSystemDTO record) {
		SpringSystem springSystem = new SpringSystem();
		BeanUtils.copyProperties(record, springSystem);
		try {
			springSystemRepo.save(springSystem);
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
	public SpringSystemDTO selectByPrimaryKey(String id) {
		SpringSystem springSystem = null;
		try {
			springSystem = springSystemRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringSystemDTO springSystemDTO = new SpringSystemDTO();
		BeanUtils.copyProperties(springSystem, springSystemDTO);
		return springSystemDTO;
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
	public void updateByPrimaryKey(SpringSystemDTO springSystemDTO) {
		SpringSystem entity = springSystemRepo.getOne(springSystemDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setCode(springSystemDTO.getCode());
			entity.setTitle(springSystemDTO.getTitle());
			entity.setDescription(springSystemDTO.getDescription());
			entity.setEnableDelete(springSystemDTO.getEnableDelete());
			entity.setEnableEdit(springSystemDTO.getEnableEdit());
			try {
				springSystemRepo.save(entity);
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
	 * @return Page<SpringSystem>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringSystemDTO> getAllRecordByPage(SpringSystemQuery springSystemQuery, Pageable pageable) {
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		Specification<SpringSystem> specification = new Specification<SpringSystem>() {

			@Override
			public Predicate toPredicate(Root<SpringSystem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springSystemQuery.getCode())) {
					Predicate code = cb.like(root.get("code").as(String.class), "%" + springSystemQuery.getCode());
					predicates.add(code);
				}
				if (!StringUtils.isEmpty(springSystemQuery.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class), "%" + springSystemQuery.getTitle());
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
		// Pageable pageable = new PageRequest(currPage - 1, size);
		// return springSystemRepo.findAll(specification, pageable);
		Page<SpringSystem> springSystems = springSystemRepo.findAll(specification, pageable);
		List<SpringSystemDTO> springSystemDTOs = new ArrayList<>();
		springSystems.stream().forEach(springSystem -> {
			SpringSystemDTO springSystemDTO = new SpringSystemDTO();
			BeanUtils.copyProperties(springSystem, springSystemDTO);
			springSystemDTOs.add(springSystemDTO);
		});
		Page<SpringSystemDTO> pages = new PageImpl(springSystemDTOs, pageable, springSystems.getTotalElements());
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
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		List<SpringSystem> entityList = springSystemRepo.findAllById(ids);
		for (SpringSystem entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springSystemRepo.setDelete(ids);
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
	public SpringSystem findByCode(String itemCode) {
		return springSystemRepo.findByCode(itemCode);
	}

	@Override
	public List<SpringSystemDTO> findInIds(List<String> ids) {
		List<SpringSystem> springSystems = springSystemRepo.findInIds(ids);
		List<SpringSystemDTO> springSystemDTOs = new ArrayList<>();
		springSystems.stream().forEach(springSystem -> {
			SpringSystemDTO springSystemDTO = new SpringSystemDTO();
			BeanUtils.copyProperties(springSystem, springSystemDTO);
			springSystemDTOs.add(springSystemDTO);
		});
		return springSystemDTOs;
	}

	@Override
	public int batchSaveLog(List<SpringSystem> logList) {
		return 0;
	}

	@Override
	public List<SpringSystemDTO> ListAll() {
		List<SpringSystem> springSystems = springSystemRepo.listAllRecord();
		List<SpringSystemDTO> springSystemDTOs = new ArrayList<>();
		springSystems.stream().forEach(springSystem -> {
			SpringSystemDTO springSystemDTO = new SpringSystemDTO();
			BeanUtils.copyProperties(springSystem, springSystemDTO);
			springSystemDTOs.add(springSystemDTO);
		});
		return springSystemDTOs;
	}

}
