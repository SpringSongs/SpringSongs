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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.job.domain.SpringJob;
import io.github.springsongs.modules.job.dto.SpringJobDTO;
import io.github.springsongs.modules.job.query.SpringJobQuery;
import io.github.springsongs.modules.job.repo.SpringJobRepo;
import io.github.springsongs.modules.job.service.ISpringJobService;

@Service
public class SpringJobServiceImpl implements ISpringJobService {

	static Logger logger = LoggerFactory.getLogger(SpringJobGroupServiceImpl.class);

	@Autowired
	private SpringJobRepo springJobRepo;

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
		springJobRepo.deleteById(id);

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
	public void insert(SpringJobDTO record) {
		try {
			springJobRepo.save(record);
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
	public SpringJobDTO selectByPrimaryKey(String id) {
		SpringJob springJob = null;
		try {
			springJob = springJobRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringJobDTO springJobDTO = new SpringJobDTO();
		BeanUtils.copyProperties(springJob, springJobDTO);
		return springJobDTO;
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
	public void updateByPrimaryKey(SpringJobDTO springJobDTO) {
		SpringJob entity = springJobRepo.getOne(springJobDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setGroupCode(springJobDTO.getGroupCode());
			entity.setGroupTitle(springJobDTO.getGroupTitle());
			entity.setTaskTitle(springJobDTO.getTaskTitle());
			entity.setTaskClassTitle(springJobDTO.getTaskClassTitle());
			entity.setCronExpression(springJobDTO.getCronExpression());
			entity.setStatus(springJobDTO.getStatus());
			try {
				springJobRepo.save(entity);
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
	 * @return Page<SpringJobDTO>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringJobDTO> getAllRecordByPage(SpringJobQuery record, Pageable pageable) {
		Specification<SpringJob> specification = new Specification<SpringJob>() {

			@Override
			public Predicate toPredicate(Root<SpringJob> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getGroupCode())) {
					Predicate groupCode = cb.like(root.get("groupCode").as(String.class), "%" + record.getGroupCode());
					predicates.add(groupCode);
				}
				if (!StringUtils.isEmpty(record.getTaskTitle())) {
					Predicate taskTitle = cb.like(root.get("taskTitle").as(String.class), "%" + record.getTaskTitle());
					predicates.add(taskTitle);
				}
				if (!StringUtils.isEmpty(record.getRemark())) {
					Predicate remark = cb.like(root.get("remark").as(String.class), "%" + record.getRemark());
					predicates.add(remark);
				}
//				Predicate deletionStateCode = cb.equal(root.get("deletedFlag").as(Boolean.class), false);
//				predicates.add(deletionStateCode);
				query.where(predicates.toArray(new Predicate[0]));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = new PageRequest(currPage - 1, size);
		// return springJobRepo.findAll(specification, pageable);

		Page<SpringJob> springJobs = springJobRepo.findAll(specification, pageable);
		List<SpringJobDTO> springJobDTOs = new ArrayList<>();
		springJobs.stream().forEach(springJob -> {
			SpringJobDTO springJobDTO = new SpringJobDTO();
			BeanUtils.copyProperties(springJob, springJobDTO);
			springJobDTOs.add(springJobDTO);
		});
		Page<SpringJobDTO> pages = new PageImpl(springJobDTOs, pageable, springJobs.getTotalElements());
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
}
