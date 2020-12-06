package io.github.springsongs.modules.sys.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.github.springsongs.modules.sys.domain.SpringSiteNotice;

public interface SpringSiteNoticeRepo extends JpaRepository <SpringSiteNotice, String>{ 
	/**
	 * 分页查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<SpringSiteNotice> findAll(Specification<SpringSiteNotice> spec, Pageable pageable);
    /**
    *
    * IN查询
    * @param ids
    * @return List<BaseSpringSiteNoticeEntity>
    * @see [相关类/方法]（可选）
    * @since [产品/模块版本] （可选）
    */
    @Query(value = "from SpringSiteNotice where id in (:ids)")
    public List<SpringSiteNotice> findInIds(@Param(value = "ids") List<String> ids);
    /**
    *
    * 逻辑删除
    * @param id
    * @return
    * @see [相关类/方法]（可选）
    * @since [产品/模块版本] （可选）
    */
	@Transactional
    @Modifying
    @Query(value = "update SpringSiteNotice set deletedStatus=1 where id=:id")
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
    @Query(value = "update SpringSiteNotice set deletedStatus=1 where id in (:ids)")
    public void setDelete(@Param(value = "ids") List<String> ids);

}
