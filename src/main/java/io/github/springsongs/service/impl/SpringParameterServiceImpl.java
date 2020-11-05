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

import io.github.springsongs.domain.SpringParameter;
import io.github.springsongs.domain.dto.SpringParameterDTO;
import io.github.springsongs.domain.query.SpringParameterQueryBO;
import io.github.springsongs.repo.SpringParameterRepo;
import io.github.springsongs.service.ISpringParameterService;
import io.github.springsongs.util.R;

@Service
@Transactional
public class SpringParameterServiceImpl implements ISpringParameterService {

	@Autowired
	private SpringParameterRepo springParameterDao;

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
		springParameterDao.deleteById(id);

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
	public void insert(SpringParameterDTO record) {
		SpringParameter springParameter = new SpringParameter();
		BeanUtils.copyProperties(record, springParameter);
		springParameterDao.save(springParameter);

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
	public SpringParameterDTO selectByPrimaryKey(String id) {
		SpringParameter springParameter = springParameterDao.getOne(id);
		SpringParameterDTO springParameterDTO = new SpringParameterDTO();
		BeanUtils.copyProperties(springParameter, springParameterDTO);
		return springParameterDTO;
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
	public void updateByPrimaryKey(SpringParameterDTO record) {
		SpringParameter springParameter = new SpringParameter();
		BeanUtils.copyProperties(record, springParameter);
		springParameterDao.save(springParameter);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseParameterEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringParameterDTO> getAllRecordByPage(SpringParameterQueryBO springParameterQuery, Pageable pageable) {
		Specification<SpringParameter> specification = new Specification<SpringParameter>() {

			@Override
			public Predicate toPredicate(Root<SpringParameter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (!StringUtils.isEmpty(springParameterQuery.getCode())) {
					Predicate code = cb.like(root.get("code").as(String.class), "%" + springParameterQuery.getCode());
					predicates.add(code);
				}
				if (!StringUtils.isEmpty(springParameterQuery.getK())) {
					Predicate k = cb.like(root.get("k").as(String.class), "%" + springParameterQuery.getK());
					predicates.add(k);
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
		// return springParameterDao.findAll(specification, pageable);
		Page<SpringParameter> springParameters = springParameterDao.findAll(specification, pageable);
		List<SpringParameterDTO> springParameterDTOs = new ArrayList<>();
		springParameters.stream().forEach(springParameter -> {
			SpringParameterDTO springParameterDTO = new SpringParameterDTO();
			BeanUtils.copyProperties(springParameter, springParameterDTO);
			springParameterDTOs.add(springParameterDTO);
		});
		Page<SpringParameterDTO> pages = new PageImpl(springParameterDTOs, pageable,
				springParameters.getTotalElements());
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
		springParameterDao.setDelete(ids);
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
	public void delete(List<String> ids) {
		springParameterDao.delete(ids);

	}

	@Override
	public List<SpringParameterDTO> listByIds(List<String> ids) {
		List<SpringParameter> springParameters=springParameterDao.listByIds(ids);
		List<SpringParameterDTO> springParameterDTOs = new ArrayList<>();
		springParameters.stream().forEach(springParameter -> {
			SpringParameterDTO springParameterDTO = new SpringParameterDTO();
			BeanUtils.copyProperties(springParameter, springParameterDTO);
			springParameterDTOs.add(springParameterDTO);
		});
		return springParameterDTOs;
	}
}
