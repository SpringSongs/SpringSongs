package io.github.springsongs.modules.activiti.service.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.activiti.dto.SpringProcessDTO;
import io.github.springsongs.modules.activiti.util.ActivitiUtils;

@Service
public class SpringProcessService {

	static Logger logger = LoggerFactory.getLogger(SpringActCategoryServiceImpl.class);
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;

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

	public InputStream resourceRead(String procDefId, String proInsId, String resType) throws Exception {

		if (StringUtils.isBlank(procDefId)){
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(proInsId).singleResult();
			procDefId = processInstance.getProcessDefinitionId();
		}
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();

		String resourceName = "";
		if (resType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}

		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		return resourceAsStream;
	}

	@Transactional(readOnly = false)
	public Model convertToModel(String procDefId) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
            InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                    processDefinition.getResourceName());
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

            BpmnJsonConverter converter = new BpmnJsonConverter();
            ObjectNode modelNode = converter.convertToJson(bpmnModel);
			Model model = repositoryService.newModel();
            model.setKey(processDefinition.getKey());
            model.setName(processDefinition.getResourceName());
            model.setCategory(processDefinition.getCategory());
            model.setDeploymentId(processDefinition.getDeploymentId());
            model.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(model.getKey()).count()+1)));

            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, model.getVersion());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
            model.setMetaInfo(modelObjectNode.toString());

            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), modelNode.toString().getBytes("utf-8"));
			return model;
        } catch (Exception e) {
        	logger.error(e.getMessage());
            throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
        }
	}
	
	
	public TaskDefinition getNextTaskInfo(String taskId) throws Exception {  

        ProcessDefinitionEntity processDefinitionEntity = null;  

        String id = null;  

        TaskDefinition task = null;  

        //获取流程实例Id信息   
        
        Task userTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        
        String processInstanceId = userTask.getProcessInstanceId();  

        //获取流程发布Id信息   
        String definitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();  

        processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(definitionId);  

        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();  

        //当前流程节点Id信息   
        String activitiId = execution.getActivityId();    

        //获取流程所有节点信息   
        List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();   

        //遍历所有节点信息   
        for(ActivityImpl activityImpl : activitiList){      
            id = activityImpl.getId();     
            if (activitiId.equals(id)) {
                //获取下一个节点信息   
                task = nextTaskDefinition(activityImpl, activityImpl.getId(), null, processInstanceId); 
                break;
            }
        }  
        return task;  
    }  

	private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, String processInstanceId){   

        PvmActivity ac = null;

        Object s = null;

        // 如果遍历节点为用户任务并且节点不是当前节点信息
        if ("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())) {
            // 获取该节点下一个节点信息
            TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior())
                    .getTaskDefinition();
            return taskDefinition;
        } else {
            // 获取节点所有流向线路信息
            List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
            List<PvmTransition> outTransitionsTemp = null;
            for (PvmTransition tr : outTransitions) {
                ac = tr.getDestination(); // 获取线路的终点节点
                // 如果流向线路为排他网关
                if ("exclusiveGateway".equals(ac.getProperty("type"))) {
                    outTransitionsTemp = ac.getOutgoingTransitions();

                    // 如果网关路线判断条件为空信息
                    if (StringUtils.isEmpty(elString)) {
                        // 获取流程启动时设置的网关判断条件信息
                        elString = getGatewayCondition(ac.getId(), processInstanceId);
                    }

                    // 如果排他网关只有一条线路信息
                    if (outTransitionsTemp.size() == 1) {
                        return nextTaskDefinition((ActivityImpl) outTransitionsTemp.get(0).getDestination(), activityId,
                                elString, processInstanceId);
                    } else if (outTransitionsTemp.size() > 1) { // 如果排他网关有多条线路信息
                        for (PvmTransition tr1 : outTransitionsTemp) {
                            s = tr1.getProperty("conditionText"); // 获取排他网关线路判断条件信息
                            // 判断el表达式是否成立
                            if (isCondition(ac.getId(), StringUtils.trim(s.toString()), elString)) {
                                return nextTaskDefinition((ActivityImpl) tr1.getDestination(), activityId, elString,
                                        processInstanceId);
                            }
                        }
                    }
                } else if ("userTask".equals(ac.getProperty("type"))) {
                    return ((UserTaskActivityBehavior) ((ActivityImpl) ac).getActivityBehavior()).getTaskDefinition();
                } else {
                }
            }
            return null;
        }
    }  
	

    public String getGatewayCondition(String gatewayId, String processInstanceId) {  
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
        Object object= runtimeService.getVariable(execution.getId(), gatewayId);
        return object==null? "":object.toString();  
    }  


    public boolean isCondition(String key, String el, String value) {  
        ExpressionFactory factory = new ExpressionFactoryImpl();    
        SimpleContext context = new SimpleContext();    
        context.setVariable(key, factory.createValueExpression(value, String.class));    
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);    
        return (Boolean) e.getValue(context);  
    }  
    
    public  String getNextNode(String procInstanceId){
        // 1、首先是根据流程ID获取当前任务：
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstanceId).list();
        String nextId = "";
        for (Task task : tasks) {
            RepositoryService rs = repositoryService;
            // 2、然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
            ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) rs)
                    .getDeployedProcessDefinition(task.getProcessDefinitionId());
            List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
            // 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
            String excId = task.getExecutionId();
            ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
                    .singleResult();
            String activitiId = execution.getActivityId();
            // 4、然后循环activitiList
            // 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
            for (ActivityImpl activityImpl : activitiList) {
                String id = activityImpl.getId();
                if (activitiId.equals(id)) {
                    logger.debug("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
                    List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
                    for (PvmTransition tr : outTransitions) {
                        PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
                        logger.debug("下一步任务任务：" + ac.getProperty("name"));
                        nextId = ac.getId();
                    }
                    break;
                }
            }
        }
        return nextId;
    }
}
