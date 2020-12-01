package io.github.springsongs.modules.activiti.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.activiti.domain.SpringActUseTask;
import io.github.springsongs.modules.activiti.dto.SpringActUseTaskDTO;
import io.github.springsongs.modules.activiti.repo.SpringActUseTaskRepo;
import io.github.springsongs.modules.activiti.service.ISpringActUseTaskService;
import io.github.springsongs.modules.job.service.impl.SpringJobHistoryServiceImpl;

@Service
public class SpringActUseTaskServiceImpl implements ISpringActUseTaskService {

	static Logger logger = LoggerFactory.getLogger(SpringJobHistoryServiceImpl.class);

	@Autowired
	private SpringActUseTaskRepo springActUseTaskRepo;

	@Autowired
	protected RepositoryService repositoryService;

	@Override
	public void deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(SpringActUseTaskDTO springActUseTaskDTO) {
		SpringActUseTask springActUseTask = new SpringActUseTask();
		BeanUtils.copyProperties(springActUseTaskDTO, springActUseTask);
		try {
			springActUseTaskRepo.save(springActUseTask);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public void insert(List<SpringActUseTaskDTO> springActUseTaskDTOList) {
		List<SpringActUseTask> springActUseTaskList = new ArrayList<>();
		springActUseTaskDTOList.stream().forEach(springActUseTaskDTO -> {
			SpringActUseTask springActUseTask = new SpringActUseTask();
			BeanUtils.copyProperties(springActUseTaskDTO, springActUseTask);
			springActUseTaskList.add(springActUseTask);
		});
		try {
			springActUseTaskRepo.saveAll(springActUseTaskList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringActUseTaskDTO selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByPrimaryKey(SpringActUseTaskDTO record) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<SpringActUseTaskDTO> getAllRecordByPage(SpringActUseTask record, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
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
	public List<SpringActUseTaskDTO> listUserTaskByProcDefKey(String procDefKey) {
		List<SpringActUseTask> springActUseTaskList = springActUseTaskRepo.listUserTaskByProcDefKey(procDefKey);
		List<SpringActUseTaskDTO> springActUseTaskDTOList = new ArrayList<>();
		springActUseTaskList.stream().forEach(springActUseTask -> {
			SpringActUseTaskDTO springActUseTaskDTO = new SpringActUseTaskDTO();
			BeanUtils.copyProperties(springActUseTask, springActUseTaskDTO);
			springActUseTaskDTOList.add(springActUseTaskDTO);
		});
		return springActUseTaskDTOList;
	}

	@Transactional
	@Override
	public void initSingleDefinition(String processDefinitionId, String procDefKey) {
		this.springActUseTaskRepo.delete(procDefKey);
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
		if (processDefinition == null) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		try {
			setSingleActivitiInfo(processDefinition);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	private void setSingleActivitiInfo(ProcessDefinition processDefinition) throws Exception {
		String proDefKey = processDefinition.getKey();
		List<SpringActUseTask> list = this.springActUseTaskRepo.listUserTaskByProcDefKey(proDefKey);
		ProcessDefinitionEntity processDef = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinition.getId());
		List<ActivityImpl> activitiList = processDef.getActivities();// 获得当前任务的所有节点
		for (ActivityImpl activity : activitiList) {
			ActivityBehavior activityBehavior = activity.getActivityBehavior();
			boolean isFound = false;
			// 是否为用户任务
			if (activityBehavior instanceof UserTaskActivityBehavior) {
				UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
				TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
				// 任务所属角色
				String taskDefKey = taskDefinition.getKey();
				Expression taskName = taskDefinition.getNameExpression();

				// 判断表中是否存在此节点
				if (list.size() != 0) {
					for (SpringActUseTask userTask : list) {
						if (taskDefKey.equals(userTask.getTaskDefKey())) {
							userTask.setProcDefKey(processDefinition.getKey());
							userTask.setProcDefName(processDefinition.getKey());
							userTask.setTaskDefKey(taskDefKey);
							userTask.setTaskName(taskName.toString());
							this.springActUseTaskRepo.save(userTask);
							isFound = true;
							break;
						}
					}

				}
				if (!isFound) {
					SpringActUseTask userTask = new SpringActUseTask();
					userTask.setProcDefKey(processDefinition.getKey());
					userTask.setProcDefName(processDefinition.getKey());
					userTask.setTaskDefKey(taskDefKey);
					userTask.setTaskName(taskName.toString());
					this.springActUseTaskRepo.save(userTask);
				}
			}
		}
	}

	@Transactional
	@Override
	public void initAllDefinition() {
		this.springActUseTaskRepo.deleteAll();
		ProcessDefinitionQuery proDefQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId()
				.desc();
		List<ProcessDefinition> processDefinitionList = proDefQuery.list();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			try {
				setSingleActivitiInfo(processDefinition);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	@Transactional
	@Override
	public void setUserToTask(String procDefKey, HttpServletRequest request) {
		List<SpringActUseTask> springActUseTaskList = springActUseTaskRepo.listUserTaskByProcDefKey(procDefKey);
		for (SpringActUseTask userTask : springActUseTaskList) {
			String taskDefKey = userTask.getTaskDefKey();
			String ids = request.getParameter(taskDefKey + "_id");
			String names = request.getParameter(taskDefKey + "_name");
			String taskType = request.getParameter(taskDefKey + "_taskType");
			userTask.setTaskType(taskType);
			userTask.setCandidateName(names);
			userTask.setCandidateIds(ids);
			this.springActUseTaskRepo.save(userTask);
		}
	}
}
