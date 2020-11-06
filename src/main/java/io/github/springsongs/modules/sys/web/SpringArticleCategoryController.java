package io.github.springsongs.modules.sys.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;
import io.github.springsongs.modules.sys.dto.SpringArticleCategoryDTO;
import io.github.springsongs.modules.sys.dto.query.SpringArticleCategoryQuery;
import io.github.springsongs.modules.sys.service.ISpringArticleCategoryService;
import io.github.springsongs.util.IpKit;

@RestController
@RequestMapping(value = "/SpringArticleCategory")
public class SpringArticleCategoryController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringArticleCategoryController.class);

	@Autowired
	private ISpringArticleCategoryService springArticleCategoryService;

	@PostMapping(value = "/ListByPage")
	public ReponseResultPageDTO<SpringArticleCategoryDTO> getPage(@RequestBody SpringArticleCategoryQuery viewEntity,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {
		Page<SpringArticleCategoryDTO> lists = springArticleCategoryService.getAllRecordByPage(viewEntity, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
	public ResponseDTO<String> get(String id) {
		SpringArticleCategoryDTO entity = springArticleCategoryService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody SpringArticleCategoryDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springArticleCategoryService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody SpringArticleCategoryDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springArticleCategoryService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);
	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springArticleCategoryService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/GetCategorysByParent")
	public ResponseDTO<ElementUiTreeDTO> getModuleByParentId(
			@RequestParam(value = "parentId", required = true) String parentId) {
		
		List<ElementUiTreeDTO> elementUiTreeDtoList = springArticleCategoryService.getCategoryByParentId(parentId);

		return ResponseDTO.successed(elementUiTreeDtoList, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/listAllRecord")
	public ResponseDTO<SpringArticleCategoryDTO> listAllRecord() {
		List<SpringArticleCategoryDTO> entitys = springArticleCategoryService.listAll();
		return ResponseDTO.successed(entitys, ResultCode.DELETE_SUCCESSED);
	}
}
