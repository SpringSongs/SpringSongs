package io.github.springsongs.modules.sys.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.springsongs.modules.sys.domain.SpringUserRole;

@Repository
public interface SpringUserRoleRepo extends JpaRepository<SpringUserRole, String> {
	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseBuserRoleEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringUserRole where id in (:ids)")
	public List<SpringUserRole> findInIds(@Param(value = "ids") List<String> ids);

	/**
	 * 删除权限
	 * 
	 * @param userId
	 * @param roleId
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from SpringUserRole where userId=:userId and roleId=:roleId")
	public void delete(@Param(value = "userId") String userId, @Param(value = "roleId") String roleId);

	/**
	 * 删除权限
	 * 
	 * @param userId
	 * @param roleId
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from SpringUserRole where roleId=:roleId")
	public void deleteByRoleId(@Param(value = "roleId") String roleId);
	
	/**
	 * 删除权限
	 * 
	 * @param userId
	 * @param roleId
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from SpringUserRole where userId=:userId")
	public void deleteByUserId(@Param(value = "userId") String userId);

}
