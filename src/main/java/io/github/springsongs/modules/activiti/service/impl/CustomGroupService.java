package io.github.springsongs.modules.activiti.service.impl;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.activiti.engine.identity.Group;
//import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
//import org.activiti.engine.impl.persistence.entity.GroupEntity;
//import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
//import org.activiti.engine.impl.persistence.entity.GroupEntityManagerImpl;
//import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import io.github.springsongs.modules.sys.dto.RoleCodeDTO;
//import io.github.springsongs.modules.sys.repo.SpringRoleRepo;

//@Component
//public class CustomGroupService extends GroupEntityManagerImpl {
//
//	@Autowired
//	private SpringRoleRepo springRoleRepo;
//
//	public CustomGroupService(ProcessEngineConfigurationImpl processEngineConfiguration,
//			GroupDataManager groupDataManager) {
//		super(processEngineConfiguration, groupDataManager);
//	}
//
//	@Override
//	public List<Group> findGroupsByUser(String userId) {
//		List<RoleCodeDTO> springRoles = springRoleRepo.getRolesByUserId(userId);
//		List<Group> groups = new ArrayList<Group>();
//		GroupEntity groupEntity = null;
//		springRoles.stream().forEach(springRole->{
//			groupEntity = new GroupEntityImpl();
//            groupEntity.setRevision(1);
//            groupEntity.setType("assignment");
//            groupEntity.setName(springRole.getCode());
//            groups.add(groupEntity);
//		});
//	}
//
//}
