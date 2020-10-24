package cn.spring.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import cn.spring.dao.SpringDictionaryDao;
import cn.spring.dao.SpringDictionaryDetailDao;
import cn.spring.domain.SpringDictionary;
import cn.spring.service.ISpringDictionaryService;
import cn.spring.util.R;

@Service
@Transactional
public class SpringDictionaryServiceImpl implements ISpringDictionaryService {
	@Autowired
	private SpringDictionaryDao baseDictionaryDao;
	
	@Autowired
	private SpringDictionaryDetailDao baseDictionaryDetailDao;

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
		baseDictionaryDao.deleteById(id);

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
	public void insert(SpringDictionary record) {
		baseDictionaryDao.save(record);

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
	public SpringDictionary selectByPrimaryKey(String id) {
		return baseDictionaryDao.getOne(id);
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
	public void updateByPrimaryKey(SpringDictionary record) {
		baseDictionaryDao.save(record);
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
	public Page<SpringDictionary> getAllRecordByPage(SpringDictionary record,Pageable pageable) {
		Specification<SpringDictionary> specification = new Specification<SpringDictionary>() {

			@Override
			public Predicate toPredicate(Root<SpringDictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getCode())) {
					Predicate code = cb.equal(root.get("code").as(String.class), record.getCode());
					predicates.add(code);
				}
				if (!StringUtils.isEmpty(record.getTitle())) {
					Predicate title = cb.equal(root.get("title").as(String.class), record.getTitle());
					predicates.add(title);
				}
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
					predicates.add(createdUserId);
				}
				Predicate deletedFlag = cb.equal(root.get("deletedFlag").as(Boolean.class), false);
				predicates.add(deletedFlag);
				Predicate[] pre = new Predicate[predicates.size()];
				query.where(predicates.toArray(pre));
				query.orderBy(cb.desc(root.get("createdOn").as(Date.class)));
				return query.getRestriction();
			}
		};
		//Pageable pageable = PageRequest.of(currPage - 1, size);
		return baseDictionaryDao.findAll(specification, pageable);
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
		R r = R.ok("Ok");
		try {
			List<String> codes=new ArrayList<String>();
			List<SpringDictionary> entityList = baseDictionaryDao.listByIds(ids);
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
				baseDictionaryDetailDao.setDeleteByDictionCode(codes);
				baseDictionaryDao.setDelete(ids);
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
		baseDictionaryDao.delete(ids);

	}

	@Override
	public List<SpringDictionary> listByIds(List<String> ids) {
		return baseDictionaryDao.findInIds(ids);
	}
}
