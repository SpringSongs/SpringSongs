package io.github.springsongs.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.springsongs.common.dto.ReponseResultPageDTO;
import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.common.web.BaseController;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringAttachment;
import io.github.springsongs.modules.sys.dto.SpringAttachmentDTO;
import io.github.springsongs.modules.sys.service.ISpringAttachmentService;
import io.github.springsongs.util.IpKit;

@RestController
@RequestMapping(value = "/SpringAttachment")
public class SpringAttachmentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringAttachmentController.class);

	@Autowired
	private ISpringAttachmentService springAttachmentService;

	@Value("${web.upload.path}")
	private String uploadPath;

	@PostMapping(value = "ListByPage")
	public ReponseResultPageDTO<SpringAttachmentDTO> listByPage(@RequestBody SpringAttachment viewEntity,
			@PageableDefault(page = 1, size = 20) Pageable pageable) {

		Page<SpringAttachmentDTO> lists = springAttachmentService.getAllRecordByPage(viewEntity, pageable);
		return ReponseResultPageDTO.successed(lists.getContent(), lists.getTotalElements(),
				ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Detail")
	public ResponseDTO<String> get(@NotEmpty(message = "id不能为空") String id) {
		SpringAttachment entity = springAttachmentService.selectByPrimaryKey(id);
		return ResponseDTO.successed(entity, ResultCode.SELECT_SUCCESSED);
	}

	@PostMapping(value = "/Create")
	public ResponseDTO<String> save(@RequestBody @Valid SpringAttachmentDTO viewEntity, HttpServletRequest request) {
		viewEntity.setCreatedBy(this.getUser().getUserName());
		viewEntity.setCreatedUserId(this.getUser().getId());
		viewEntity.setCreatedIp(IpKit.getRealIp(request));
		viewEntity.setCreatedOn(new Date());
		springAttachmentService.insert(viewEntity);
		return ResponseDTO.successed(null, ResultCode.SAVE_SUCCESSED);
	}

	@PostMapping(value = "/Edit")
	public ResponseDTO<String> update(@RequestBody @Valid SpringAttachmentDTO viewEntity, HttpServletRequest request) {
		viewEntity.setUpdatedOn(new Date());
		viewEntity.setUpdatedUserId(this.getUser().getId());
		viewEntity.setUpdatedBy(this.getUser().getUserName());
		viewEntity.setUpdatedIp(IpKit.getRealIp(request));
		springAttachmentService.updateByPrimaryKey(viewEntity);
		return ResponseDTO.successed(null, ResultCode.UPDATE_SUCCESSED);

	}

	@PostMapping(value = "/SetDeleted")
	public ResponseDTO<String> setDeleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springAttachmentService.setDeleted(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/Deleted")
	public ResponseDTO<String> deleted(@RequestParam(value = "ids", required = true) List<String> ids) {
		springAttachmentService.delete(ids);
		return ResponseDTO.successed(null, ResultCode.DELETE_SUCCESSED);
	}

	@PostMapping(value = "/Upload")
	public ResponseDTO<String> upload(MultipartFile file, HttpServletRequest request) {
		
		String originalFileName = file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString()
				+ originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		File f = new File(uploadPath + fileName);
		try {
			file.transferTo(f);
			return ResponseDTO.successed(fileName, ResultCode.DELETE_SUCCESSED);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		} catch (IOException e) {
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}
}
