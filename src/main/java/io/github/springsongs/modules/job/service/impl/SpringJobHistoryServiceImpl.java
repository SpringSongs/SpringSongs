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

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.job.domain.SpringJobHistory;
import io.github.springsongs.modules.job.dto.SpringJobHistoryDTO;
import io.github.springsongs.modules.job.repo.SpringJobHistoryRepo;
import io.github.springsongs.modules.job.service.ISpringJobHistoryService;
import io.github.springsongs.util.Constant;

@Service
public class SpringJobHistoryServiceImpl implements ISpringJobHistoryService {

	static Logger logger = LoggerFactory.getLogger(SpringJobHistoryServiceImpl.class);

	@Autowired
	private SpringJobHistoryRepo springJobHistoryRepo;

	@Override
	public void deleteByPrimaryKey(String id) {
		try {
			springJobHistoryRepo.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void insert(SpringJobHistoryDTO springJobHistoryDTO) {
		SpringJobHistory springJobHistory = new SpringJobHistory();
		BeanUtils.copyProperties(springJobHistoryDTO, springJobHistory);
		try {
			springJobHistoryRepo.save(springJobHistory);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringJobHistoryDTO selectByPrimaryKey(String id) {
		SpringJobHistory springJobHistory = null;
		try {
			springJobHistory = springJobHistoryRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringJobHistoryDTO springJobHistoryDTO = new SpringJobHistoryDTO();
		BeanUtils.copyProperties(springJobHistory, springJobHistoryDTO);
		return springJobHistoryDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringJobHistoryDTO record) {

	}

	@Override
	public Page<SpringJobHistoryDTO> getAllRecordByPage(SpringJobHistory record, Pageable pageable) {
		if (pageable.getPageSize()<=0||pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		int page=pageable.getPageNumber()<=0?0:pageable.getPageNumber()-1;
		pageable = PageRequest.of(page, pageable.getPageSize());
		Specification<SpringJobHistory> specification = new Specification<SpringJobHistory>() {

			@Override
			public Predicate toPredicate(Root<SpringJobHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

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
		Page<SpringJobHistory> springJobHistorys = springJobHistoryRepo.findAll(specification, pageable);
		List<SpringJobHistoryDTO> springJobHistoryDTOs = new ArrayList<>();
		springJobHistorys.stream().forEach(springJobHistory -> {
			SpringJobHistoryDTO springJobHistoryDTO = new SpringJobHistoryDTO();
			BeanUtils.copyProperties(springJobHistory, springJobHistoryDTO);
			springJobHistoryDTOs.add(springJobHistoryDTO);
		});
		Page<SpringJobHistoryDTO> pages = new PageImpl(springJobHistoryDTOs, pageable,
				springJobHistorys.getTotalElements());
		return pages;
	}

	@Override
	public void setDeleted(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springJobHistoryRepo.setDelete(ids);
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
