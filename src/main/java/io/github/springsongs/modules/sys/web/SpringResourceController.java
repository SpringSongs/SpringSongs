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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.sys.domain.SpringResource;
import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;
import io.github.springsongs.modules.sys.dto.MenuDTO;
import io.github.springsongs.modules.sys.dto.MenuRouterDTO;
import io.github.springsongs.modules.sys.dto.SpringParameterDTO;
import io.github.springsongs.modules.sys.dto.SpringResourceDTO;
import io.github.springsongs.modules.sys.dto.query.SpringResourceQuery;
import io.github.springsongs.modules.sys.service.ISpringResourceService;
import io.github.springsongs.security.util.SecurityUtils;
import io.github.springsongs.util.IpKit;

@RestController
@RequestMapping(value = "/SpringResource")
public class SpringResourceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringResourceController.class);

	@Autowired
	private ISpringResourceService springResourceService;

	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringResourceDTO> listByPage(@RequestBody SpringResourceQuery springResourceQuery,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringResourceDTO> lists = springResourceService.getAllRecordByPage(springResourceQuery, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
	public ResponseDTO<SpringParameterDTO> get(@NotEmpty(message = "id不能为空") String id) {

		SpringResource entity = springResourceService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringResourceDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springResourceService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringResourceDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springResourceService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springResourceService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/GetMenus")
	public ResponseDTO<MenuDTO> getMenus() {
		List<MenuDTO> menuList = springResourceService.ListModuleByUserId(this.getUser().getId());
		return ResponseDTO.successed(menuList, ResultCode.SELECT_SUCCESSED);
	}

	@GetMapping(value = "/GetRouters")
	public ResponseDTO<List<MenuRouterDTO>> getRouters() {
		List<MenuRouterDTO> menuRouterDTOs = springResourceService.listResourceByUserId(this.getUser().getId());
		return ResponseDTO.successed(menuRouterDTOs, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/GetMenusByParent")
	public ResponseDTO<ElementUiTreeDTO> getModuleByParentId(
			@RequestParam(value = "parentId", required = true) String parentId,
			@RequestParam(value = "systemId", required = true) String systemId) {
		List<ElementUiTreeDTO> elementUiTreeDtoList = springResourceService.getModulesByParentId(parentId, systemId);
		return ResponseDTO.successed(elementUiTreeDtoList, ResultCode.SELECT_SUCCESSED);
	}

}
