package io.github.springsongs.modules.sys.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.sys.domain.SpringResourceRole;
import io.github.springsongs.modules.sys.domain.SpringUserRole;
import io.github.springsongs.modules.sys.dto.SpringRoleDTO;
import io.github.springsongs.modules.sys.dto.query.SpringRoleQuery;
import io.github.springsongs.modules.sys.service.ISpringResourceService;
import io.github.springsongs.modules.sys.service.ISpringRoleService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "角色管理")
@RestController
@RequestMapping(value = "/SpringRole")
public class SpringRoleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringRoleController.class);

	@Autowired
	private ISpringRoleService springRoleService;

	@Autowired
	private ISpringResourceService springResourceService;

	@ApiOperation(value = "获取角色分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "springRoleQuery", dataType = "SpringRoleQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringRoleDTO> listByPage(@RequestBody SpringRoleQuery springRoleQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringRoleDTO> lists = springRoleService.getAllRecordByPage(springRoleQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "根据用户查询角色分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", dataType = "String"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "ListByUserId/{userId}")
	public ReponseResultPageDTO<SpringRoleDTO> listByUserId(
			@PathVariable(value = "userId", required = true) String userId,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringRoleDTO> lists = springRoleService.ListRoleByUserId(userId, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取角色", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<SpringRoleDTO> get(@NotEmpty(message = "id不能为空") String id) {
		SpringRoleDTO entity = springRoleService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建角色", notes = "根据SpringRoleDTO附件创建角色", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringRoleDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringRoleDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springRoleService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改角色", notes = "根据SpringRoleDTO修改附件", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringRoleDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringRoleDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springRoleService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除角色", notes = "根据List<String>对象删除角色", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "角色编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springRoleService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "删除角色", notes = "根据List<String>对象删除角色", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "角色编号", required = true)
	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springRoleService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "分配角色用户", notes = "分配用户角色", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleId", dataType = "String"),
			@ApiImplicitParam(name = "userIds", dataType = "List<String>"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest") })
	@PostMapping(value = "/SetUsers/{roleId}")
	public ResponseDTO<String> setUsers(@PathVariable(value = "roleId", required = true) String roleId,
			@RequestParam(value = "ids", required = true) List<String> userIds, HttpServletRequest request) {
		List<SpringUserRole> baseUserRoleEntityList = new ArrayList<SpringUserRole>();
		for (String str : userIds) {
			SpringUserRole entity = new SpringUserRole();
			entity.setRoleId(roleId);
			entity.setUserId(str);
			entity.setCreatedBy(this.getUser().getUserName());
			entity.setCreatedUserId(this.getUser().getId());
			entity.setCreatedIp(IpKit.getRealIp(request));
			entity.setCreatedOn(new Date());
			baseUserRoleEntityList.add(entity);
		}
		springRoleService.saveUserToRole(baseUserRoleEntityList, roleId);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "删除角色用户", notes = "删除用户角色", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleId", dataType = "String"),
			@ApiImplicitParam(name = "userIds", dataType = "List<String>"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest") })
	@PostMapping(value = "/DeleteUsers/{roleId}")
	public ResponseDTO<String> deleteUsers(@PathVariable(value = "roleId", required = true) String roleId,
			@RequestParam(value = "ids", required = true) List<String> userIds, HttpServletRequest request) {
		springRoleService.deleteUserFromRole(userIds, roleId);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "分配资源角色权限", notes = "分配资源角色权限", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleId", dataType = "String"),
			@ApiImplicitParam(name = "moduleIds", dataType = "List<String>"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest") })
	@PostMapping(value = "/SetAuthority/{roleId}")
	public ResponseDTO<String> setAuthority(@PathVariable(value = "roleId", required = true) String roleId,
			@RequestParam(value = "moduleIds", required = true) List<String> moduleIds, HttpServletRequest request) {
		List<SpringResourceRole> baseModuleRoleEntityList = new ArrayList<SpringResourceRole>();
		for (String str : moduleIds) {
			SpringResourceRole entity = new SpringResourceRole();
			entity.setRoleId(roleId);
			entity.setModuleId(str);
			entity.setCreatedBy(this.getUser().getUserName());
			entity.setCreatedUserId(this.getUser().getId());
			entity.setCreatedIp(IpKit.getRealIp(request));
			entity.setCreatedOn(new Date());
			baseModuleRoleEntityList.add(entity);
		}
		springResourceService.saveModuleToRole(baseModuleRoleEntityList, roleId);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "分配资源角色权限", notes = "分配资源角色权限", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleId", dataType = "String"),
			@ApiImplicitParam(name = "moduleId", dataType = "String"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest") })
	@PostMapping(value = "/SetAuthority")
	public ResponseDTO<String> setAuthorityRoleIdAndModuleId(
			@RequestParam(value = "roleId", required = true) String roleId,
			@RequestParam(value = "moduleId", required = true) String moduleId, HttpServletRequest request) {
		springResourceService.saveModuleToRole(moduleId, roleId);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "删除资源角色权限", notes = "分配资源角色", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleId", dataType = "String"),
			@ApiImplicitParam(name = "moduleId", dataType = "String"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest") })
	@PostMapping(value = "/DeleteByRoleIdAndModuleId")
	public ResponseDTO<String> deleteByRoleIdAndModuleId(@RequestParam(value = "roleId", required = true) String roleId,
			@RequestParam(value = "moduleId", required = true) String moduleId, HttpServletRequest request) {
		springResourceService.deleteByRoleIdAndModuleId(moduleId, roleId);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "根据角色查资源", notes = "根据roleId对象查资源", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "String", name = "roleId", value = "角色编号", required = true)
	@PostMapping(value = "/ListAuthority/{roleId}")
	public ResponseDTO<List<String>> listAuthority(@PathVariable(value = "roleId", required = true) String roleId) {

		List<SpringResourceRole> baseModuleRoleEntityList = springResourceService.listModulesByRoleId(roleId);
		List<String> moduleIds = new ArrayList<String>();
		baseModuleRoleEntityList.stream().forEach(item -> {
			moduleIds.add(item.getModuleId());
		});
		return ResponseDTO.successed(moduleIds, ResultCode.SELECT_SUCCESSED);
	}
}
