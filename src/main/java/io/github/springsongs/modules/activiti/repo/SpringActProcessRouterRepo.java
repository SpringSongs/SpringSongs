package io.github.springsongs.modules.activiti.repo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.springsongs.modules.activiti.domain.SpringActProcessRouter;

@Repository
public interface SpringActProcessRouterRepo extends JpaRepository<SpringActProcessRouter, String> {

	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseSpringActProcessRouterEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringActProcessRouter where id in (:ids)")
	public List<SpringActProcessRouter> findInIds(@Param(value = "ids") List<String> ids);

	/**
	 * 根据procDefKey查询
	 * 
	 * @param procDefKey
	 * @return
	 */
	@Query(value = "from SpringActProcessRouter where procDefKey=:procDefKey")
	public SpringActProcessRouter findSpringActProcessRouterByProcDefKey(
			@Param(value = "procDefKey") String procDefKey);
}
