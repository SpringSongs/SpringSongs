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

import io.github.springsongs.modules.sys.domain.SpringOrganization;

@Repository
public interface SpringOrganizationRepo extends JpaRepository <SpringOrganization, String>{ 
	
	/**
	 * 分页查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringOrganization> findAll(Specification<SpringOrganization> spec, Pageable pageable);
	
    /**
    *
    * IN查询
    * @param ids
    * @return List<BaseSpringOrganizationEntity>
    * @see [相关类/方法]（可选）
    * @since [产品/模块版本] （可选）
    */
	@Transactional
    @Query(value = "from SpringOrganization where id in (:ids)")
    public List<SpringOrganization> findInIds(@Param(value = "ids") List<String> ids);
    /**
    *
    * 逻辑删除
    * @param id
    * @return
    * @see [相关类/方法]（可选）
    * @since [产品/模块版本] （可选）
    */
    @Modifying
    @Query(value = "update SpringOrganization set deletedStatus=1 where id=:id")
    public void setDelete(@Param(value = "id") String id);
    /**
    *
    * 逻辑批量删除
    * @param ids
    * @return
    * @see [相关类/方法]（可选）
    * @since [产品/模块版本] （可选）
    */
    @Transactional
    @Modifying
    @Query(value = "update SpringOrganization set deletedStatus=1 where id in (:ids)")
    public void setDelete(@Param(value = "ids") List<String> ids);
    
    /**
     * 根据上级主键查询组织机构
     * @param parentId
     * @return 
     */
    @Query(value = "from SpringOrganization where deletedStatus=0 and parentId=:parentId")
    public List<SpringOrganization> listOrganizationByParentId(@Param(value = "parentId") String parentId);

    /**
     * 查询所有组织机构
     * @return
     */
    @Query(value = "from SpringOrganization where deletedStatus=0")
	public List<SpringOrganization> listAllRecord();
}
