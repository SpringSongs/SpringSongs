package cn.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import cn.spring.annotation.Description;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_article_category", schema = "base_system")
public class SpringArticleCategory {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Description(title ="主键")
    @Column(name="id")
    private String id;
    public String getId(){
        return  this.id;
    }
    public void setId(String id){
        this.id=id;
    }

    @Size(max=45, min=0)
    @Description(title ="上级主键")
    @Column(name="parent_id")
    private String parentId;
    public String getParentId(){
        return  this.parentId;
    }
    public void setParentId(String parentId){
        this.parentId=parentId;
    }

    @Size(max=45, min=0)
    @Description(title ="编码")
    @Column(name="code")
    private String code;
    public String getCode(){
        return  this.code;
    }
    public void setCode(String code){
        this.code=code;
    }

    @Size(max=45, min=0)
    @Description(title ="标题")
    @Column(name="title")
    private String title;
    public String getTitle(){
        return  this.title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    @Size(max=45, min=0)
    @Description(title ="关键词")
    @Column(name="keywords")
    private String keywords;
    public String getKeywords(){
        return  this.keywords;
    }
    public void setKeywords(String keywords){
        this.keywords=keywords;
    }

    @Size(max=45, min=0)
    @Description(title ="描述")
    @Column(name="description")
    private String description;
    public String getDescription(){
        return  this.description;
    }
    public void setDescription(String description){
        this.description=description;
    }

    @Size(max=45, min=0)
    @Description(title ="排序")
    @Column(name="sort_order")
    private String sortOrder;
    public String getSortOrder(){
        return  this.sortOrder;
    }
    public void setSortOrder(String sortOrder){
        this.sortOrder=sortOrder;
    }

    @Description(title ="逻辑删除")
    @Column(name="deleted_status")
    private boolean deletedStatus;
    public boolean getDeletedStatus(){
        return  this.deletedStatus;
    }
    public void setDeletedStatus(boolean deletedStatus){
        this.deletedStatus=deletedStatus;
    }

    @Size(max=36, min=0)
    @Description(title ="创建人主键")
    @Column(name="created_user_id")
    private String createdUserId;
    public String getCreatedUserId(){
        return  this.createdUserId;
    }
    public void setCreatedUserId(String createdUserId){
        this.createdUserId=createdUserId;
    }

    @Size(max=36, min=0)
    @Description(title ="创建人")
    @Column(name="created_by")
    private String createdBy;
    public String getCreatedBy(){
        return  this.createdBy;
    }
    public void setCreatedBy(String createdBy){
        this.createdBy=createdBy;
    }

    @Description(title ="创建时间")
    @Column(name="created_on")
    private java.util.Date createdOn;
    public java.util.Date getCreatedOn(){
        return  this.createdOn;
    }
    public void setCreatedOn(java.util.Date createdOn){
        this.createdOn=createdOn;
    }

    @Size(max=45, min=0)
    @Description(title ="创建ip")
    @Column(name="created_ip")
    private String createdIp;
    public String getCreatedIp(){
        return  this.createdIp;
    }
    public void setCreatedIp(String createdIp){
        this.createdIp=createdIp;
    }

    @Size(max=36, min=0)
    @Description(title ="编辑人主键")
    @Column(name="updated_user_id")
    private String updatedUserId;
    public String getUpdatedUserId(){
        return  this.updatedUserId;
    }
    public void setUpdatedUserId(String updatedUserId){
        this.updatedUserId=updatedUserId;
    }

    @Size(max=45, min=0)
    @Description(title ="编辑人")
    @Column(name="updated_by")
    private String updatedBy;
    public String getUpdatedBy(){
        return  this.updatedBy;
    }
    public void setUpdatedBy(String updatedBy){
        this.updatedBy=updatedBy;
    }

    @Description(title ="编辑时间")
    @Column(name="updated_on")
    private java.util.Date updatedOn;
    public java.util.Date getUpdatedOn(){
        return  this.updatedOn;
    }
    public void setUpdatedOn(java.util.Date updatedOn){
        this.updatedOn=updatedOn;
    }

    @Size(max=45, min=0)
    @Description(title ="编辑ip")
    @Column(name="updated_ip")
    private String updatedIp;
    public String getUpdatedIp(){
        return  this.updatedIp;
    }
    public void setUpdatedIp(String updatedIp){
        this.updatedIp=updatedIp;
    }

    @Description(title ="版本号")
    @Column(name="version")
    private int  version;
    public int getVersion(){
        return  this.version;
    }
    public void setVersion(int version){
        this.version=version;
    }
}
