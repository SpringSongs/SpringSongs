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
import io.github.springsongs.modules.sys.domain.SpringDictionary;
import io.github.springsongs.modules.sys.dto.SpringDictionaryDTO;
import io.github.springsongs.modules.sys.dto.query.SpringDictionaryQuery;
import io.github.springsongs.modules.sys.repo.SpringDictionaryDetailRepo;
import io.github.springsongs.modules.sys.repo.SpringDictionaryRepo;
import io.github.springsongs.modules.sys.service.ISpringDictionaryService;
import io.github.springsongs.util.Constant;

@Service
public class SpringDictionaryServiceImpl implements ISpringDictionaryService {

	static Logger logger = LoggerFactory.getLogger(SpringDictionaryServiceImpl.class);

	@Autowired
	private SpringDictionaryRepo springDictionaryRepo;

	@Autowired
	private SpringDictionaryDetailRepo springDictionaryDetailRepo;

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
			springDictionaryRepo.deleteById(id);
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
	public void insert(SpringDictionaryDTO record) {
		SpringDictionary springDictionary = new SpringDictionary();
		BeanUtils.copyProperties(record, springDictionary);
		try {
			springDictionaryRepo.save(springDictionary);
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
	public SpringDictionaryDTO selectByPrimaryKey(String id) {
		SpringDictionary springDictionary = null;
		try {
			springDictionary = springDictionaryRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringDictionaryDTO springDictionaryDTO = new SpringDictionaryDTO();
		BeanUtils.copyProperties(springDictionary, springDictionaryDTO);
		return springDictionaryDTO;
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
	public void updateByPrimaryKey(SpringDictionaryDTO springDictionaryDTO) {
		SpringDictionary entity = springDictionaryRepo.getOne(springDictionaryDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else if (!entity.getEnableEdit()) {
			throw new SpringSongsException(ResultCode.INFO_CAN_NOT_EDIT);
		} else {
			entity.setCode(springDictionaryDTO.getCode());
			entity.setTitle(springDictionaryDTO.getTitle());
			entity.setDescription(springDictionaryDTO.getDescription());
			entity.setSortCode(springDictionaryDTO.getSortCode());
			entity.setEnableEdit(springDictionaryDTO.getEnableEdit());
			try {
				springDictionaryRepo.save(entity);
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
	 * @return Page<BaseDictionaryEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringDictionaryDTO> getAllRecordByPage(SpringDictionaryQuery springDictionaryQuery,
			Pageable pageable) {
		
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		
		Specification<SpringDictionary> specification = new Specification<SpringDictionary>() {

			@Override
			public Predicate toPredicate(Root<SpringDictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springDictionaryQuery.getCode())) {
					Predicate code = cb.like(root.get("code").as(String.class), "%" + springDictionaryQuery.getCode());
					predicates.add(code);
				}
				if (!StringUtils.isEmpty(springDictionaryQuery.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class),
							"%" + springDictionaryQuery.getTitle());
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
		Page<SpringDictionary> springDictionarys = springDictionaryRepo.findAll(specification, pageable);
		List<SpringDictionaryDTO> pringDictionaryDTOs = new ArrayList<>();
		springDictionarys.stream().forEach(springDictionary -> {
			SpringDictionaryDTO springDictionaryDTO = new SpringDictionaryDTO();
			BeanUtils.copyProperties(springDictionary, springDictionaryDTO);
			pringDictionaryDTOs.add(springDictionaryDTO);
		});
		Page<SpringDictionaryDTO> pages = new PageImpl(pringDictionaryDTOs, pageable,
				springDictionarys.getTotalElements());
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
		List<String> codes = new ArrayList<String>();
		List<SpringDictionary> entityList = springDictionaryRepo.listByIds(ids);
		for (SpringDictionary entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		entityList.stream().forEach(t -> {
			codes.add(t.getCode());
		});
		try {
			springDictionaryDetailRepo.setDeleteByDictionCode(codes);
			springDictionaryRepo.setDelete(ids);
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
		try {
			springDictionaryRepo.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public List<SpringDictionaryDTO> listByIds(List<String> ids) {
		List<SpringDictionary> springDictionarys = null;
		try {
			springDictionarys = springDictionaryRepo.findInIds(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
		List<SpringDictionaryDTO> springDictionaryDTOs = new ArrayList<>();
		springDictionarys.stream().forEach(springDictionary -> {
			SpringDictionaryDTO springDictionaryDTO = new SpringDictionaryDTO();
			BeanUtils.copyProperties(springDictionary, springDictionaryDTO);
			springDictionaryDTOs.add(springDictionaryDTO);
		});
		return springDictionaryDTOs;
	}
}
