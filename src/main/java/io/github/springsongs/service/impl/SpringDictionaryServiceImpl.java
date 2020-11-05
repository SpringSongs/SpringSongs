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

import io.github.springsongs.domain.SpringDictionary;
import io.github.springsongs.domain.SpringDictionaryDetail;
import io.github.springsongs.domain.dto.SpringDictionaryDTO;
import io.github.springsongs.domain.dto.SpringDictionaryDetailDTO;
import io.github.springsongs.domain.query.SpringDictionaryQueryBO;
import io.github.springsongs.repo.SpringDictionaryRepo;
import io.github.springsongs.repo.SpringDictionaryDetailDao;
import io.github.springsongs.service.ISpringDictionaryService;
import io.github.springsongs.util.R;

@Service
@Transactional
public class SpringDictionaryServiceImpl implements ISpringDictionaryService {
	@Autowired
	private SpringDictionaryRepo springDictionaryDao;

	@Autowired
	private SpringDictionaryDetailDao springDictionaryDetailDao;

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
		springDictionaryDao.deleteById(id);

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
	public void insert(SpringDictionaryDTO record) {
		SpringDictionary springDictionary = new SpringDictionary();
		BeanUtils.copyProperties(record, springDictionary);
		springDictionaryDao.save(springDictionary);
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
	public SpringDictionaryDTO selectByPrimaryKey(String id) {
		SpringDictionary springDictionary = springDictionaryDao.getOne(id);
		SpringDictionaryDTO springDictionaryDTO = new SpringDictionaryDTO();
		BeanUtils.copyProperties(springDictionary, springDictionaryDTO);
		return springDictionaryDTO;
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
	public void updateByPrimaryKey(SpringDictionaryDTO record) {
		SpringDictionary springDictionary = new SpringDictionary();
		BeanUtils.copyProperties(record, springDictionary);
		springDictionaryDao.save(record);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseDictionaryEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringDictionaryDTO> getAllRecordByPage(SpringDictionaryQueryBO springDictionaryQuery,
			Pageable pageable) {
		Specification<SpringDictionary> specification = new Specification<SpringDictionary>() {

			@Override
			public Predicate toPredicate(Root<SpringDictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(springDictionaryQuery.getCode())) {
					Predicate code = cb.like(root.get("code").as(String.class), "%" + springDictionaryQuery.getCode());
					predicates.add(code);
				}
				if (!StringUtils.isEmpty(springDictionaryQuery.getTitle())) {
					Predicate title = cb.like(root.get("title").as(String.class),
							"%" + springDictionaryQuery.getTitle());
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
		// Pageable pageable = PageRequest.of(currPage - 1, size);
		Page<SpringDictionary> springDictionarys = springDictionaryDao.findAll(specification, pageable);
		List<SpringDictionaryDTO> pringDictionaryDTOs = new ArrayList<>();
		springDictionarys.stream().forEach(springDictionary -> {
			SpringDictionaryDTO springDictionaryDTO = new SpringDictionaryDTO();
			BeanUtils.copyProperties(springDictionary, springDictionaryDTO);
			pringDictionaryDTOs.add(springDictionaryDTO);
		});
		Page<SpringDictionaryDTO> pages = new PageImpl(pringDictionaryDTOs, pageable,
				springDictionarys.getTotalElements());
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
	public R setDeleted(List<String> ids) {
		R r = R.succeed("Ok");
		try {
			List<String> codes = new ArrayList<String>();
			List<SpringDictionary> entityList = springDictionaryDao.listByIds(ids);
			for (SpringDictionary entity : entityList) {
				if (entity.getEnableDelete() == false) {
					r.put("msg", entity.getTitle() + "不允许删除!");
					r.put("code", 500);
					break;
				}
			}
			if (r.get("code").toString().equals("200")) {
				entityList.stream().forEach(t -> {
					codes.add(t.getCode());
				});
				springDictionaryDetailDao.setDeleteByDictionCode(codes);
				springDictionaryDao.setDelete(ids);
				r.put("msg", "删除成功!");
				r.put("code", 200);
			}
		} catch (Exception e) {
			r.put("msg", "系统错误!");
			r.put("code", 500);
		}
		return r;
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
		springDictionaryDao.delete(ids);

	}

	@Override
	public List<SpringDictionary> listByIds(List<String> ids) {
		return springDictionaryDao.findInIds(ids);
	}
}
