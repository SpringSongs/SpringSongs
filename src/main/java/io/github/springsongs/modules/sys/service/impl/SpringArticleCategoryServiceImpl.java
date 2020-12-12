package io.github.springsongs.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import io.github.springsongs.modules.sys.domain.SpringArticleCategory;
import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;
import io.github.springsongs.modules.sys.dto.SpringArticleCategoryDTO;
import io.github.springsongs.modules.sys.dto.SpringArticleCategoryTreeDTO;
import io.github.springsongs.modules.sys.dto.SpringResourceDTO;
import io.github.springsongs.modules.sys.dto.query.SpringArticleCategoryQuery;
import io.github.springsongs.modules.sys.repo.SpringArticleCategoryRepo;
import io.github.springsongs.modules.sys.service.ISpringArticleCategoryService;
import io.github.springsongs.util.Constant;

@Service
public class SpringArticleCategoryServiceImpl implements ISpringArticleCategoryService {

	static Logger logger = LoggerFactory.getLogger(SpringArticleCategoryServiceImpl.class);

	@Autowired
	private SpringArticleCategoryRepo springArticleCategoryDao;

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
			springArticleCategoryDao.deleteById(id);
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
	public void insert(SpringArticleCategoryDTO record) {
		SpringArticleCategory springArticleCategory = new SpringArticleCategory();
		if (StringUtils.isEmpty(record.getParentId())) {
			record.setParentId("0");
		}
		BeanUtils.copyProperties(record, springArticleCategory);
		try {
			springArticleCategoryDao.save(springArticleCategory);
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
	public SpringArticleCategoryDTO selectByPrimaryKey(String id) {
		SpringArticleCategory springArticleCategory = null;
		try {
			springArticleCategory = springArticleCategoryDao.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringArticleCategoryDTO springArticleCategoryDTO = new SpringArticleCategoryDTO();
		BeanUtils.copyProperties(springArticleCategory, springArticleCategoryDTO);
		return springArticleCategoryDTO;
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
	public void updateByPrimaryKey(SpringArticleCategoryDTO springArticleCategoryDTO) {
		SpringArticleCategory entity = springArticleCategoryDao.getOne(springArticleCategoryDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setParentId(springArticleCategoryDTO.getParentId());
			entity.setCode(springArticleCategoryDTO.getCode());
			entity.setTitle(springArticleCategoryDTO.getTitle());
			entity.setKeywords(springArticleCategoryDTO.getKeywords());
			entity.setDescription(springArticleCategoryDTO.getDescription());
			entity.setSortOrder(springArticleCategoryDTO.getSortOrder());
			entity.setVersion(springArticleCategoryDTO.getVersion());
			try {
				springArticleCategoryDao.save(entity);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
			}
		}
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseSpringArticleCategoryEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringArticleCategoryDTO> getAllRecordByPage(SpringArticleCategoryQuery record, Pageable pageable) {
		if (pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		Specification<SpringArticleCategory> specification = new Specification<SpringArticleCategory>() {
			@Override
			public Predicate toPredicate(Root<SpringArticleCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getParentId())) {
					Predicate parentId = cb.equal(root.get("parentId").as(String.class), record.getParentId());
					predicates.add(parentId);
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
				query.orderBy(cb.asc(root.get("sortOrder").as(Integer.class)),
						cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		Page<SpringArticleCategory> springAritlceCategorys = springArticleCategoryDao.findAll(specification, pageable);
		List<SpringArticleCategoryDTO> springArticleCategoryDTOs = new ArrayList<>();
		springAritlceCategorys.stream().forEach(springAritlceCategory -> {
			SpringArticleCategoryDTO springArticleCategoryDTO = new SpringArticleCategoryDTO();
			BeanUtils.copyProperties(springAritlceCategory, springArticleCategoryDTO);
			springArticleCategoryDTOs.add(springArticleCategoryDTO);
		});
		Page<SpringArticleCategoryDTO> pages = new PageImpl(springArticleCategoryDTOs, pageable,
				springAritlceCategorys.getTotalElements());
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
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		for (String id : ids) {
			List<SpringArticleCategory> entitys = springArticleCategoryDao.getByParentId(id);
			if (!entitys.isEmpty()) {
				throw new SpringSongsException(ResultCode.HASED_CHILD_IDS_CANNOT_DELETE);
			}
		}
		try {
			springArticleCategoryDao.setDelete(ids);
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
	public List<ElementUiTreeDTO> getCategoryByParentId(String parentId) {
		if (StringUtils.isEmpty(parentId)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		List<SpringArticleCategory> baseCategoryEntityList = springArticleCategoryDao.getByParentId(parentId);
		List<ElementUiTreeDTO> elementUiTreeDtoList = new ArrayList<ElementUiTreeDTO>();
		List<String> ids = new ArrayList<String>();
		for (SpringArticleCategory entity : baseCategoryEntityList) {
			ids.add(entity.getId());
		}
		if (ids.size() > 0) {
			List<SpringArticleCategory> baseCategoryEntityList1 = springArticleCategoryDao.getInParentId(ids);
			for (SpringArticleCategory entity : baseCategoryEntityList) {
				ElementUiTreeDTO elementUiTreeDto = new ElementUiTreeDTO();
				elementUiTreeDto.setId(entity.getId());
				elementUiTreeDto.setLeaf(true);
				elementUiTreeDto.setName(entity.getTitle());
				for (SpringArticleCategory entity1 : baseCategoryEntityList1) {
					if (entity.getId().equals(entity1.getParentId())) {
						elementUiTreeDto.setLeaf(false);
						break;
					}
				}
				elementUiTreeDtoList.add(elementUiTreeDto);
			}
		}
		return elementUiTreeDtoList;
	}

	/**
	 * 根据上级查找下级
	 */
	@Override
	public List<SpringArticleCategoryDTO> getByParentId(String parentId) {
		List<SpringArticleCategory> springArticleCategorys = springArticleCategoryDao.getByParentId(parentId);
		List<SpringArticleCategoryDTO> springArticleCategoryDTOs = new ArrayList<>();
		springArticleCategorys.stream().forEach(springArticleCategory -> {
			SpringArticleCategoryDTO springArticleCategoryDTO = new SpringArticleCategoryDTO();
			BeanUtils.copyProperties(springArticleCategory, springArticleCategoryDTO);
			springArticleCategoryDTOs.add(springArticleCategoryDTO);
		});
		return springArticleCategoryDTOs;
	}

	/**
	 * 查询全部
	 */
	@Override
	public List<SpringArticleCategoryDTO> listAll() {
		List<SpringArticleCategory> springArticleCategorys = springArticleCategoryDao.listAllRecord();
		List<SpringArticleCategoryDTO> springArticleCategoryDTOs = new ArrayList<>();
		springArticleCategorys.stream().forEach(springArticleCategory -> {
			SpringArticleCategoryDTO springArticleCategoryDTO = new SpringArticleCategoryDTO();
			BeanUtils.copyProperties(springArticleCategory, springArticleCategoryDTO);
			springArticleCategoryDTOs.add(springArticleCategoryDTO);
		});
		return springArticleCategoryDTOs;
	}

	@Override
	public List<SpringArticleCategoryDTO> ListAllToTree() {
		List<SpringArticleCategoryDTO> springArticleCategoryDTOList = this.listAll();
		SpringArticleCategoryTreeDTO springArticleCategoryTreeDTO = new SpringArticleCategoryTreeDTO(
				springArticleCategoryDTOList);
		springArticleCategoryDTOList = springArticleCategoryTreeDTO.builTree();
		return springArticleCategoryDTOList;
	}

}
