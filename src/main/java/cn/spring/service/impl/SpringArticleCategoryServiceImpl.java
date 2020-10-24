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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.spring.dao.SpringArticleCategoryDao;
import cn.spring.domain.SpringArticleCategory;
import cn.spring.domain.SpringResource;
import cn.spring.service.ISpringArticleCategoryService;
import cn.spring.util.R;
import cn.spring.vo.ElementUiTreeVo;

@Service
@Transactional
public class SpringArticleCategoryServiceImpl implements ISpringArticleCategoryService {

	@Autowired
	private SpringArticleCategoryDao baseSpringArticleCategoryDao;

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
		baseSpringArticleCategoryDao.deleteById(id);

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
	public void insert(SpringArticleCategory record) {
		baseSpringArticleCategoryDao.save(record);

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
	public SpringArticleCategory selectByPrimaryKey(String id) {
		return baseSpringArticleCategoryDao.getOne(id);
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
	public void updateByPrimaryKey(SpringArticleCategory record) {
		baseSpringArticleCategoryDao.save(record);
	}

	/**
	 *
	 * 分页查询
	 * 
	 * @param record
	 * @return Page<BaseSpringArticleCategoryEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Override
	public Page<SpringArticleCategory> getAllRecordByPage(SpringArticleCategory record, Pageable pageable) {
		Specification<SpringArticleCategory> specification = new Specification<SpringArticleCategory>() {
			@Override
			public Predicate toPredicate(Root<SpringArticleCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(record.getCreatedUserId())) {
					Predicate createdUserId = cb.equal(root.get("createdUserId").as(String.class),
							record.getCreatedUserId());
					predicates.add(createdUserId);
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
		return baseSpringArticleCategoryDao.findAll(specification, pageable);
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
		baseSpringArticleCategoryDao.setDelete(ids);
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
	public List<ElementUiTreeVo> getCategoryByParentId(String parentId) {
		List<SpringArticleCategory> baseCategoryEntityList = baseSpringArticleCategoryDao.getByParentId(parentId);
		List<ElementUiTreeVo> elementUiTreeDtoList = new ArrayList<ElementUiTreeVo>();
		List<String> ids = new ArrayList<String>();
		for (SpringArticleCategory entity : baseCategoryEntityList) {
			ids.add(entity.getId());
		}
		if (ids.size() > 0) {
			List<SpringArticleCategory> baseCategoryEntityList1 = baseSpringArticleCategoryDao.getInParentId(ids);
			for (SpringArticleCategory entity : baseCategoryEntityList) {
				ElementUiTreeVo elementUiTreeDto = new ElementUiTreeVo();
				elementUiTreeDto.setId(entity.getId());
				elementUiTreeDto.setLeaf(true);
				elementUiTreeDto.setName(entity.getTitle());
				for (SpringArticleCategory entity1 : baseCategoryEntityList1) {
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

}
