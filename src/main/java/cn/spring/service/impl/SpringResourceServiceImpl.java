package cn.spring.service.impl;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.spring.dao.SpringResourceDao;
import cn.spring.dao.SpringResourceRoleDao;
import cn.spring.domain.SpringResource;
import cn.spring.domain.SpringResourceRole;
import cn.spring.dto.ModuleRoleDto;
import cn.spring.service.ISpringResourceService;
import cn.spring.util.R;
import cn.spring.vo.ElementUiTreeVo;
import cn.spring.vo.MenuVo;

@Service
@Transactional
public class SpringResourceServiceImpl implements ISpringResourceService {
	@Autowired
	private SpringResourceDao springResourceDao;

	@Autowired
	private SpringResourceRoleDao baseModuleRoleDao;

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
	public void insert(SpringResource record) {
		springResourceDao.save(record);

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
	public SpringResource selectByPrimaryKey(String id) {
		return springResourceDao.getOne(id);
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
	public void updateByPrimaryKey(SpringResource record) {
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
	public Page<SpringResource> getAllRecordByPage(SpringResource record, Pageable pageable) {
		Specification<SpringResource> specification = new Specification<SpringResource>() {
			@Override
			public Predicate toPredicate(Root<SpringResource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getParentId())) {
					Predicate parentId = cb.equal(root.get("parentId").as(String.class), record.getParentId());
					predicates.add(parentId);
				}
				if (!StringUtils.isEmpty(record.getSystemId())) {
					Predicate systemId = cb.equal(root.get("systemId").as(String.class), record.getSystemId());
					predicates.add(systemId);
				}
				Predicate deletedFlag = cb.equal(root.get("deletedFlag").as(Boolean.class), false);
				predicates.add(deletedFlag);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		return springResourceDao.findAll(specification, pageable);
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
	public List<MenuVo> ListModuleByUserId(String userId) {
		List<SpringResource> modules = springResourceDao.listModuleByUserId(userId);
		return getSoredModules(modules);
	}

	public List<MenuVo> getSoredModules(List<SpringResource> modules) {
		List<SpringResource> parentModules = modules.stream().filter((SpringResource m) -> m.getParentId().equals("0")
				&& m.getMenuFlag() == true && m.getDeletedFlag() == false).collect(toList());
		List<SpringResource> secondModules = modules.stream().filter((SpringResource m) -> !m.getParentId().equals("0")
				&& m.getMenuFlag() == true && m.getDeletedFlag() == false).collect(toList());
		List<MenuVo> menuDtoList = this.getSecondModules(parentModules, secondModules);
		return menuDtoList;
	}

	public List<MenuVo> getSecondModules(List<SpringResource> parentModules, List<SpringResource> secondModules) {
		List<MenuVo> menuDtoList = new ArrayList<>();
		for (int i = 0; i < parentModules.size(); i++) {
			MenuVo menuDto = new MenuVo();
			menuDto.setId(parentModules.get(i).getId().toString());
			menuDto.setIcon("");
			menuDto.setLink(parentModules.get(i).getVueUrl());
			menuDto.setTitle(parentModules.get(i).getTitle());
			menuDto.setCode(parentModules.get(i).getCode());
			menuDto.setIndex(parentModules.get(i).getSortCode());
			menuDtoList.add(menuDto);
			List<MenuVo> secondMenuDtoList = new ArrayList<>();
			for (SpringResource secondModuleEntity : secondModules) {
				if (parentModules.get(i).getId().equals(secondModuleEntity.getParentId())) {
					MenuVo secondMenuDto = new MenuVo();
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
	public List<SpringResource> listByIds(List<String> ids) {
		return springResourceDao.findAllById(ids);
	}

	@Override
	public List<ElementUiTreeVo> getModulesByParentId(String parentId, String systemId) {
		List<SpringResource> baseModulesEntityList = springResourceDao.getByParentId(parentId, systemId);
		List<ElementUiTreeVo> elementUiTreeDtoList = new ArrayList<ElementUiTreeVo>();
		List<String> ids = new ArrayList<String>();
		for (SpringResource entity : baseModulesEntityList) {
			ids.add(entity.getId());
		}
		if (ids.size() > 0) {
			List<SpringResource> baseModulesEntityList1 = springResourceDao.getInParentId(ids);
			for (SpringResource entity : baseModulesEntityList) {
				ElementUiTreeVo elementUiTreeDto = new ElementUiTreeVo();
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
			baseModuleRoleDao.delete(roleId, moduleId);
		}
	}

	@Override
	public void saveModuleToRole(List<SpringResourceRole> baseModuleRoleEntityList, String roleId) {
		baseModuleRoleDao.delete(roleId);
		baseModuleRoleDao.saveAll(baseModuleRoleEntityList);
	}

	@Override
	public List<SpringResourceRole> listModulesByRoleId(String roleId) {
		return baseModuleRoleDao.listModulesByRoleId(roleId);
	}

	@Override
	public List<ModuleRoleDto> listAllRoleModules() {
		return springResourceDao.listAllRoleModules();
	}
}
