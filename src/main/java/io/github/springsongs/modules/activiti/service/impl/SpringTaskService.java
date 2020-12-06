package io.github.springsongs.modules.activiti.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.springsongs.modules.activiti.dto.SpringTaskDTO;
import io.github.springsongs.modules.activiti.util.ActivitiUtils;
import io.github.springsongs.util.ActivitiConstants;

@Service
public class SpringTaskService {

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	protected RepositoryService repositoryService;

	public Page<SpringTaskDTO> getTasksByStarter(String userId, String title, String category, Pageable pageable) {
		List<SpringTaskDTO> tasks = new ArrayList<>();
		HistoricProcessInstanceQuery histProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
				.startedBy(userId).includeProcessVariables().orderByProcessInstanceStartTime().desc();
		if (StringUtils.isNotBlank(category)) {
			histProcessInstanceQuery.processDefinitionCategory(category);
		}
		if (StringUtils.isNotBlank(title)) {
			histProcessInstanceQuery.variableValueLikeIgnoreCase("title", "%" + title + "%");
		}
		histProcessInstanceQuery.listPage(pageable.getPageNumber() * pageable.getPageSize(), pageable.getPageSize())
				.stream().forEach(processInstance -> {
					HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery()
							.processInstanceId(processInstance.getId()).includeProcessVariables()
							.orderByTaskCreateTime().desc();
					SpringTaskDTO dto = getTaskDTO(taskQuery.list().get(0),
							processInstance.getEndTime() == null ? ActivitiConstants.PROCESS_INSTANCE_ING
									: ActivitiConstants.PROCESS_INSTANCE_DONE);
					dto.setTime(processInstance.getStartTime().getTime());
					tasks.add(dto);
				});
		Page<SpringTaskDTO> pages = new PageImpl(tasks, pageable, histProcessInstanceQuery.count());
		return pages;
	}

	public Page<SpringTaskDTO> getTodoTasks(String userId, String title, String category, Pageable pageable) {
		List<SpringTaskDTO> tasks = new ArrayList<>();
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();
		if (StringUtils.isNotBlank(category)) {
			taskQuery.taskCategory(category);
		}
		if (StringUtils.isNotBlank(title)) {
			taskQuery.processVariableValueLikeIgnoreCase("title", "%" + title + "%");
		}
		List<SpringTaskDTO> taskDTOS = taskQuery
				.listPage(pageable.getPageNumber() * pageable.getPageSize(), pageable.getPageSize()).stream()
				.map(task -> {
					SpringTaskDTO dto = getTaskDTO(task,
							task.getAssignee() == null ? ActivitiConstants.TASK_STATUS_CLAIM
									: ActivitiConstants.TASK_STATUS_TODO);
					dto.setTime(task.getCreateTime().getTime());
					return dto;
				}).collect(Collectors.toList());
		tasks.addAll(taskDTOS);
		Page<SpringTaskDTO> pages = new PageImpl(tasks, pageable, taskQuery.count());
		return pages;
	}

	public Page<SpringTaskDTO> getFinishTasks(String userId, String title, String category, Pageable pageable) {
		List<SpringTaskDTO> tasks = new ArrayList<>();
		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId)
				.finished().includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		if (StringUtils.isNotBlank(category)) {
			histTaskQuery.taskCategory(category);
		}
		if (StringUtils.isNotBlank(title)) {
			histTaskQuery.processVariableValueLikeIgnoreCase("title", "%" + title + "%");
		}
		Map<String, List<HistoricTaskInstance>> collect = histTaskQuery.list().stream()
				.collect(Collectors.groupingBy(taskInstance -> taskInstance.getProcessInstanceId()));
		collect.entrySet().stream().forEach(entry -> {
			HistoricTaskInstance task = entry.getValue().get(0);
			HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId()).includeProcessVariables().singleResult();
			SpringTaskDTO dto = getTaskDTO(task,
					processInstance.getEndTime() == null ? ActivitiConstants.PROCESS_INSTANCE_ING
							: ActivitiConstants.PROCESS_INSTANCE_DONE);
			dto.setTime(task.getEndTime().getTime());
			tasks.add(dto);
		});
		List<SpringTaskDTO> taskDTOs = tasks.stream().sorted(Comparator.comparing(SpringTaskDTO::getTime).reversed())
				.collect(Collectors.toList());
		Page<SpringTaskDTO> pages = new PageImpl(tasks, pageable, histTaskQuery.count());
		return pages;
	}

	private SpringTaskDTO getTaskDTO(TaskInfo task, String status) {
		ProcessDefinition processDefinition = getProcessDefinition(task.getProcessDefinitionId());
		String deploymentId = processDefinition.getDeploymentId();
		Deployment deployment = getDeployment(deploymentId);
		return ActivitiUtils.toTaskDTO(task, status, processDefinition, deployment);
	}

	private ProcessDefinition getProcessDefinition(String processDefinitionId) {
		return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
	}

	private Deployment getDeployment(String deploymentId) {
		return repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	}
}
