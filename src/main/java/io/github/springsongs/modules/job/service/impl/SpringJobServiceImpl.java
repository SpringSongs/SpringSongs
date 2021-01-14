package io.github.springsongs.modules.job.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
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
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.enumeration.SchedulerStatus;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.job.domain.SpringBaseJob;
import io.github.springsongs.modules.job.domain.SpringJob;
import io.github.springsongs.modules.job.dto.SpringJobDTO;
import io.github.springsongs.modules.job.query.SpringJobQuery;
import io.github.springsongs.modules.job.repo.SpringJobRepo;
import io.github.springsongs.modules.job.service.ISpringJobService;
import io.github.springsongs.util.Constant;

@Service
public class SpringJobServiceImpl implements ISpringJobService {

	static Logger logger = LoggerFactory.getLogger(SpringJobServiceImpl.class);

	@Autowired
	private SpringJobRepo springJobRepo;

	@Autowired
	// @Qualifier("Scheduler")
	private Scheduler scheduler;

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
		if (pageable.getPageSize()<=0||pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		int page=pageable.getPageNumber()<=0?0:pageable.getPageNumber()-1;
		pageable = PageRequest.of(page, pageable.getPageSize());
		
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

	@Override
	@Transactional
	public void addTask(SpringJobDTO record) {
		try {

			scheduler.start();

			JobDetail jobDetail = JobBuilder.newJob(getClass(record.getTaskClassTitle()).getClass())
					.withIdentity(record.getTaskClassTitle(), record.getGroupCode()).build();

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(record.getCronExpression());

			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(record.getTaskClassTitle(), record.getGroupCode()).withSchedule(scheduleBuilder)
					.build();

			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.ADD_TASK_FAIL);
		}
		try {
			SpringJob springJob = new SpringJob();
			record.setStatus(SchedulerStatus.CREATED.getStatus());
			BeanUtils.copyProperties(record, springJob);
			springJobRepo.save(springJob);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	@Transactional
	public void updateTask(SpringJobDTO springJobDTO) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(springJobDTO.getTaskClassTitle(),
					springJobDTO.getGroupCode());

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(springJobDTO.getCronExpression());

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			scheduler.rescheduleJob(triggerKey, trigger);

		} catch (SchedulerException e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.UPDATE_TASK_FAIL);
		}

		SpringJob entity = springJobRepo.getOne(springJobDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			springJobDTO.setStatus(SchedulerStatus.UPDATED.getStatus());
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

	@Override
	@Transactional
	public void pauseTask(String taskClassName, String groupCode) {
		try {
			scheduler.pauseJob(JobKey.jobKey(taskClassName, groupCode));
		} catch (SchedulerException ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.PAUSE_TASK_FAIL);
		}
		SpringJob entity = springJobRepo.findByGroupCodeAndTaskClassTitle(groupCode, taskClassName);
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			try {
				entity.setStatus(SchedulerStatus.PAUSED.getStatus());
				springJobRepo.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	@Override
	@Transactional
	public void deleteTask(String taskClassName, String groupCode) {
		try {
			scheduler.pauseTrigger(TriggerKey.triggerKey(taskClassName, groupCode));
			scheduler.unscheduleJob(TriggerKey.triggerKey(taskClassName, groupCode));
			scheduler.deleteJob(JobKey.jobKey(taskClassName, groupCode));
		} catch (SchedulerException ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.DELETE_TASK_FAIL);
		}
		SpringJob entity = springJobRepo.findByGroupCodeAndTaskClassTitle(groupCode, taskClassName);
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			try {
				entity.setStatus(SchedulerStatus.DELETED.getStatus());
				springJobRepo.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	private SpringBaseJob getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (SpringBaseJob) class1.newInstance();
	}

	@Override
	@Transactional
	public void resumeTask(String taskClassName, String taskGroupCode) {
		try {
			scheduler.resumeJob(JobKey.jobKey(taskClassName, taskGroupCode));
		} catch (SchedulerException ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.RESUME_TASK_FAIL);
		}
		SpringJob entity = springJobRepo.findByGroupCodeAndTaskClassTitle(taskGroupCode, taskClassName);
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			try {
				entity.setStatus(SchedulerStatus.RESUME.getStatus());
				springJobRepo.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}
}
