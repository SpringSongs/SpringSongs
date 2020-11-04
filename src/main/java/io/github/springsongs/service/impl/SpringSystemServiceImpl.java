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

import io.github.springsongs.dao.SpringSystemDao;
import io.github.springsongs.domain.SpringSystem;
import io.github.springsongs.domain.dto.SpringParameterDTO;
import io.github.springsongs.domain.dto.SpringSystemDTO;
import io.github.springsongs.domain.query.SpringSystemQuery;
import io.github.springsongs.service.ISpringSystemService;
import io.github.springsongs.util.R;

@Service
@Transactional
public class SpringSystemServiceImpl implements ISpringSystemService {

	@Autowired
	private SpringSystemDao springSystemDao;

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
		springSystemDao.deleteById(id);

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
	public void insert(SpringSystemDTO record) {
		SpringSystem springSystem = new SpringSystem();
		BeanUtils.copyProperties(record, springSystem);
		springSystemDao.save(springSystem);

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
	public SpringSystemDTO selectByPrimaryKey(String id) {
		SpringSystem springSystem = springSystemDao.getOne(id);
		SpringSystemDTO springSystemDTO = new SpringSystemDTO();
		BeanUtils.copyProperties(springSystem, springSystemDTO);
		return springSystemDTO;
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
	public void updateByPrimaryKey(SpringSystemDTO record) {
		SpringSystem springSystem = new SpringSystem();
		BeanUtils.copyProperties(record, springSystem);
		springSystemDao.save(springSystem);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<SpringSystem>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringSystemDTO> getAllRecordByPage(SpringSystemQuery springSystemQuery, Pageable pageable) {
		Specification<SpringSystem> specification = new Specification<SpringSystem>() {

			@Override
			public Predicate toPredicate(Root<SpringSystem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springSystemQuery.getCode())) {
					Predicate code = cb.like(root.get("code").as(String.class), "%" + springSystemQuery.getCode());
					predicates.add(code);
				}
				if (!StringUtils.isEmpty(springSystemQuery.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class), "%" + springSystemQuery.getTitle());
					predicates.add(title);
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
		// return springSystemDao.findAll(specification, pageable);
		Page<SpringSystem> springSystems = springSystemDao.findAll(specification, pageable);
		List<SpringSystemDTO> springSystemDTOs = new ArrayList<>();
		springSystems.stream().forEach(springSystem -> {
			SpringSystemDTO springSystemDTO = new SpringSystemDTO();
			BeanUtils.copyProperties(springSystem, springSystemDTO);
			springSystemDTOs.add(springSystemDTO);
		});
		Page<SpringSystemDTO> pages = new PageImpl(springSystemDTOs, pageable, springSystems.getTotalElements());
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
		springSystemDao.setDelete(ids);
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
	public SpringSystem findByCode(String itemCode) {
		return springSystemDao.findByCode(itemCode);
	}

	@Override
	public List<SpringSystemDTO> findInIds(List<String> ids) {
		List<SpringSystem> springSystems = springSystemDao.findInIds(ids);
		List<SpringSystemDTO> springSystemDTOs = new ArrayList<>();
		springSystems.stream().forEach(springSystem -> {
			SpringSystemDTO springSystemDTO = new SpringSystemDTO();
			BeanUtils.copyProperties(springSystem, springSystemDTO);
			springSystemDTOs.add(springSystemDTO);
		});
		return springSystemDTOs;
	}

	@Override
	public int batchSaveLog(List<SpringSystem> logList) {
		return 0;
	}

	@Override
	public List<SpringSystemDTO> ListAll() {
		List<SpringSystem> springSystems = springSystemDao.listAllRecord();
		List<SpringSystemDTO> springSystemDTOs = new ArrayList<>();
		springSystems.stream().forEach(springSystem -> {
			SpringSystemDTO springSystemDTO = new SpringSystemDTO();
			BeanUtils.copyProperties(springSystem, springSystemDTO);
			springSystemDTOs.add(springSystemDTO);
		});
		return springSystemDTOs;
	}

}
