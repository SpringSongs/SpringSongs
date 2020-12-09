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
import io.github.springsongs.modules.sys.domain.SpringDistrict;
import io.github.springsongs.modules.sys.dto.SpringDistrictDTO;
import io.github.springsongs.modules.sys.repo.SpringDistrictRepo;
import io.github.springsongs.modules.sys.service.ISpringDistrictService;
import io.github.springsongs.util.Constant;

@Service
public class SpringDistrictServiceImpl implements ISpringDistrictService {

	static Logger logger = LoggerFactory.getLogger(SpringDistrictServiceImpl.class);

	@Autowired
	private SpringDistrictRepo springDistrictRepo;

	@Override
	public void deleteByPrimaryKey(Long id) {
		try {
			springDistrictRepo.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void insert(SpringDistrictDTO record) {
		SpringDistrict springDistrict = new SpringDistrict();
		BeanUtils.copyProperties(record, springDistrict);
		try {
			springDistrictRepo.save(springDistrict);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringDistrictDTO selectByPrimaryKey(Long id) {
		SpringDistrict springDistrict = null;
		springDistrict = springDistrictRepo.getOne(id);
		if (null == springDistrict) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringDistrictDTO springDistrictDTO = new SpringDistrictDTO();
		BeanUtils.copyProperties(springDistrict, springDistrictDTO);
		return springDistrictDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringDistrictDTO record) {
		SpringDistrict springDistrict = springDistrictRepo.getOne(record.getId());
		if (null == springDistrict) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringDistrict springDistrictDO = new SpringDistrict();
		BeanUtils.copyProperties(record, springDistrictDO);
		try {
			springDistrictRepo.save(springDistrictDO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public Page<SpringDistrictDTO> getAllRecordByPage(SpringDistrict record, Pageable pageable) {
		if (pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}

		Specification<SpringDistrict> specification = new Specification<SpringDistrict>() {

			@Override
			public Predicate toPredicate(Root<SpringDistrict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getParentId())) {
					Predicate parentId = cb.equal(root.get("parentId").as(String.class), record.getParentId());
					predicates.add(parentId);
				}
				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.asc(root.get("sortOrder").as(Integer.class)),cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		
		Page<SpringDistrict> springDistricts = springDistrictRepo.findAll(specification, pageable);
		List<SpringDistrictDTO> pringDictionaryDTOs = new ArrayList<>();
		springDistricts.getContent().stream().forEach(springDistrict -> {
			SpringDistrictDTO springDistrictDTO = new SpringDistrictDTO();
			BeanUtils.copyProperties(springDistrict, springDistrictDTO);
			pringDictionaryDTOs.add(springDistrictDTO);
		});
		Page<SpringDistrictDTO> pages = new PageImpl(pringDictionaryDTOs, pageable, springDistricts.getTotalElements());
		return pages;
	}

	@Override
	public void setDeleted(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springDistrictRepo.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}

	}

	@Override
	public void batchSaveExcel(List<String[]> list) {

	}

	@Override
	public List<SpringDistrictDTO> listSpringDistrictByParentId(Long parentId) {
		List<SpringDistrict> springDistrictList=springDistrictRepo.listSpringDistrictByParentId(parentId);
		List<SpringDistrictDTO> springDistrictDTOList=new ArrayList<>();
		springDistrictList.stream().forEach(springDistrict->{
			SpringDistrictDTO springDistrictDTO=new SpringDistrictDTO();
			BeanUtils.copyProperties(springDistrict,springDistrictDTO);
			springDistrictDTOList.add(springDistrictDTO);
		});
		return springDistrictDTOList;
	}

}
