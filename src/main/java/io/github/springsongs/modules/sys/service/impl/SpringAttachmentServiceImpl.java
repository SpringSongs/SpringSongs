package io.github.springsongs.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringAttachment;
import io.github.springsongs.modules.sys.dto.SpringAttachmentDTO;
import io.github.springsongs.modules.sys.repo.SpringAttachmentRepo;
import io.github.springsongs.modules.sys.service.ISpringAttachmentService;
import io.github.springsongs.util.Constant;

@Service
public class SpringAttachmentServiceImpl implements ISpringAttachmentService {

	static Logger logger = LoggerFactory.getLogger(SpringAttachmentCategoryServiceImpl.class);

	@Autowired
	private SpringAttachmentRepo springAttachmentDao;

	/**
	 *
	 * 物理删除
	 * 
	 * @param id
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void deleteByPrimaryKey(String id) {
		try {
			springAttachmentDao.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 *
	 * 保存
	 * 
	 * @param record
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void insert(SpringAttachmentDTO record) {
		SpringAttachment springAttachment = new SpringAttachment();
		BeanUtils.copyProperties(record, springAttachment);
		try {
			springAttachmentDao.save(record);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 *
	 * 获取单项
	 * 
	 * @param id
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public SpringAttachmentDTO selectByPrimaryKey(String id) {
		SpringAttachment springAttachment = null;
		try {
			springAttachment = springAttachmentDao.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringAttachmentDTO springAttachmentDTO = new SpringAttachmentDTO();
		BeanUtils.copyProperties(springAttachment, springAttachmentDTO);
		return springAttachmentDTO;
	}

	/**
	 *
	 * 更新
	 * 
	 * @param record
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void updateByPrimaryKey(SpringAttachmentDTO springAttachmentDTO) {
		SpringAttachment entity = springAttachmentDao.getOne(springAttachmentDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		entity.setDescription(springAttachmentDTO.getDescription());
		try {
			springAttachmentDao.save(entity);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseFileEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringAttachmentDTO> getAllRecordByPage(SpringAttachment record, Pageable pageable) {
		
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		
		Specification<SpringAttachment> specification = new Specification<SpringAttachment>() {

			@Override
			public Predicate toPredicate(Root<SpringAttachment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getFolderId())) {
					Predicate folderId = cb.equal(root.get("folderId").as(String.class), record.getFolderId());
					predicates.add(folderId);
				}
				if (!StringUtils.isEmpty(record.getDescription())) {
					Predicate description = cb.equal(root.get("description").as(String.class), record.getDescription());
					predicates.add(description);
				}
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
					predicates.add(createdUserId);
				}
				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		// return springAttachmentDao.findAll(specification, pageable);
		Page<SpringAttachment> springAttachments = springAttachmentDao.findAll(specification, pageable);
		List<SpringAttachmentDTO> springAttachmentDTOs = new ArrayList<>();
		springAttachments.stream().forEach(springAttachment -> {
			SpringAttachmentDTO springAttachmentDTO = new SpringAttachmentDTO();
			BeanUtils.copyProperties(springAttachment, springAttachmentDTO);
			springAttachmentDTOs.add(springAttachmentDTO);
		});
		Page<SpringAttachmentDTO> pages = new PageImpl(springAttachmentDTOs, pageable,
				springAttachments.getTotalElements());
		return pages;
	}

	/**
	 *
	 * 逻辑删除
	 * 
	 * @param record
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void setDeleted(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springAttachmentDao.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 *
	 * Excel批量保存
	 * 
	 * @param list
	 * @return R
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public void batchSaveExcel(List<String[]> list) {
		
	}

	@Override
	public void delete(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springAttachmentDao.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}
}
