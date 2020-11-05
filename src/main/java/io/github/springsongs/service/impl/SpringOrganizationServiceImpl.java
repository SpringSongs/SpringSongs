package io.github.springsongs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import io.github.springsongs.domain.SpringOrganization;
import io.github.springsongs.domain.dto.SpringOrganizationDTO;
import io.github.springsongs.repo.SpringOrganizationRepo;
import io.github.springsongs.service.ISpringOrganizationService;
import io.github.springsongs.util.R;

@Service
@Transactional
public class SpringOrganizationServiceImpl implements ISpringOrganizationService {

	@Autowired
	private SpringOrganizationRepo springOrganizationDao;

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
		springOrganizationDao.deleteById(id);

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
	public void insert(SpringOrganizationDTO record) {
		SpringOrganization SpringOrganization = new SpringOrganization();
		BeanUtils.copyProperties(record, SpringOrganization);
		springOrganizationDao.save(SpringOrganization);

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
	public SpringOrganizationDTO selectByPrimaryKey(String id) {
		SpringOrganization SpringOrganization = springOrganizationDao.getOne(id);
		SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
		BeanUtils.copyProperties(SpringOrganization, springOrganizationDTO);
		return springOrganizationDTO;
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
	public void updateByPrimaryKey(SpringOrganizationDTO record) {
		SpringOrganization SpringOrganization = new SpringOrganization();
		BeanUtils.copyProperties(record, SpringOrganization);
		springOrganizationDao.save(record);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseSpringOrganizationEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringOrganizationDTO> getAllRecordByPage(SpringOrganization record, Pageable pageable) {
		Specification<SpringOrganization> specification = new Specification<SpringOrganization>() {

			@Override
			public Predicate toPredicate(Root<SpringOrganization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate receiverId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
					predicates.add(receiverId);
				}
				if (!StringUtils.isEmpty(record.getParentId())) {
					Predicate parentId = cb.equal(root.get("parentId").as(String.class), record.getParentId());
					predicates.add(parentId);
				}
				Predicate deletedStatus = cb.equal(root.get("deletedStatus").as(Boolean.class), false);
				predicates.add(deletedStatus);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = new PageRequest(currPage - 1, size);
		Page<SpringOrganization> springOrganizations = springOrganizationDao.findAll(specification, pageable);
		List<SpringOrganizationDTO> springOrganizationDTOs = new ArrayList<>();
		springOrganizations.stream().forEach(springOrganization -> {
			SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
			BeanUtils.copyProperties(springOrganization, springOrganizationDTO);
			springOrganizationDTOs.add(springOrganizationDTO);
		});
		Page<SpringOrganizationDTO> pages = new PageImpl(springOrganizationDTOs, pageable,
				springOrganizations.getTotalElements());
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
		springOrganizationDao.setDelete(ids);
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

	/**
	 * 根据上级主键查询组织机构
	 * 
	 * @param parentId
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public List<SpringOrganizationDTO> listOrganizationsByParent(String parentId) {
		List<SpringOrganization> springOrganizations=springOrganizationDao.listOrganizationByParentId(parentId);
		List<SpringOrganizationDTO> springOrganizationDTOs = new ArrayList<>();
		springOrganizations.stream().forEach(springOrganization -> {
			SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
			BeanUtils.copyProperties(springOrganization, springOrganizationDTO);
			springOrganizationDTOs.add(springOrganizationDTO);
		});
		return springOrganizationDTOs;
	}

	@Override
	public List<SpringOrganizationDTO> listAll() {
		List<SpringOrganization> springOrganizations=springOrganizationDao.listAllRecord();
		List<SpringOrganizationDTO> springOrganizationDTOs = new ArrayList<>();
		springOrganizations.stream().forEach(springOrganization -> {
			SpringOrganizationDTO springOrganizationDTO = new SpringOrganizationDTO();
			BeanUtils.copyProperties(springOrganization, springOrganizationDTO);
			springOrganizationDTOs.add(springOrganizationDTO);
		});
		return springOrganizationDTOs;
	}

}
