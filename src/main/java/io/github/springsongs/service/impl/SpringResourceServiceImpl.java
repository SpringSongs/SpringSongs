package io.github.springsongs.service.impl;

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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.github.springsongs.domain.SpringParameter;
import io.github.springsongs.domain.SpringResource;
import io.github.springsongs.domain.SpringResourceRole;
import io.github.springsongs.domain.dto.ElementUiTreeDTO;
import io.github.springsongs.domain.dto.MenuDTO;
import io.github.springsongs.domain.dto.ResourceRoleDTO;
import io.github.springsongs.domain.dto.SpringParameterDTO;
import io.github.springsongs.domain.dto.SpringResourceDTO;
import io.github.springsongs.domain.query.SpringResourceQueryBO;
import io.github.springsongs.repo.SpringResourceRepo;
import io.github.springsongs.repo.SpringResourceRoleRepo;
import io.github.springsongs.service.ISpringResourceService;
import io.github.springsongs.util.R;

@Service

public class SpringResourceServiceImpl implements ISpringResourceService {
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
		springResourceDao.deleteById(id);

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
		springResourceDao.save(springResource);
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
		SpringResource springResource = springResourceDao.getOne(id);
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
	public void updateByPrimaryKey(SpringResourceDTO record) {
		SpringResource springResource = new SpringResource();
		BeanUtils.copyProperties(record, springResource);
		springResourceDao.save(record);
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
	public Page<SpringResourceDTO> getAllRecordByPage(SpringResourceQueryBO springResourceQuery, Pageable pageable) {
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
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		//return springResourceDao.findAll(specification, pageable);
		Page<SpringResource> springResources = springResourceDao.findAll(specification, pageable);
		List<SpringResourceDTO> springResourceDTOs = new ArrayList<>();
		springResources.stream().forEach(springResource -> {
			SpringResourceDTO springResourceDTO = new SpringResourceDTO();
			BeanUtils.copyProperties(springResource, springResourceDTO);
			springResourceDTOs.add(springResourceDTO);
		});
		Page<SpringResourceDTO> pages = new PageImpl(springResourceDTOs, pageable,
				springResources.getTotalElements());
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
		springResourceDao.setDelete(ids);
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
	public R batchSaveExcel(List<String[]> list) {
		return new R();
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
		springResourceDao.deleteAll();
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
	public List<ResourceRoleDTO> listAllRoleModules() {
		return springResourceDao.listAllRoleModules();
	}
}
