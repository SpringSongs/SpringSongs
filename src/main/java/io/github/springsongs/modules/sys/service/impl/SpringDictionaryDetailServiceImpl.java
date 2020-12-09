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
import io.github.springsongs.modules.sys.domain.SpringDictionaryDetail;
import io.github.springsongs.modules.sys.dto.SpringDictionaryDetailDTO;
import io.github.springsongs.modules.sys.dto.query.SpringDictionaryDetailQuery;
import io.github.springsongs.modules.sys.repo.SpringDictionaryDetailRepo;
import io.github.springsongs.modules.sys.service.ISpringDictionaryDetailService;
import io.github.springsongs.util.Constant;

@Service
public class SpringDictionaryDetailServiceImpl implements ISpringDictionaryDetailService {

	static Logger logger = LoggerFactory.getLogger(SpringContactServiceImpl.class);

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
			springDictionaryDetailRepo.deleteById(id);
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
	public void insert(SpringDictionaryDetailDTO record) {
		SpringDictionaryDetail springDictionaryDetail = new SpringDictionaryDetail();
		BeanUtils.copyProperties(record, springDictionaryDetail);
		try {
			springDictionaryDetailRepo.save(springDictionaryDetail);
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
	public SpringDictionaryDetailDTO selectByPrimaryKey(String id) {
		SpringDictionaryDetail springDictionaryDetail = null;
		try {
			springDictionaryDetail = springDictionaryDetailRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringDictionaryDetailDTO springDictionaryDetailDTO = new SpringDictionaryDetailDTO();
		BeanUtils.copyProperties(springDictionaryDetail, springDictionaryDetailDTO);
		return springDictionaryDetailDTO;
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
	public void updateByPrimaryKey(SpringDictionaryDetailDTO springDictionaryDetailDTO) {
		SpringDictionaryDetail entity = springDictionaryDetailRepo.getOne(springDictionaryDetailDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setDictionaryCode(springDictionaryDetailDTO.getDictionaryCode());
			entity.setParentId(springDictionaryDetailDTO.getParentId());
			entity.setDetailCode(springDictionaryDetailDTO.getDetailCode());
			entity.setDetailName(springDictionaryDetailDTO.getDetailName());
			entity.setDetailValue(springDictionaryDetailDTO.getDetailValue());
			entity.setDescription(springDictionaryDetailDTO.getDescription());
			entity.setChildIds(springDictionaryDetailDTO.getChildIds());
			entity.setSortCode(springDictionaryDetailDTO.getSortCode());
			entity.setEnableEdit(springDictionaryDetailDTO.getEnableEdit());
			entity.setEnableDelete(springDictionaryDetailDTO.getEnableDelete());
			entity.setDeletedStatus(springDictionaryDetailDTO.getDeletedStatus());
			try {
				springDictionaryDetailRepo.save(entity);
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
	 * @return Page<BaseDictionaryDetailEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringDictionaryDetailDTO> getAllRecordByPage(SpringDictionaryDetailQuery springDictionaryDetailQuery,
			Pageable pageable) {
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		Specification<SpringDictionaryDetail> specification = new Specification<SpringDictionaryDetail>() {

			@Override
			public Predicate toPredicate(Root<SpringDictionaryDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (!StringUtils.isEmpty(springDictionaryDetailQuery.getDictionaryCode())) {
					Predicate dictionaryCode = cb.equal(root.get("dictionaryCode").as(String.class),
							springDictionaryDetailQuery.getDictionaryCode());
					predicates.add(dictionaryCode);
				}
				if (!StringUtils.isEmpty(springDictionaryDetailQuery.getDetailCode())) {
					Predicate detailCode = cb.like(root.get("detailCode").as(String.class),
							"%" + springDictionaryDetailQuery.getDetailCode());
					predicates.add(detailCode);
				}
				if (!StringUtils.isEmpty(springDictionaryDetailQuery.getDetailName())) {
					Predicate detailName = cb.like(root.get("detailName").as(String.class),
							"%" + springDictionaryDetailQuery.getDetailName());
					predicates.add(detailName);
				}
				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.asc(root.get("sortCode").as(Integer.class)),cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		Page<SpringDictionaryDetail> springDictionaryDetails = springDictionaryDetailRepo.findAll(specification,
				pageable);
		List<SpringDictionaryDetailDTO> springDictionaryDetailDTOs = new ArrayList<>();
		springDictionaryDetails.getContent().stream().forEach(springDictionaryDetail -> {
			SpringDictionaryDetailDTO springDictionaryDetailDTO = new SpringDictionaryDetailDTO();
			BeanUtils.copyProperties(springDictionaryDetail, springDictionaryDetailDTO);
			springDictionaryDetailDTOs.add(springDictionaryDetailDTO);
		});
		Page<SpringDictionaryDetailDTO> pages = new PageImpl(springDictionaryDetailDTOs, pageable,
				springDictionaryDetails.getTotalElements());
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
			springDictionaryDetailRepo.setDelete(ids);
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
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springDictionaryDetailRepo.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
	}

	@Override
	public void setDeleteByCode(String code) {
		try {
			springDictionaryDetailRepo.setDeleteByCode(code);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
	}

	@Override
	public List<SpringDictionaryDetailDTO> listSpringDictionaryDetailByDictionaryCode(String dictionaryCode) {
		List<SpringDictionaryDetailDTO> springDictionaryDetailDTOList=new ArrayList<>();
		List<SpringDictionaryDetail> springDictionaryDetailList=springDictionaryDetailRepo.listSpringDictionaryDetailByDictionaryCode(dictionaryCode);
		springDictionaryDetailList.stream().forEach(springDictionaryDetail->{
			SpringDictionaryDetailDTO springDictionaryDetailDTO=new SpringDictionaryDetailDTO();
			BeanUtils.copyProperties(springDictionaryDetail, springDictionaryDetailDTO);
			springDictionaryDetailDTOList.add(springDictionaryDetailDTO);
		});
		return springDictionaryDetailDTOList;
	}
}
