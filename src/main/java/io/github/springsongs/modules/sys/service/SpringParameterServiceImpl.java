package io.github.springsongs.modules.sys.service;

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
import io.github.springsongs.modules.sys.domain.SpringParameter;
import io.github.springsongs.modules.sys.dto.SpringParameterDTO;
import io.github.springsongs.modules.sys.query.SpringParameterQuery;
import io.github.springsongs.modules.sys.repo.SpringParameterRepo;
import io.github.springsongs.modules.sys.service.impl.SpringDictionaryServiceImpl;

@Service
@Transactional
public class SpringParameterServiceImpl implements ISpringParameterService {
	static Logger logger = LoggerFactory.getLogger(SpringDictionaryServiceImpl.class);
	@Autowired
	private SpringParameterRepo springParameterRepo;

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
			springParameterRepo.deleteById(id);
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
	public void insert(SpringParameterDTO record) {
		SpringParameter springParameter = new SpringParameter();
		BeanUtils.copyProperties(record, springParameter);
		try {
			springParameterRepo.save(springParameter);
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
	public SpringParameterDTO selectByPrimaryKey(String id) {
		SpringParameter springParameter = null;
		try {
			springParameter = springParameterRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringParameterDTO springParameterDTO = new SpringParameterDTO();
		BeanUtils.copyProperties(springParameter, springParameterDTO);
		return springParameterDTO;
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
	public void updateByPrimaryKey(SpringParameterDTO springParameterDTO) {
		SpringParameter entity = springParameterRepo.getOne(springParameterDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else if (!entity.getEnableEdit()) {
			throw new SpringSongsException(ResultCode.INFO_CAN_NOT_EDIT);
		} else {
			entity.setCode(springParameterDTO.getCode());
			entity.setK(springParameterDTO.getK());
			entity.setV(springParameterDTO.getV());
			entity.setSortCode(springParameterDTO.getSortCode());
			entity.setEnableEdit(springParameterDTO.getEnableEdit());
			entity.setEnableDelete(springParameterDTO.getEnableDelete());
			try {
				springParameterRepo.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
			}
		}
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseParameterEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringParameterDTO> getAllRecordByPage(SpringParameterQuery springParameterQuery, Pageable pageable) {
		Specification<SpringParameter> specification = new Specification<SpringParameter>() {

			@Override
			public Predicate toPredicate(Root<SpringParameter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (!StringUtils.isEmpty(springParameterQuery.getCode())) {
					Predicate code = cb.like(root.get("code").as(String.class), "%" + springParameterQuery.getCode());
					predicates.add(code);
				}
				if (!StringUtils.isEmpty(springParameterQuery.getK())) {
					Predicate k = cb.like(root.get("k").as(String.class), "%" + springParameterQuery.getK());
					predicates.add(k);
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
		// return springParameterRepo.findAll(specification, pageable);
		Page<SpringParameter> springParameters = springParameterRepo.findAll(specification, pageable);
		List<SpringParameterDTO> springParameterDTOs = new ArrayList<>();
		springParameters.stream().forEach(springParameter -> {
			SpringParameterDTO springParameterDTO = new SpringParameterDTO();
			BeanUtils.copyProperties(springParameter, springParameterDTO);
			springParameterDTOs.add(springParameterDTO);
		});
		Page<SpringParameterDTO> pages = new PageImpl(springParameterDTOs, pageable,
				springParameters.getTotalElements());
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
		List<SpringParameter> entityList = springParameterRepo.listByIds(ids);
		for (SpringParameter entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springParameterRepo.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
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
		List<SpringParameter> entityList = springParameterRepo.listByIds(ids);
		for (SpringParameter entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springParameterRepo.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
	}

	@Override
	public List<SpringParameterDTO> listByIds(List<String> ids) {
		List<SpringParameter> springParameters = springParameterRepo.listByIds(ids);
		List<SpringParameterDTO> springParameterDTOs = new ArrayList<>();
		springParameters.stream().forEach(springParameter -> {
			SpringParameterDTO springParameterDTO = new SpringParameterDTO();
			BeanUtils.copyProperties(springParameter, springParameterDTO);
			springParameterDTOs.add(springParameterDTO);
		});
		return springParameterDTOs;
	}
}
