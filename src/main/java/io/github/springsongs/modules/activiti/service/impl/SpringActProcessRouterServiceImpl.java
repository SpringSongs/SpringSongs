package io.github.springsongs.modules.activiti.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.activiti.domain.SpringActProcessRouter;
import io.github.springsongs.modules.activiti.dto.SpringActProcessRouterDTO;
import io.github.springsongs.modules.activiti.repo.SpringActProcessRouterRepo;
import io.github.springsongs.modules.activiti.service.ISpringActProcessRouterService;

@Service
public class SpringActProcessRouterServiceImpl implements ISpringActProcessRouterService {

	static Logger logger = LoggerFactory.getLogger(SpringActCategoryServiceImpl.class);

	@Autowired
	private SpringActProcessRouterRepo springActProcessRouterRepo;

	@Override
	public void deleteByPrimaryKey(String id) {
		springActProcessRouterRepo.deleteById(id);
	}

	@Override
	public void insert(SpringActProcessRouterDTO springActProcessRouterDTO) {
		SpringActProcessRouter springActProcessRouter = new SpringActProcessRouter();
		BeanUtils.copyProperties(springActProcessRouterDTO, springActProcessRouter);
		try {
			springActProcessRouterRepo.save(springActProcessRouter);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public SpringActProcessRouterDTO selectByPrimaryKey(String id) {
		SpringActProcessRouter springActProcessRouter = springActProcessRouterRepo.getOne(id);
		if (null == springActProcessRouter) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringActProcessRouterDTO springActProcessRouterDTO = new SpringActProcessRouterDTO();
		BeanUtils.copyProperties(springActProcessRouter, springActProcessRouterDTO);
		return springActProcessRouterDTO;
	}

	@Override
	public void updateByPrimaryKey(SpringActProcessRouterDTO springActProcessRouterDTO) {
		SpringActProcessRouter springActProcessRouter = springActProcessRouterRepo
				.getOne(springActProcessRouterDTO.getId());
		if (null == springActProcessRouter) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		try {
			SpringActProcessRouter springActProcessRouterDO = new SpringActProcessRouter();
			BeanUtils.copyProperties(springActProcessRouterDTO, springActProcessRouterDO);
			springActProcessRouterRepo.save(springActProcessRouterDO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public Page<SpringActProcessRouterDTO> getAllRecordByPage(SpringActProcessRouter record, Pageable pageable) {
		return null;
	}

	@Override
	public void setDeleted(List<String> ids) {

	}

	@Override
	public void batchSaveExcel(List<String[]> list) {

	}

	@Override
	public SpringActProcessRouterDTO findSpringActProcessRouterByProcDefKey(String procDefKey) {
		SpringActProcessRouter springActProcessRouter = springActProcessRouterRepo
				.findSpringActProcessRouterByProcDefKey(procDefKey);
		if (null == springActProcessRouter) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringActProcessRouterDTO springActProcessRouterDTO = new SpringActProcessRouterDTO();
		BeanUtils.copyProperties(springActProcessRouter, springActProcessRouterDTO);
		return springActProcessRouterDTO;
	}

}
