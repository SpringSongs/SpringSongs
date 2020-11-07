package io.github.springsongs.modules.sys.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.springsongs.modules.sys.domain.SpringLoginLog;

@Repository
public interface SpringLoginLogRepo extends JpaRepository<SpringLoginLog, String> {
	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringLoginLog> findAll(Specification<SpringLoginLog> spec, Pageable pageable);

	/**
	 * 物理删除
	 * 
	 * @param ids
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from SpringLoginLog where id in (:ids)")
	public void delete(@Param(value = "ids") List<String> ids);
}
