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
import io.github.springsongs.modules.sys.domain.SpringContact;
import io.github.springsongs.modules.sys.dto.SpringContactDTO;
import io.github.springsongs.modules.sys.dto.query.SpringContactQuery;
import io.github.springsongs.modules.sys.repo.SpringContactRepo;
import io.github.springsongs.modules.sys.service.ISpringContactService;
import io.github.springsongs.util.Constant;

@Service
public class SpringContactServiceImpl implements ISpringContactService {

	static Logger logger = LoggerFactory.getLogger(SpringContactServiceImpl.class);

	@Autowired
	private SpringContactRepo springContactRepo;

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
			springContactRepo.deleteById(id);
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
	public void insert(SpringContactDTO record) {
		SpringContact springContact = new SpringContact();
		BeanUtils.copyProperties(record, springContact);
		try {
			springContactRepo.save(springContact);
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
	public SpringContactDTO selectByPrimaryKey(String id) {
		SpringContact springContact = null;
		try {
			springContact = springContactRepo.getOne(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
		SpringContactDTO springContactDTO = new SpringContactDTO();
		BeanUtils.copyProperties(springContact, springContactDTO);
		return springContactDTO;
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
	public void updateByPrimaryKey(SpringContactDTO springContactDTO) {
		SpringContact entity = springContactRepo.getOne(springContactDTO.getId());
		if (null == entity) {
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		} else {
			entity.setCompany(springContactDTO.getCompany());
			entity.setTitle(springContactDTO.getTitle());
			entity.setUsername(springContactDTO.getUsername());
			entity.setEmail(springContactDTO.getEmail());
			entity.setWeb(springContactDTO.getWeb());
			entity.setFax(springContactDTO.getFax());
			entity.setQq(springContactDTO.getQq());
			entity.setWebchat(springContactDTO.getWebchat());
			entity.setMobile(springContactDTO.getMobile());
			entity.setTel(springContactDTO.getTel());
			entity.setSortCode(springContactDTO.getSortCode());
			try {
				springContactRepo.save(entity);
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
	 * @return Page<BaseBusinessCardEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringContactDTO> getAllRecordByPage(SpringContactQuery springContactQuery, Pageable pageable) {
		if (pageable.getPageSize()>Constant.MAX_PAGE_SIZE) {
			throw new SpringSongsException(ResultCode.PARAMETER_NOT_NULL_ERROR);
		}
		Specification<SpringContact> specification = new Specification<SpringContact>() {

			@Override
			public Predicate toPredicate(Root<SpringContact> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springContactQuery.getCompany())) {
					Predicate company = cb.like(root.get("company").as(String.class),
							springContactQuery.getCompany() + "%");
					predicates.add(company);
				}
				if (!StringUtils.isEmpty(springContactQuery.getUsername())) {
					Predicate username = cb.like(root.get("username").as(String.class),
							springContactQuery.getUsername() + "%");
					predicates.add(username);
				}
				if (!StringUtils.isEmpty(springContactQuery.getMobile())) {
					Predicate mobile = cb.like(root.get("mobile").as(String.class),
							springContactQuery.getMobile() + "%");
					predicates.add(mobile);
				}
				if (!StringUtils.isEmpty(springContactQuery.getQq())) {
					Predicate qq = cb.like(root.get("qq").as(String.class), springContactQuery.getQq() + "%");
					predicates.add(qq);
				}
				if (!StringUtils.isEmpty(springContactQuery.getWebchat())) {
					Predicate webchat = cb.like(root.get("webchat").as(String.class),
							springContactQuery.getWebchat() + "%");
					predicates.add(webchat);
				}
				if (!StringUtils.isEmpty(springContactQuery.getFax())) {
					Predicate fax = cb.like(root.get("fax").as(String.class), springContactQuery.getFax() + "%");
					predicates.add(fax);
				}
				if (!StringUtils.isEmpty(springContactQuery.getTel())) {
					Predicate tel = cb.like(root.get("tel").as(String.class), springContactQuery.getTel() + "%");
					predicates.add(tel);
				}
				if (!StringUtils.isEmpty(springContactQuery.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class), springContactQuery.getTitle() + "%");
					predicates.add(title);
				}
				if (!StringUtils.isEmpty(springContactQuery.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							springContactQuery.getCreatedUserId());
					predicates.add(createdUserId);
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
		Page<SpringContact> springContacts = springContactRepo.findAll(specification, pageable);
		List<SpringContactDTO> springContactDTOs = new ArrayList<>();
		springContacts.stream().forEach(springContact -> {
			SpringContactDTO springContactDTO = new SpringContactDTO();
			BeanUtils.copyProperties(springContact, springContactDTO);
			springContactDTOs.add(springContactDTO);
		});
		Page<SpringContactDTO> pages = new PageImpl(springContactDTOs, pageable, springContacts.getTotalElements());
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
		try {
			springContactRepo.setDelete(ids);
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
		} else if (ids.size() > 1000) {
			throw new SpringSongsException(ResultCode.PARAMETER_MORE_1000);
		}
		try {
			springContactRepo.delete(ids);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new SpringSongsException(ResultCode.INFO_NOT_FOUND);
		}
	}
}
