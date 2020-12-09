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

import io.github.springsongs.modules.sys.domain.SpringResource;
import io.github.springsongs.modules.sys.dto.ResourceRoleDTO;

@Repository
public interface SpringResourceRepo extends JpaRepository<SpringResource, String> {
	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringResource> findAll(Specification<SpringResource> spec, Pageable pageable);

	/**
	 *
	 * IN查询
	 * 
	 * @param ids
	 * @return List<BaseModuleEntity>
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringResource where id in (:ids)")
	public List<SpringResource> findInIds(@Param(value = "ids") List<String> ids);

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
	@Query(value = "update SpringResource set deletedStatus=1 where id=:id")
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
	@Query(value = "update SpringResource set deletedStatus=1 where id in (:ids)")
	public void setDelete(@Param(value = "ids") List<String> ids);

	/**
	 *
	 * 根据上级节点查询了节点
	 * 
	 * @param List<BaseModuleEntity>
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringResource where parentId=:parentId and deletedStatus=0 and systemId=:systemId")
	public List<SpringResource> getByParentId(@Param(value = "parentId") String parentId,@Param(value = "systemId") String systemId);

	/**
	 *
	 * 根据上级节点查询子节点
	 * 
	 * @param ids
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@Query(value = "from SpringResource where parentId in (:parentId) and deletedStatus=0")
	public List<SpringResource> getInParentId(@Param(value = "parentId") List<String> parentId);
	
	/**
	 * 查找用户菜单
	 * @param userId
	 * @return
	 */
	@Query(nativeQuery = true, value = "SELECT DISTINCT  bue.* FROM  spring_user bu"
			+ "    LEFT JOIN spring_user_role bur ON bu.id = bur.user_id" 
			+ "    LEFT JOIN spring_role br ON bur.role_id = br.id" 
			+ "    LEFT JOIN spring_resource_role bp ON bp.role_id = br.id" 
			+ "    LEFT JOIN spring_resource bue ON bue.id = bp.module_id " 
			+ "    WHERE bue.deleted_status=0 and bue.menu_flag=1 and bur.user_id = ? order by bue.sort_code asc")
	public List<SpringResource> listModuleByUserId(String userId);
	
	@Query(value = "SELECT DISTINCT new io.github.springsongs.modules.sys.dto.ResourceRoleDTO(bm.vueUrl,br.title) FROM  SpringRole br" 
	+ "        LEFT JOIN SpringResourceRole bp ON br.id = bp.roleId "
	+ "        LEFT JOIN SpringResource bm ON bm.id = bp.moduleId where br.title in (:title)")
	public List<ResourceRoleDTO> listAllRoleModules(@Param(value = "title") List<String> title);
	
	@Query(value = "from SpringResource where deletedStatus=0 and systemId=:systemCode")
	public List<SpringResource> listAllResources(@Param(value = "systemCode")String systemCode);
}
