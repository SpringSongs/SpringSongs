package io.github.springsongs.modules.job.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.springsongs.modules.job.domain.SpringJob;

public interface SpringJobRepo extends JpaRepository<SpringJob, String> {

	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringJob> findAll(Specification<SpringJob> spec, Pageable pageable);

	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseSpringJobEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringJob where id in (:ids)")
	public List<SpringJob> findInIds(@Param(value = "ids") List<String> ids);

	/**
	 * 根据组别编码和任务名称查询
	 * @param groupCode
	 * @param taskTitle
	 * @return
	 */
	public SpringJob findByGroupCodeAndTaskClassTitle(@Param(value = "groupCode") String groupCode,
			@Param(value = "taskTitle") String taskClassTitle);

}
