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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringFriendLink;
import io.github.springsongs.modules.sys.dto.SpringFriendLinkDTO;
import io.github.springsongs.modules.sys.repo.SpringFriendLinkRepo;
import io.github.springsongs.modules.sys.service.ISpringFriendLinkService;
import io.github.springsongs.util.Constant;

@Service
public class SpringFriendLinkServiceImpl implements ISpringFriendLinkService {

	static Logger logger = LoggerFactory.getLogger(SpringFriendLinkServiceImpl.class);

	@Autowired
	private SpringFriendLinkRepo springFriendLinkRepo;

	@Override
	public void deleteByPrimaryKey(String id) {
		try {
			springFriendLinkRepo.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void insert(SpringFriendLinkDTO record) {
		SpringFriendLink springFriendLink = new SpringFriendLink();
		BeanUtils.copyProperties(record, springFriendLink);
		try {
			springFriendLinkRepo.save(springFriendLink);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}

	}

	@Override
	public SpringFriendLinkDTO selectByPrimaryKey(String id) {
		SpringFriendLink springFriendLink = null;
		springFriendLink = springFriendLinkRepo.getOne(id);
		if (null == springFriendLink) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringFriendLinkDTO springFriendLinkDTO = new SpringFriendLinkDTO();
		BeanUtils.copyProperties(springFriendLink, springFriendLinkDTO);
		return springFriendLinkDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringFriendLinkDTO record) {
		SpringFriendLink springFriendLink = springFriendLinkRepo.getOne(record.getId());
		if (null == springFriendLink) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringFriendLink springFriendLinkDO = new SpringFriendLink();
		BeanUtils.copyProperties(record, springFriendLinkDO);
		try {
			springFriendLinkRepo.save(springFriendLinkDO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public Page<SpringFriendLinkDTO> getAllRecordByPage(SpringFriendLink record, Pageable pageable) {
		if (pageable.getPageSize()<=0||pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		int page=pageable.getPageNumber()<=0?0:pageable.getPageNumber()-1;
		pageable = PageRequest.of(page, pageable.getPageSize());

		Specification<SpringFriendLink> specification = new Specification<SpringFriendLink>() {

			@Override
			public Predicate toPredicate(Root<SpringFriendLink> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		
		Page<SpringFriendLink> springFriendLinks = springFriendLinkRepo.findAll(specification, pageable);
		List<SpringFriendLinkDTO> springFriendLinkDTOs = new ArrayList<>();
		springFriendLinks.getContent().stream().forEach(springFriendLink -> {
			SpringFriendLinkDTO springFriendLinkDTO = new SpringFriendLinkDTO();
			BeanUtils.copyProperties(springFriendLink, springFriendLinkDTO);
			springFriendLinkDTOs.add(springFriendLinkDTO);
		});
		Page<SpringFriendLinkDTO> pages = new PageImpl(springFriendLinkDTOs, pageable, springFriendLinks.getTotalElements());
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
			springFriendLinkRepo.setDelete(ids);
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
