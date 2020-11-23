package io.github.springsongs.modules.activiti.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.springsongs.modules.activiti.dto.SpringProcessDTO;
import io.github.springsongs.modules.activiti.util.ActivitiUtils;

@Service
public class SpringProcessService {

	@Autowired
	private RepositoryService repositoryService;

	public Page<SpringProcessDTO> getAllRecordByPage(String category, Pageable pageable) {

		List<SpringProcessDTO> SpringProcessDTOList = new ArrayList<>();
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().latestVersion();
		if (StringUtils.isNotBlank(category)) {
			query.processDefinitionCategory(category);
		}
		query.listPage(pageable.getPageNumber(), pageable.getPageSize()).stream().forEach(processDefinition -> {
			Deployment deployment = repositoryService.createDeploymentQuery()
					.deploymentId(processDefinition.getDeploymentId()).singleResult();
			SpringProcessDTOList.add(ActivitiUtils.toProcessDTO(processDefinition, deployment));
		});
		Page<SpringProcessDTO> springProcessDTOPage = new PageImpl(SpringProcessDTOList, pageable, query.count());
		return springProcessDTOPage;
	}

	public String updateState(String state, String procDefId) {
		if (state.equals("active")) {
			repositoryService.activateProcessDefinitionById(procDefId, true, null);
		} else if (state.equals("suspend")) {
			repositoryService.suspendProcessDefinitionById(procDefId, true, null);
		}
		return state;
	}

}
