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

import io.github.springsongs.modules.sys.domain.SpringUser;

@Repository
public interface SpringUserRepo extends JpaRepository<SpringUser, String> {
	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringUser> findAll(Specification<SpringUser> spec, Pageable pageable);

	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseBuserEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringUser where id in (:ids)")
	public List<SpringUser> findInIds(@Param(value = "ids") List<String> ids);

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
	@Query(value = "update SpringUser set deletedStatus=1 where id=:id")
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
	@Query(value = "update SpringUser set deletedStatus=1 where id in (:ids)")
	public void setDelete(@Param(value = "ids") List<String> ids);

	/**
	 * 查找用户名
	 * 
	 * @param username
	 * @return
	 */
	@Query(value = "from SpringUser where userName=:userName")
	SpringUser getByUserName(@Param(value = "userName") String userName);

	/**
	 * 查找用户
	 * 
	 * @param ids
	 * @return
	 */
	@Query(value = "from SpringUser where id in (:ids)")
	List<SpringUser> listUserByIds(@Param(value = "ids") List<String> ids);

	/**
	 * 物理删除
	 * 
	 * @param ids
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from SpringUser where id in (:ids)")
	public void delete(@Param(value = "ids") List<String> ids);

	@Query(value = "select m.* from spring_user m left join spring_user_role mr on m.id=mr.user_id where mr.role_id=:roleId"
			, countQuery = "select count(m.*) from spring_user m left join spring_user_role mr on m.id=mr.user_id where m.role_id=:roleId",nativeQuery = true)
	public Page<SpringUser> ListUsersByRoleId(@Param(value = "roleId") String roleId, Pageable pageable);
}
