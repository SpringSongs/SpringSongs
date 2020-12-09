package io.github.springsongs.modules.sys.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import io.github.springsongs.modules.sys.domain.SpringResource;
import io.github.springsongs.modules.sys.domain.SpringResourceRole;
import io.github.springsongs.modules.sys.dto.ElementUiTreeDTO;
import io.github.springsongs.modules.sys.dto.MenuDTO;
import io.github.springsongs.modules.sys.dto.MenuRouterDTO;
import io.github.springsongs.modules.sys.dto.MenuRouterDTO.Meta;
import io.github.springsongs.modules.sys.dto.MenuRouterTreeDTO;
import io.github.springsongs.modules.sys.dto.ResourceRoleDTO;
import io.github.springsongs.modules.sys.dto.SpringResourceDTO;
import io.github.springsongs.modules.sys.dto.SpringResourceTreeDTO;
import io.github.springsongs.modules.sys.dto.query.SpringResourceQuery;
import io.github.springsongs.modules.sys.repo.SpringResourceRepo;
import io.github.springsongs.modules.sys.repo.SpringResourceRoleRepo;
import io.github.springsongs.modules.sys.service.ISpringResourceService;
import io.github.springsongs.util.Constant;

@Service
public class SpringResourceServiceImpl implements ISpringResourceService {
	static Logger logger = LoggerFactory.getLogger(SpringResourceServiceImpl.class);
	@Autowired
	private SpringResourceRepo springResourceDao;

