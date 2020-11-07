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

import io.github.springsongs.modules.sys.domain.SpringRole;
import io.github.springsongs.modules.sys.dto.RoleCodeDTO;

@Repository
public interface SpringRoleRepo extends JpaRepository<SpringRole, String> {
	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringRole> findAll(Specification<SpringRole> spec, Pageable pageable);

	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseRoleEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringRole where id in (:ids)")
	public List<SpringRole> findInIds(@Param(value = "ids") List<String> ids);

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
	@Query(value = "update SpringRole set deletedStatus=1 where id=:id")
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
	@Query(value = "update SpringRole set deletedStatus=1 where id in (:ids)")
	public void setDelete(@Param(value = "ids") List<String> ids);

	@Query(value = "select new io.github.springsongs.modules.sys.dto.RoleCodeDTO(bre.id,bre.title) from SpringRole bre left join SpringUserRole bure on bre.id=bure.roleId left join SpringUser bue on bue.id=bure.userId where bure.userId=:userId")
	public List<RoleCodeDTO> getRolesByUserId(@Param(value = "userId") String userId);

	/**
	 * 根据userId 查找 role
	 * 
	 * @param userId
	 * @param pageable
	 * @return
	 */
	@Query(value = "select r.* from spring_role r left join spring_user_role bure on r.id=bure.role_id where bure.user_id=:userId", 
			countQuery = "select count(r.*) from role r left join spring_user_role bure on r.id=bure.role_id where bure.user_id=:userId", nativeQuery = true)
	Page<SpringRole> ListRoleByUserId(@Param(value = "userId") String userId, Pageable pageable);
}
