package io.github.springsongs.modules.sys.service.impl;

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

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.modules.sys.domain.SpringLoginLog;
import io.github.springsongs.modules.sys.dto.SpringLoginLogDTO;
import io.github.springsongs.modules.sys.dto.query.SpringLoginLogQuery;
import io.github.springsongs.modules.sys.repo.SpringLoginLogRepo;
import io.github.springsongs.modules.sys.service.ISpringLoginLogService;
import io.github.springsongs.util.Constant;

@Service
public class SpringLoginLogServiceImpl implements ISpringLoginLogService {
	@Autowired
	private SpringLoginLogRepo springLoginLogRepo;

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
		springLoginLogRepo.deleteById(id);

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
	public void insert(SpringLoginLogDTO record) {
		SpringLoginLog springLoginLog = new SpringLoginLog();
		BeanUtils.copyProperties(record, springLoginLog);
		springLoginLogRepo.save(springLoginLog);

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
	public SpringLoginLogDTO selectByPrimaryKey(String id) {
		SpringLoginLog springLoginLog = springLoginLogRepo.getOne(id);
		SpringLoginLogDTO springLoginLogDTO = new SpringLoginLogDTO();
		BeanUtils.copyProperties(springLoginLog, springLoginLogDTO);
		return springLoginLogDTO;
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
	public void updateByPrimaryKey(SpringLoginLogDTO record) {
		SpringLoginLog springLoginLog = new SpringLoginLog();
		BeanUtils.copyProperties(record, springLoginLog);
		springLoginLogRepo.save(record);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseLoginLogEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringLoginLogDTO> getAllRecordByPage(SpringLoginLogQuery springLoginLogQuery, Pageable pageable) {
		
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		
		
		Specification<SpringLoginLog> specification = new Specification<SpringLoginLog>() {

			@Override
			public Predicate toPredicate(Root<SpringLoginLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springLoginLogQuery.getCreatedBy())) {
					Predicate createdBy = cb.equal(root.get("createdBy").as(String.class),
							springLoginLogQuery.getCreatedBy());
					predicates.add(createdBy);
				}
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		
		Page<SpringLoginLog> springLoginLogs = springLoginLogRepo.findAll(specification, pageable);
		List<SpringLoginLogDTO> springLoginLogDTOs = new ArrayList<>();
		springLoginLogs.stream().forEach(springLoginLog -> {
			SpringLoginLogDTO springLoginLogDTO = new SpringLoginLogDTO();
			BeanUtils.copyProperties(springLoginLog, springLoginLogDTO);
			springLoginLogDTOs.add(springLoginLogDTO);
		});
		Page<SpringLoginLogDTO> pages = new PageImpl(springLoginLogDTOs, pageable,
				springLoginLogs.getTotalElements());
		return pages;
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
		springLoginLogRepo.delete(ids);

	}
}
