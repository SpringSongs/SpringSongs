package io.github.springsongs.modules.job.repo;

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

import io.github.springsongs.modules.job.domain.SpringJobHistory;

@Repository
public interface SpringJobHistoryRepo extends JpaRepository<SpringJobHistory, String> {
	
	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringJobHistory> findAll(Specification<SpringJobHistory> spec, Pageable pageable);
	
	
	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseSpringJobHistoryEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringJobHistory where id in (:ids)")
	public List<SpringJobHistory> findInIds(@Param(value = "ids") List<String> ids);

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
	@Query(value = "update SpringJobHistory set deletedStatus=1 where id=:id")
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
	@Query(value = "update SpringJobHistory set deletedStatus=1 where id in (:ids)")
	public void setDelete(@Param(value = "ids") List<String> ids);
}