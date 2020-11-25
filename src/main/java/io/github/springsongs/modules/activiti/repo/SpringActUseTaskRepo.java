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

import io.github.springsongs.modules.activiti.domain.SpringActUseTask;

@Repository
public interface SpringActUseTaskRepo extends JpaRepository<SpringActUseTask, String> {
	
	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringActUseTask> findAll(Specification<SpringActUseTask> spec, Pageable pageable);
	
	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseSpringActUseTaskEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringActUseTask where id in (:ids)")
	public List<SpringActUseTask> findInIds(@Param(value = "ids") List<String> ids);

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
	@Query(value = "update SpringActUseTask set deletedFlag=1 where id=:id")
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
	@Query(value = "update SpringActUseTask set deletedFlag=1 where id in (:ids)")
	public void setDelete(@Param(value = "ids") List<String> ids);

	/**
	 * 物理删除
	 * @param procDefKey
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from SpringActUseTask where procDefKey=:procDefKey")
	public void delete(@Param(value = "procDefKey") String procDefKey);
	
	/**
	 * procDefKey查询
	 * @param procDefKey
	 * @return
	 */
	@Query(value = "from SpringActUseTask where procDefKey=:procDefKey")
	public List<SpringActUseTask> listUserTaskByProcDefKey(@Param(value = "procDefKey") String procDefKey);
	
	/**
	 * taskDefKey查询
	 * @param taskDefKey
	 * @return
	 */
	@Query(value = "from SpringActUseTask where taskDefKey=:taskDefKey")
	public SpringActUseTask findUserTaskByTaskDefKey(@Param(value = "taskDefKey") String taskDefKey);
}
