package io.github.springsongs.modules.sys.web;

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
import io.github.springsongs.modules.sys.domain.SpringResource;
import io.github.springsongs.modules.sys.dto.EasyUiMenuDTO;
import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;
import io.github.springsongs.modules.sys.dto.MenuDTO;
import io.github.springsongs.modules.sys.dto.MenuRouterDTO;
import io.github.springsongs.modules.sys.dto.SpringParameterDTO;
import io.github.springsongs.modules.sys.dto.SpringResourceDTO;
import io.github.springsongs.modules.sys.dto.query.SpringResourceQuery;
import io.github.springsongs.modules.sys.service.ISpringResourceService;
import io.github.springsongs.util.IpKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "资源管理")
@RestController
@RequestMapping(value = "/SpringResource")
public class SpringResourceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringResourceController.class);

	@Autowired
	private ISpringResourceService springResourceService;

	@ApiOperation(value = "获取资源分页列表", response = ReponseResultPageDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "springResourceQuery", dataType = "SpringResourceQuery"),
			@ApiImplicitParam(name = "pageable", dataType = "Pageable"), })
	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringResourceDTO> listByPage(@RequestBody SpringResourceQuery springResourceQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringResourceDTO> lists = springResourceService.getAllRecordByPage(springResourceQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取资源", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "String") })
	@GetMapping(value = "/Detail")
	public ResponseDTO<SpringParameterDTO> get(@NotEmpty(message = "id不能为空") String id) {

		SpringResource entity = springResourceService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "创建资源", notes = "根据SpringResourceDTO创建资源", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringAttachmentDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringResourceDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springResourceService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@ApiOperation(value = "修改资源", notes = "根据SpringAttachmentDTO修改资源", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "viewEntity", dataType = "SpringResourceDTO"),
			@ApiImplicitParam(name = "request", dataType = "HttpServletRequest"), })
	@PutMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringResourceDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springResourceService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@ApiOperation(value = "删除资源", notes = "根据List<String>对象删除资源", response = ResponseDTO.class)
	@ApiImplicitParam(dataType = "List<String>", name = "ids", value = "资源编号", required = true)
	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springResourceService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@ApiOperation(value = "获取资源菜单", notes = "获取资源菜单", response = ResponseDTO.class)
	@GetMapping(value = "/GetMenus")
	public ResponseDTO<MenuDTO> getMenus() {
		List<MenuDTO> menuList = springResourceService.ListModuleByUserId(this.getUser().getId());
		return ResponseDTO.successed(menuList, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取资源菜单路由", notes = "获取资源菜单路由", response = ResponseDTO.class)
	@GetMapping(value = "/GetRouters")
	public ResponseDTO<List<MenuRouterDTO>> getRouters() {
		List<MenuRouterDTO> menuRouterDTOs = springResourceService.listResourceByUserId(this.getUser().getId());
		return ResponseDTO.successed(menuRouterDTOs, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "获取EASYUI菜单", notes = "获取EASYUI菜单", response = ResponseDTO.class)
	@PostMapping(value = "/GetEasyUIMenu")
	public ResponseDTO<List<EasyUiMenuDTO>> getEasyUIMenu() {
		List<EasyUiMenuDTO> easyUiMenuDTO = springResourceService.listEasyUiResourceByUserId(this.getUser().getId());
		return ResponseDTO.successed(easyUiMenuDTO, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "根据上级查询资源", notes = "根据上级查询资源", response = ResponseDTO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "parentId", dataType = "String"),
			@ApiImplicitParam(name = "systemId", dataType = "String"), })
	@GetMapping(value = "/GetMenusByParent")
	public ResponseDTO<ElementUiTreeDTO> getModuleByParentId(
			@RequestParam(value = "parentId", required = true) String parentId,
			@RequestParam(value = "systemId", required = true) String systemId) {
		List<ElementUiTreeDTO> elementUiTreeDtoList = springResourceService.getModulesByParentId(parentId, systemId);
		return ResponseDTO.successed(elementUiTreeDtoList, ResultCode.SELECT_SUCCESSED);
	}

	@ApiOperation(value = "查询资源树", notes = "查询资源树", response = ResponseDTO.class)
	@GetMapping(value = "/ListAllToTree")
	public ResponseDTO<SpringResourceDTO> ListAllToTree(
			@RequestParam(value = "systemId", required = true) String systemId) {
		List<SpringResourceDTO> entitys = springResourceService.ListAllToTree(systemId);
		return ResponseDTO.successed(entitys, ResultCode.SELECT_SUCCESSED);
	}

}
