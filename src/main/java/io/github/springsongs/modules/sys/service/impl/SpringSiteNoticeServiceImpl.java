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
import io.github.springsongs.modules.sys.domain.SpringSiteNotice;
import io.github.springsongs.modules.sys.dto.SpringSiteNoticeDTO;
import io.github.springsongs.modules.sys.repo.SpringSiteNoticeRepo;
import io.github.springsongs.modules.sys.service.ISpringSiteNoticeService;
import io.github.springsongs.util.Constant;

@Service
public class SpringSiteNoticeServiceImpl implements ISpringSiteNoticeService {

	static Logger logger = LoggerFactory.getLogger(SpringSiteNoticeServiceImpl.class);

	@Autowired
	private SpringSiteNoticeRepo springSiteNoticeRepo;

	@Override
	public void deleteByPrimaryKey(String id) {
		try {
			springSiteNoticeRepo.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void insert(SpringSiteNoticeDTO record) {
		SpringSiteNotice springSiteNotice = new SpringSiteNotice();
		BeanUtils.copyProperties(record, springSiteNotice);
		try {
			springSiteNoticeRepo.save(springSiteNotice);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringSiteNoticeDTO selectByPrimaryKey(String id) {
		SpringSiteNotice springSiteNotice = null;
		springSiteNotice = springSiteNoticeRepo.getOne(id);
		if (null == springSiteNotice) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringSiteNoticeDTO springSiteNoticeDTO = new SpringSiteNoticeDTO();
		BeanUtils.copyProperties(springSiteNotice, springSiteNoticeDTO);
		return springSiteNoticeDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringSiteNoticeDTO record) {
		SpringSiteNotice springSiteNotice = springSiteNoticeRepo.getOne(record.getId());
		if (null == springSiteNotice) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringSiteNotice SpringSiteNoticeDO = new SpringSiteNotice();
		BeanUtils.copyProperties(record, SpringSiteNoticeDO);
		try {
			springSiteNoticeRepo.save(SpringSiteNoticeDO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public Page<SpringSiteNoticeDTO> getAllRecordByPage(SpringSiteNotice record, Pageable pageable) {
		if (pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}

		Specification<SpringSiteNotice> specification = new Specification<SpringSiteNotice>() {

			@Override
			public Predicate toPredicate(Root<SpringSiteNotice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

		Page<SpringSiteNotice> springSiteNotices = springSiteNoticeRepo.findAll(specification, pageable);
		List<SpringSiteNoticeDTO> springSiteNoticeDTOs = new ArrayList<>();
		springSiteNotices.getContent().stream().forEach(springSiteNotice -> {
			SpringSiteNoticeDTO springSiteNoticeDTO = new SpringSiteNoticeDTO();
			BeanUtils.copyProperties(springSiteNotice, springSiteNoticeDTO);
			springSiteNoticeDTOs.add(springSiteNoticeDTO);
		});
		Page<SpringSiteNoticeDTO> pages = new PageImpl(springSiteNoticeDTOs, pageable,
				springSiteNotices.getTotalElements());
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
			springSiteNoticeRepo.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void batchSaveExcel(List<String[]> list) {

	}

}
