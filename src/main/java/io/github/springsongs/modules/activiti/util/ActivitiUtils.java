package io.github.springsongs.modules.activiti.util;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.TaskInfo;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.activiti.dto.SpringProcessDTO;
import io.github.springsongs.modules.activiti.dto.SpringTaskDTO;

public class ActivitiUtils {

	/**
	 * 抽取流程实例需要返回的内容
	 *
	 * @param processDefinition
	 * @param deployment
	 * @return
	 */
	public static SpringProcessDTO toProcessDTO(ProcessDefinition processDefinition, Deployment deployment) {
		if (null == processDefinition || null == deployment) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		SpringProcessDTO dto = new SpringProcessDTO();
		dto.category = processDefinition.getCategory();
		dto.processonDefinitionId = processDefinition.getId();
		dto.key = processDefinition.getKey();
		dto.name = deployment.getName();
		dto.revision = processDefinition.getVersion();
		dto.deploymentTime = deployment.getDeploymentTime().getTime();
		dto.xmlName = processDefinition.getResourceName();
		dto.picName = processDefinition.getDiagramResourceName();
		dto.deploymentId = deployment.getId();
		dto.suspend = processDefinition.isSuspended();
		dto.suspendStr = processDefinition.isSuspended() == true ? "未激活" : "激活";
		dto.description = processDefinition.getDescription();
		return dto;
	}

	

	public static SpringTaskDTO toTaskDTO(TaskInfo task, String status, ProcessDefinition processDefinition,
			Deployment deployment) {
		SpringTaskDTO dto = new SpringTaskDTO();
		dto.setTaskId(task.getId());
		dto.setTaskName(task.getName());
		// dto.setTime(historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getStartTime().getTime());
		// dto.setVariable(task.getTaskLocalVariables());
		dto.setPdName(deployment.getName());
		dto.setVersion("V:" + processDefinition.getVersion());
		dto.setProcessInstanceId(task.getProcessInstanceId());
		dto.setStatus(status);
		dto.setTitle((String) task.getProcessVariables().get("title"));
		dto.setNodeKey(task.getTaskDefinitionKey());
		dto.setProcessDefKey(processDefinition.getKey());
		return dto;
	}
}
