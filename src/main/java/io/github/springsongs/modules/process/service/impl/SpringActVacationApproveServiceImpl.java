package io.github.springsongs.modules.process.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.activiti.domain.SpringActUseTask;
import io.github.springsongs.modules.activiti.repo.SpringActUseTaskRepo;
import io.github.springsongs.modules.activiti.service.impl.SpringProcessService;
import io.github.springsongs.modules.process.domain.SpringActVacation;
import io.github.springsongs.modules.process.domain.SpringActVacationApprove;
import io.github.springsongs.modules.process.dto.SpringActVacationApproveDTO;
import io.github.springsongs.modules.process.repo.SpringActVacationApproveRepo;
import io.github.springsongs.modules.process.repo.SpringActVacationRepo;
import io.github.springsongs.modules.process.service.ISpringActVacationApproveService;
import io.github.springsongs.modules.sys.service.impl.SpringAritlceServiceImpl;
import io.github.springsongs.util.Constant;

@Service
public class SpringActVacationApproveServiceImpl implements ISpringActVacationApproveService {

	static Logger logger = LoggerFactory.getLogger(SpringAritlceServiceImpl.class);

	@Autowired
	private SpringActVacationApproveRepo springActVacationApproveRepo;

	@Autowired
	private ISpringActVacationApproveService springActVacationApproveService;

	@Autowired
	private SpringActVacationRepo springActVacationRepo;

	@Autowired
	private TaskService taskService;

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	private SpringActUseTaskRepo springActUseTaskRepo;

	@Autowired
	private SpringProcessService springProcessService;

	@Override
	public void deleteByPrimaryKey(String id) {
		springActVacationApproveRepo.deleteById(id);
	}

	@Override
	public void insert(SpringActVacationApproveDTO springActVacationApproveDTO) {
		SpringActVacationApprove springActVacationApprove = new SpringActVacationApprove();
		BeanUtils.copyProperties(springActVacationApproveDTO, springActVacationApprove);
		try {
			springActVacationApproveRepo.save(springActVacationApprove);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringActVacationApproveDTO selectByPrimaryKey(String id) {

		SpringActVacationApprove springActVacationApprove = springActVacationApproveRepo.getOne(id);
		// Optional.ofNullable(springActVacationApprove).orElseThrow(()->new
		// SpringSongsException(ResultCode.INFO_NOT_FOUND));
		if (null == springActVacationApprove) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringActVacationApproveDTO springActVacationApproveDTO = new SpringActVacationApproveDTO();
		BeanUtils.copyProperties(springActVacationApprove, springActVacationApproveDTO);
		return springActVacationApproveDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringActVacationApproveDTO springActVacationApproveDTO) {
		SpringActVacationApprove springActVacationApprove = springActVacationApproveRepo
				.getOne(springActVacationApproveDTO.getId());
		// Optional.ofNullable(springActVacationApprove).orElseThrow(()->new
		// SpringSongsException(ResultCode.INFO_NOT_FOUND));
		if (null == springActVacationApprove) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		try {
			SpringActVacationApprove springActVacationApproveDO = new SpringActVacationApprove();
			BeanUtils.copyProperties(springActVacationApproveDTO, springActVacationApproveDO);
			springActVacationApproveRepo.save(springActVacationApproveDO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public Page<SpringActVacationApproveDTO> getAllRecordByPage(SpringActVacationApprove record, Pageable pageable) {
		if (pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		Specification<SpringActVacationApprove> specification = new Specification<SpringActVacationApprove>() {
			@Override
			public Predicate toPredicate(Root<SpringActVacationApprove> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		Page<SpringActVacationApprove> springActVacationApproves = springActVacationApproveRepo.findAll(specification,
				pageable);
		List<SpringActVacationApproveDTO> springActVacationApproveDTOs = new ArrayList<>();
		springActVacationApproves.getContent().stream().forEach(springAritlceCategory -> {
			SpringActVacationApproveDTO springActVacationApproveDTO = new SpringActVacationApproveDTO();
			BeanUtils.copyProperties(springAritlceCategory, springActVacationApproveDTO);
			springActVacationApproveDTOs.add(springActVacationApproveDTO);
		});
		Page<SpringActVacationApproveDTO> pages = new PageImpl(springActVacationApproveDTOs, pageable,
				springActVacationApproves.getTotalElements());
		return pages;
	}

	@Override
	public void setDeleted(List<String> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void batchSaveExcel(List<String[]> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void completeTask(String taskId, String auditStr) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String procInstanceId = task.getProcessInstanceId();
		if (task == null) {
			logger.error("审核任务ID:{}查询到任务为空！", taskId);
			throw new SpringSongsException(ResultCode.TASK_HADED_CONFIG);
		}

		TaskDefinition taskDefinition = null;
		try {
			taskDefinition = springProcessService.getNextTaskInfo(taskId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("audit", auditStr);
		taskService.setVariables(taskId, map);
		taskService.complete(taskId, map);

		if (null != taskDefinition) {
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstanceId).list();
			SpringActUseTask springActUseTask = springActUseTaskRepo.findUserTaskByTaskDefKey(taskDefinition.getKey());
			String nextTaskId = "";
			for (Task taskTemp : tasks) {
				if (taskTemp.getTaskDefinitionKey().equals(taskDefinition.getKey())) {
					nextTaskId = taskTemp.getId();
					break;
				}
			}
			if (null != springActUseTask) {
				if ("assignee".equals(springActUseTask.getTaskType())) {
					taskService.setAssignee(nextTaskId, springActUseTask.getCandidateIds());
				}
			}
		}

		if (null == taskDefinition) {
			SpringActVacation springActVacation = springActVacationRepo.findByProcessInstanceId(procInstanceId);
			springActVacation.setProcessStatus(Short.valueOf(auditStr));
			springActVacationRepo.save(springActVacation);
		}
		//springActVacationApproveService.updateByPrimaryKey(record);
//		String excId = task.getExecutionId();
//		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
//		String activitiId = execution.getProcessDefinitionKey();
//		execution.getActivity().getActivityBehavior();
	}

	@Transactional
	@Override
	public void completeSpringActVacationApprove(SpringActVacationApproveDTO record, String taskId) {
		springActVacationApproveService.insert(record);
		springActVacationApproveService.completeTask(taskId, String.valueOf(record.getResult()));
		//Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// record.setProcessInstanceId(task.getProcessInstanceId());
		// record.setTaskId(task.getId());
		// springActVacationApproveService.updateByPrimaryKey(record);
	}

}
