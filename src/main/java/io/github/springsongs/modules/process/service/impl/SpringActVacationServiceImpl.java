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

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
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
import io.github.springsongs.modules.job.service.impl.SpringJobServiceImpl;
import io.github.springsongs.modules.process.domain.SpringActVacation;
import io.github.springsongs.modules.process.dto.SpringActVacationDTO;
import io.github.springsongs.modules.process.repo.SpringActVacationRepo;
import io.github.springsongs.modules.process.service.ISpringActVacationService;

@Service
public class SpringActVacationServiceImpl implements ISpringActVacationService {

	static Logger logger = LoggerFactory.getLogger(SpringJobServiceImpl.class);

	@Autowired
	private SpringActVacationRepo springActVacationRepo;

	@Autowired
	private ISpringActVacationService springActVacationService;

	@Autowired
	protected IdentityService identityService;

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private SpringActUseTaskRepo springActUseTaskRepo;

	@Override
	public void deleteByPrimaryKey(String id) {
		springActVacationRepo.deleteById(id);
	}

	@Transactional
	@Override
	public void insert(SpringActVacationDTO record) {
		SpringActVacation springActVacation = new SpringActVacation();
		BeanUtils.copyProperties(record, springActVacation);
		try {
			springActVacationRepo.save(springActVacation);
			springActVacationService.startSpringActVacation(springActVacation);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringActVacationDTO selectByPrimaryKey(String id) {
		SpringActVacation springActVacation = null;
		try {
			springActVacation = springActVacationRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringActVacationDTO springActVacationDTO = new SpringActVacationDTO();
		BeanUtils.copyProperties(springActVacation, springActVacationDTO);
		return springActVacationDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringActVacationDTO record) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<SpringActVacationDTO> getAllRecordByPage(SpringActVacation record, Pageable pageable) {
		Specification<SpringActVacation> specification = new Specification<SpringActVacation>() {

			@Override
			public Predicate toPredicate(Root<SpringActVacation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

//				Predicate deletionStateCode = cb.equal(root.get("deletedFlag").as(Boolean.class), false);
//				predicates.add(deletionStateCode);
				query.where(predicates.toArray(new Predicate[0]));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = new PageRequest(currPage - 1, size);
		// return springJobRepo.findAll(specification, pageable);

		Page<SpringActVacation> springActVacations = springActVacationRepo.findAll(specification, pageable);
		List<SpringActVacationDTO> springActVacationDTOs = new ArrayList<>();
		springActVacations.stream().forEach(springActVacation -> {
			SpringActVacationDTO springActVacationDTO = new SpringActVacationDTO();
			BeanUtils.copyProperties(springActVacation, springActVacationDTO);
			springActVacationDTOs.add(springActVacationDTO);
		});
		Page<SpringActVacationDTO> pages = new PageImpl(springActVacationDTOs, pageable,
				springActVacations.getTotalElements());
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

	public String startSpringActVacation(SpringActVacation vacation) throws Exception {
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(vacation.getUserId().toString());
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("entity", vacation);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(vacation.getProcDefKey(),
				vacation.getId(), variables);
		String processInstanceId = processInstance.getId();
		vacation.setProcessInstanceId(processInstanceId);
		this.springActVacationRepo.save(vacation);
		logger.info("processInstanceId: " + processInstanceId);
		this.identityService.setAuthenticatedUserId(null);
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(vacation.getProcDefKey()).latestVersion().singleResult();
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		task.setCategory(processDefinition.getCategory());
		SpringActUseTask springActUseTask = springActUseTaskRepo.findUserTaskByTaskDefKey(task.getTaskDefinitionKey());
		taskService.saveTask(task);
		if ("assignee".equals(springActUseTask.getTaskYpe())) {
			taskService.setAssignee(task.getId(), springActUseTask.getCandidateIds());
		}
		return processInstanceId;
	}
}