	@Autowired
	private SpringResourceRoleRepo springResourceRoleDao;

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
			springResourceDao.deleteById(id);
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
	public void insert(SpringResourceDTO record) {
		SpringResource springResource = new SpringResource();
		BeanUtils.copyProperties(record, springResource);
		try {
			springResourceDao.save(springResource);
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
	public SpringResourceDTO selectByPrimaryKey(String id) {
		SpringResource springResource = null;
		try {
			springResource = springResourceDao.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringResourceDTO springResourceDTO = new SpringResourceDTO();
		BeanUtils.copyProperties(springResource, springResourceDTO);
		return springResourceDTO;
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
	public void updateByPrimaryKey(SpringResourceDTO springResourceDTO) {
		SpringResource entity = springResourceDao.getOne(springResourceDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else if (!entity.getEnableEdit()) {
			throw new SpringSongsException(ResultCode.INFO_CAN_NOT_EDIT);
		} else {
			entity.setCode(springResourceDTO.getCode());
			entity.setTitle(springResourceDTO.getTitle());
			entity.setMenuFlag(springResourceDTO.getMenuFlag());
			entity.setVueUrl(springResourceDTO.getVueUrl());
			entity.setVueIcon(springResourceDTO.getVueIcon());
			entity.setAngularUrl(springResourceDTO.getAngularUrl());
			entity.setParentId(springResourceDTO.getParentId());
			entity.setParentName(springResourceDTO.getParentName());
			entity.setSortCode(springResourceDTO.getSortCode());
			entity.setShowStatus(springResourceDTO.getShowStatus());
			entity.setEnableEdit(springResourceDTO.getEnableEdit());
			entity.setEnableDelete(springResourceDTO.getEnableDelete());
			try {
				springResourceDao.save(entity);
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
	 * @return Page<BaseModuleEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringResourceDTO> getAllRecordByPage(SpringResourceQuery springResourceQuery, Pageable pageable) {

		if (pageable.getPageSize() > Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}

		Specification<SpringResource> specification = new Specification<SpringResource>() {
			@Override
			public Predicate toPredicate(Root<SpringResource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springResourceQuery.getParentId())) {
					Predicate parentId = cb.equal(root.get("parentId").as(String.class),
							springResourceQuery.getParentId());
					predicates.add(parentId);
				}
				if (!StringUtils.isEmpty(springResourceQuery.getSystemId())) {
					Predicate systemId = cb.equal(root.get("systemId").as(String.class),
							springResourceQuery.getSystemId());
					predicates.add(systemId);
				}
				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.asc(root.get("sortCode").as(Integer.class)),cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		// return springResourceDao.findAll(specification, pageable);
		Page<SpringResource> springResources = springResourceDao.findAll(specification, pageable);
		List<SpringResourceDTO> springResourceDTOs = new ArrayList<>();
		springResources.stream().forEach(springResource -> {
			SpringResourceDTO springResourceDTO = new SpringResourceDTO();
			BeanUtils.copyProperties(springResource, springResourceDTO);
			springResourceDTOs.add(springResourceDTO);
		});
		Page<SpringResourceDTO> pages = new PageImpl(springResourceDTOs, pageable, springResources.getTotalElements());
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
		List<SpringResource> entityList = springResourceDao.findAllById(ids);
		for (SpringResource entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springResourceDao.setDelete(ids);
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
	public List<MenuDTO> ListModuleByUserId(String userId) {
		List<SpringResource> modules = springResourceDao.listModuleByUserId(userId);
		return getSoredModules(modules);
	}

	public List<MenuDTO> getSoredModules(List<SpringResource> modules) {
		List<SpringResource> parentModules = modules.stream().filter((SpringResource m) -> m.getParentId().equals("0")
				&& m.getMenuFlag() == true && m.getDeletedStatus() == false).collect(toList());
		List<SpringResource> secondModules = modules.stream().filter((SpringResource m) -> !m.getParentId().equals("0")
				&& m.getMenuFlag() == true && m.getDeletedStatus() == false).collect(toList());
		List<MenuDTO> menuDtoList = this.getSecondModules(parentModules, secondModules);
		return menuDtoList;
	}

	public List<MenuDTO> getSecondModules(List<SpringResource> parentModules, List<SpringResource> secondModules) {
		List<MenuDTO> menuDtoList = new ArrayList<>();
		for (int i = 0; i < parentModules.size(); i++) {
			MenuDTO menuDto = new MenuDTO();
			menuDto.setId(parentModules.get(i).getId().toString());
			menuDto.setIcon("");
			menuDto.setLink(parentModules.get(i).getVueUrl());
			menuDto.setTitle(parentModules.get(i).getTitle());
			menuDto.setCode(parentModules.get(i).getCode());
			menuDto.setIndex(parentModules.get(i).getSortCode());
			menuDtoList.add(menuDto);
			List<MenuDTO> secondMenuDtoList = new ArrayList<>();
			for (SpringResource secondModuleEntity : secondModules) {
				if (parentModules.get(i).getId().equals(secondModuleEntity.getParentId())) {
					MenuDTO secondMenuDto = new MenuDTO();
					secondMenuDto.setId(secondModuleEntity.getId().toString());
					secondMenuDto.setIcon("");
					secondMenuDto.setLink(secondModuleEntity.getVueUrl());
					secondMenuDto.setTitle(secondModuleEntity.getTitle());
					secondMenuDto.setCode(secondModuleEntity.getCode());
					secondMenuDto.setIndex(secondModuleEntity.getSortCode());
					secondMenuDtoList.add(secondMenuDto);
				}
			}
			menuDtoList.get(i).setMenuDtoList(secondMenuDtoList);
		}

		return menuDtoList;
	}

	@Override
	public void delete(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		} else if (ids.size() > Constant.MAX_ITEM_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		List<SpringResource> entityList = springResourceDao.findAllById(ids);
		for (SpringResource entity : entityList) {
			if (entity.getEnableDelete() == false) {
				throw new SpringSongsException(ResultCode.INFO_CAN_NOT_DELETE);
			}
		}
		try {
			springResourceDao.setDelete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.SYSTEM_ERROR);
		}
	}

	@Override
	public List<SpringResourceDTO> listByIds(List<String> ids) {
		List<SpringResource> springResources = springResourceDao.findAllById(ids);
		List<SpringResourceDTO> springResourceDTOs = new ArrayList<>();
		springResources.stream().forEach(springResource -> {
			SpringResourceDTO springResourceDTO = new SpringResourceDTO();
			BeanUtils.copyProperties(springResource, springResourceDTO);
			springResourceDTOs.add(springResourceDTO);
		});
		return springResourceDTOs;
	}

	@Override
	public List<ElementUiTreeDTO> getModulesByParentId(String parentId, String systemId) {
		List<SpringResource> baseModulesEntityList = springResourceDao.getByParentId(parentId, systemId);
		List<ElementUiTreeDTO> elementUiTreeDtoList = new ArrayList<ElementUiTreeDTO>();
		List<String> ids = new ArrayList<String>();
		for (SpringResource entity : baseModulesEntityList) {
			ids.add(entity.getId());
		}
		if (ids.size() > 0) {
			List<SpringResource> baseModulesEntityList1 = springResourceDao.getInParentId(ids);
			for (SpringResource entity : baseModulesEntityList) {
				ElementUiTreeDTO elementUiTreeDto = new ElementUiTreeDTO();
				elementUiTreeDto.setId(entity.getId());
				elementUiTreeDto.setLeaf(true);
				elementUiTreeDto.setName(entity.getTitle());
				for (SpringResource entity1 : baseModulesEntityList1) {
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

	@Override
	public void delete(Map map) {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String roleId = entry.getKey();
			String moduleId = entry.getValue();
			springResourceRoleDao.delete(roleId, moduleId);
		}
	}

	@Override
	@Transactional
	public void saveModuleToRole(List<SpringResourceRole> baseModuleRoleEntityList, String roleId) {
		springResourceRoleDao.delete(roleId);
		springResourceRoleDao.saveAll(baseModuleRoleEntityList);
	}

	@Override
	public List<SpringResourceRole> listModulesByRoleId(String roleId) {
		return springResourceRoleDao.listModulesByRoleId(roleId);
	}

	@Override
	public List<ResourceRoleDTO> listAllRoleModules(List<String> roleCode) {
		return springResourceDao.listAllRoleModules(roleCode);
	}

	@Override
	public List<MenuRouterDTO> listResourceByUserId(String userId) {
		// List<MenuRouterTreeDTO> menuRouterTreeDTOList=new ArrayList<>();
		List<SpringResource> springResourceList = springResourceDao.listModuleByUserId(userId);
		final List<MenuRouterDTO> menuRouterDTOList = new ArrayList<>();
		springResourceList.stream().forEach(springResource -> {
			MenuRouterDTO node = new MenuRouterDTO();
			node.setId(springResource.getId());
			node.setParentId(springResource.getParentId());
			Meta metaTitle = new Meta();
			metaTitle.setTitle(springResource.getTitle());
			metaTitle.setIcon(springResource.getVueIcon());
			node.setMeta(metaTitle);
			node.setPath(springResource.getCode());
			node.setName(springResource.getCode());
			node.setComponent(springResource.getVueUrl());
			node.setRedirect("");
			node.setHidden(springResource.getShowStatus());
			menuRouterDTOList.add(node);
		});
		MenuRouterTreeDTO menuRouterTreeDTO = new MenuRouterTreeDTO(menuRouterDTOList);
		List<MenuRouterDTO> menuRouterDTOListTree = new ArrayList<>();
		menuRouterDTOListTree = menuRouterTreeDTO.builTree();
		return menuRouterDTOListTree;
	}

	@Override
	public List<SpringResourceDTO> ListAllToTree(String systemCode) {
		List<SpringResource> springResourceList = springResourceDao.listAllResources(systemCode);
		final List<SpringResourceDTO> springResourceDTOList = new ArrayList<>();
		springResourceList.stream().forEach(springResource -> {
			SpringResourceDTO springResourceDTO = new SpringResourceDTO();
			BeanUtils.copyProperties(springResource, springResourceDTO);
			springResourceDTOList.add(springResourceDTO);
		});
		SpringResourceTreeDTO springResourceTreeDTO = new SpringResourceTreeDTO(springResourceDTOList);
		List<SpringResourceDTO> springResourceDTOTreeList = new ArrayList<>();
		springResourceDTOTreeList = springResourceTreeDTO.builTree();
		return springResourceDTOTreeList;
	}
}
