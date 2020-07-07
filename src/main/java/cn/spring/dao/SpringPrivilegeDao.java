package cn.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.spring.domain.SpringPrivilege;

@Repository
public interface SpringPrivilegeDao extends JpaRepository <SpringPrivilege, String>{ 
    /**
    *
    * IN查询
    * @param ids
    * @return List<BaseAuthorityEntity>
    * @see [相关类/方法]（可选）
    * @since [产品/模块版本] （可选）
    */
    @Query(value = "from SpringPrivilege where id in (:ids)")
    public List<SpringPrivilege> findInIds(@Param(value = "ids") List<String> ids);
    
}
