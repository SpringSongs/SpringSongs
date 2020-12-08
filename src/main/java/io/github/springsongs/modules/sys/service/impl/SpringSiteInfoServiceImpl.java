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
import io.github.springsongs.modules.sys.domain.SpringSiteInfo;
import io.github.springsongs.modules.sys.dto.SpringSiteInfoDTO;
import io.github.springsongs.modules.sys.repo.SpringSiteInfoRepo;
import io.github.springsongs.modules.sys.service.ISpringSiteInfoService;
import io.github.springsongs.util.Constant;

@Service
public class SpringSiteInfoServiceImpl implements ISpringSiteInfoService {

	static Logger logger = LoggerFactory.getLogger(SpringFriendLinkServiceImpl.class);

	@Autowired
	private SpringSiteInfoRepo springSiteInfoRepo;

	@Override
	public void deleteByPrimaryKey(String id) {
		try {
			springSiteInfoRepo.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void insert(SpringSiteInfoDTO record) {
		SpringSiteInfo springSiteInfo = new SpringSiteInfo();
		BeanUtils.copyProperties(record, springSiteInfo);
		try {
			springSiteInfoRepo.save(springSiteInfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringSiteInfoDTO selectByPrimaryKey(String id) {
		SpringSiteInfo springSiteInfo = null;
		springSiteInfo = springSiteInfoRepo.getOne(id);
		if (null == springSiteInfo) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringSiteInfoDTO springSiteInfoDTO = new SpringSiteInfoDTO();
		BeanUtils.copyProperties(springSiteInfo, springSiteInfoDTO);
		return springSiteInfoDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringSiteInfoDTO record) {
		SpringSiteInfo springSiteInfo = springSiteInfoRepo.getOne(record.getId());
		if (null == springSiteInfo) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringSiteInfo springSiteInfoDO = new SpringSiteInfo();
		BeanUtils.copyProperties(record, springSiteInfoDO);
		try {
			springSiteInfoRepo.save(springSiteInfoDO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public Page<SpringSiteInfoDTO> getAllRecordByPage(SpringSiteInfo record, Pageable pageable) {
		if (pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}

		Specification<SpringSiteInfo> specification = new Specification<SpringSiteInfo>() {

			@Override
			public Predicate toPredicate(Root<SpringSiteInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getTitle())) {
					Predicate title = cb.equal(root.get("title").as(String.class), record.getTitle());
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

		Page<SpringSiteInfo> springSiteInfos = springSiteInfoRepo.findAll(specification, pageable);
		List<SpringSiteInfoDTO> springSiteInfoDTOs = new ArrayList<>();
		springSiteInfos.getContent().stream().forEach(springSiteInfo -> {
			SpringSiteInfoDTO springSiteInfoDTO = new SpringSiteInfoDTO();
			BeanUtils.copyProperties(springSiteInfo, springSiteInfoDTO);
			springSiteInfoDTOs.add(springSiteInfoDTO);
		});
		Page<SpringSiteInfoDTO> pages = new PageImpl(springSiteInfoDTOs, pageable, springSiteInfos.getTotalElements());
		return pages;
	}

	@Override
	public void setDeleted(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springSiteInfoRepo.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void batchSaveExcel(List<String[]> list) {
		// TODO Auto-generated method stub

	}

}
