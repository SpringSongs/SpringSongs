package io.github.springsongs.modules.sys.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.springsongs.modules.sys.domain.SpringDistrict;

@Repository
public interface SpringDistrictRepo extends JpaRepository<SpringDistrict, Long> {

	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringDistrict> findAll(Specification<SpringDistrict> spec, Pageable pageable);

	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseSpringDistrictEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringDistrict where id in (:ids)")
	public List<SpringDistrict> findInIds(@Param(value = "ids") List<Long> ids);

	/**
	 *
	 * 逻辑删除
	 * 
	 * @param id
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Transactional
	@Modifying
	@Query(value = "update SpringDistrict set deletedStatus=1 where id=:id")
	public void setDelete(@Param(value = "id") Long id);

	/**
	 *
	 * 逻辑批量删除
	 * 
	 * @param ids
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Transactional
	@Modifying
	@Query(value = "update SpringDistrict set deletedStatus=1 where id in (:ids)")
	public void setDelete(@Param(value = "ids") List<Long> ids);

	/**
	 * 根据上级节点查找
	 * 
	 * @param parentId
	 * @return
	 */
	@Query(value = "from SpringDistrict where deletedStatus=0 and parentId=:parentId")
	public List<SpringDistrict> listSpringDistrictByParentId(@Param(value = "parentId") Long parentId);
}
