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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringSiteMessage;
import io.github.springsongs.modules.sys.dto.SpringSiteMessageDTO;
import io.github.springsongs.modules.sys.repo.SpringSiteMessageRepo;
import io.github.springsongs.modules.sys.service.ISpringSiteMessageService;
import io.github.springsongs.util.Constant;

@Service
public class SpringSiteMessageServiceImpl implements ISpringSiteMessageService {

	static Logger logger = LoggerFactory.getLogger(SpringSiteMessageServiceImpl.class);

	@Autowired
	private SpringSiteMessageRepo springSiteMessageRepo;

	@Override
	public void deleteByPrimaryKey(String id) {
		try {
			springSiteMessageRepo.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Async
	@Override
	public void insert(SpringSiteMessageDTO record) {
		SpringSiteMessage springSiteMessage = new SpringSiteMessage();
		BeanUtils.copyProperties(record, springSiteMessage);
		try {
			springSiteMessageRepo.save(springSiteMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringSiteMessageDTO selectByPrimaryKey(String id) {
		SpringSiteMessage springSiteMessage = null;
		springSiteMessage = springSiteMessageRepo.getOne(id);
		if (null == springSiteMessage) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		if (springSiteMessage.getStatus() != 1) {
			springSiteMessage.setStatus((short) 1);
			springSiteMessageRepo.save(springSiteMessage);
		}
		SpringSiteMessageDTO springSiteMessageDTO = new SpringSiteMessageDTO();
		BeanUtils.copyProperties(springSiteMessage, springSiteMessageDTO);
		return springSiteMessageDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringSiteMessageDTO record) {
		SpringSiteMessage springSiteMessage = springSiteMessageRepo.getOne(record.getId());
		if (null == springSiteMessage) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringSiteMessage springSiteMessageDO = new SpringSiteMessage();
		BeanUtils.copyProperties(record, springSiteMessageDO);
		try {
			springSiteMessageRepo.save(springSiteMessageDO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public Page<SpringSiteMessageDTO> getAllRecordByPage(SpringSiteMessage record, Pageable pageable) {
		if (pageable.getPageSize()<=0||pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		int page=pageable.getPageNumber()<=0?0:pageable.getPageNumber()-1;
		pageable = PageRequest.of(page, pageable.getPageSize());

		Specification<SpringSiteMessage> specification = new Specification<SpringSiteMessage>() {

			@Override
			public Predicate toPredicate(Root<SpringSiteMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getToUserId())) {
					Predicate toUserId = cb.equal(root.get("toUserId").as(String.class), record.getToUserId());
					predicates.add(toUserId);
				}
				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};

		Page<SpringSiteMessage> springSiteMessages = springSiteMessageRepo.findAll(specification, pageable);
		List<SpringSiteMessageDTO> springSiteMessageDTOs = new ArrayList<>();
		springSiteMessages.getContent().stream().forEach(springSiteMessage -> {
			SpringSiteMessageDTO springSiteMessageDTO = new SpringSiteMessageDTO();
			BeanUtils.copyProperties(springSiteMessage, springSiteMessageDTO);
			springSiteMessageDTOs.add(springSiteMessageDTO);
		});
		Page<SpringSiteMessageDTO> pages = new PageImpl(springSiteMessageDTOs, pageable,
				springSiteMessages.getTotalElements());
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
			springSiteMessageRepo.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void batchSaveExcel(List<String[]> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public int countNotReadMessageByUserId(String toUserId) {
		return springSiteMessageRepo.countNotReadMessageByUserId(toUserId);
	}

}
