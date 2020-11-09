package io.github.springsongs.modules.activiti.repo;

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

import io.github.springsongs.modules.activiti.domain.SpringActCategory;
@Repository
public interface SpringActCategoryRepo extends JpaRepository<SpringActCategory, String> {
	

	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringActCategory> findAll(Specification<SpringActCategory> spec, Pageable pageable);
	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseSpringActCategoryEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringActCategory where id in (:ids)")
	public List<SpringActCategory> findInIds(@Param(value = "ids") List<String> ids);

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
	@Query(value = "update SpringActCategory set deletedStatus=1 where id=:id")
	public void setDelete(@Param(value = "id") String id);

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
	@Query(value = "update SpringActCategory set deletedStatus=1 where id in (:ids)")
	public void setDelete(@Param(value = "ids") List<String> ids);
	
	/**
     * 根据组别ID查询
     * @param code
     */
    @Query(value = "from SpringActCategory where deletedStatus=0 and categoryCode=:categoryCode")
    public SpringActCategory getByCode (@Param(value="categoryCode") String categoryCode);
    
    /**
     * 查询全部
     * @return
     */
    @Query(value = "from SpringActCategory where deletedStatus=0")
    public List<SpringActCategory> listAll();
}