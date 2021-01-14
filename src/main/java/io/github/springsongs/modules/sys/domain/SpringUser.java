package io.github.springsongs.modules.sys.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.github.springsongs.modules.sys.dto.RoleCodeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "spring_user", schema = "base_system")
public class SpringUser extends SpringBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3764521651638018825L;
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Description(title ="id")
    @Column(name="id")
	@ApiModelProperty("主键")
    private String id;
    public String getId(){
        return  this.id;
    }
    public void setId(String id){
        this.id=id;
    }

    @Email(message = "邮箱格式不正确")  
    @NotNull(message="请填写邮箱")
    @Size(max=45, min=1)
    @Description(title ="邮箱")
    @Column(name="email")
    @ApiModelProperty("邮箱")
    private String email;
    public String getEmail(){
        return  this.email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "11位手机号格式不正确") 
    @Size(max=45, min=1)
    @Description(title ="手机")
    @Column(name="mobile")
    @ApiModelProperty("手机")
    private String mobile;
    public String getMobile(){
        return  this.mobile;
    }
    public void setMobile(String mobile){
        this.mobile=mobile;
    }

    @Size(max=45, min=0)
    @Description(title ="头像")
    @Column(name="portrait")
    @ApiModelProperty("头像")
    private String portrait;
    public String getPortrait(){
        return  this.portrait;
    }
    public void setPortrait(String portrait){
        this.portrait=portrait;
    }

    @NotBlank(message="请填写用户名")
    @Size(max=45, min=1)
    @Description(title ="用户名")
    @Column(name="user_name")
    @ApiModelProperty("用户名")
    private String userName;
    public String getUserName(){
        return  this.userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }

    @Size(max=45, min=0)
    @Description(title ="真实姓名")
    @NotBlank(message="请填写真实姓名")
    @Column(name="true_name")
    @ApiModelProperty("真实姓名")
    private String trueName;
    public String getTrueName(){
        return  this.trueName;
    }
    public void setTrueName(String trueName){
        this.trueName=trueName;
    }

    @Size(max=45, min=0)
    @Description(title ="签名")
    @Column(name="resume")
    @ApiModelProperty("签名")
    private String resume;
    public String getResume(){
        return  this.resume;
    }
    public void setResume(String resume){
        this.resume=resume;
    }

    
    @Description(title ="组织机构主键")
    @Column(name="organization_id")
    @ApiModelProperty("组织机构主键")
    private String organizationId;
    public String getOrganizationId(){
        return  this.organizationId;
    }
    public void setOrganizationId(String organizationId){
        this.organizationId=organizationId;
    }

   
    @Description(title ="组织机构名称")
    @Column(name="organization_name")
    @ApiModelProperty("组织机构名称")
    private String organizationName;
    public String getOrganizationName(){
        return  this.organizationName;
    }
    public void setOrganizationName(String organizationName){
        this.organizationName=organizationName;
    }

    
    @Description(title ="职称主键")
    @Column(name="title_id")
    @ApiModelProperty("职称主键")
    private String titleId;
    public String getTitleId(){
        return  this.titleId;
    }
    public void setTitleId(String titleId){
        this.titleId=titleId;
    }

   
    @Description(title ="职称名称")
    @Column(name="title_name")
    @ApiModelProperty("职称名称")
    private String titleName;
    public String getTitleName(){
        return  this.titleName;
    }
    public void setTitleName(String titleName){
        this.titleName=titleName;
    }

    @Description(title ="登录次数")
    @Column(name="login_count")
    @ApiModelProperty("登录次数")
    private int loginCount;
    public int getLoginCount(){
        return  this.loginCount;
    }
    public void setLoginCount(int loginCount){
        this.loginCount=loginCount;
    }

    @Description(title ="注册时间")
    @Column(name="register_time")
    @ApiModelProperty("注册时间")
    private java.util.Date registerTime;
    public java.util.Date getRegisterTime(){
        return  this.registerTime;
    }
    public void setRegisterTime(java.util.Date registerTime){
        this.registerTime=registerTime;
    }

    @Size(max=45, min=0)
    @Description(title ="注册IP")
    @Column(name="regsiter_ip")
    @ApiModelProperty("注册IP")
    private String regsiterIp;
    public String getRegsiterIp(){
        return  this.regsiterIp;
    }
    public void setRegsiterIp(String regsiterIp){
        this.regsiterIp=regsiterIp;
    }

    @Description(title ="最后一次登录时间")
    @Column(name="last_login_time")
    @ApiModelProperty("最后一次登录时间")
    private java.util.Date lastLoginTime;
    public java.util.Date getLastLoginTime(){
        return  this.lastLoginTime;
    }
    public void setLastLoginTime(java.util.Date lastLoginTime){
        this.lastLoginTime=lastLoginTime;
    }

    @Description(title ="状态")
    @Column(name="status", columnDefinition = "TINYINT(1)")
    @ApiModelProperty("状态")
    private boolean status;
    public boolean getStatus(){
        return  this.status;
    }
    public void setStatus(boolean status){
        this.status=status;
    }

    @Description(title ="锁定状态")
    @Column(name="lock_status", columnDefinition = "TINYINT(1)")
    @ApiModelProperty("锁定状态")
    private boolean lockStatus;
    public boolean getLockStatus(){
        return  this.lockStatus;
    }
    public void setLockStatus(boolean lockStatus){
        this.lockStatus=lockStatus;
    }

    @Description(title ="排序")
    @Column(name="sort_order")
    @ApiModelProperty("排序")
    private int sortOrder;
    public int getSortOrder(){
        return  this.sortOrder;
    }
    public void setSortOrder(int sortOrder){
        this.sortOrder=sortOrder;
    }

    @Description(title ="允许编辑0不允许1允许")
    @Column(name="enable_edit", columnDefinition = "TINYINT(1)")
    @ApiModelProperty("允许编辑0不允许1允许")
    private boolean enableEdit;
    public boolean getEnableEdit(){
        return  this.enableEdit;
    }
    public void setEnableEdit(boolean enableEdit){
        this.enableEdit=enableEdit;
    }

    @Description(title ="允许删除0不允许1允许")
    @Column(name="enable_delete", columnDefinition = "TINYINT(1)")
    @ApiModelProperty("允许删除0不允许1允许")
    private boolean enableDelete;
    public boolean getEnableDelete(){
        return  this.enableDelete;
    }
    public void setEnableDelete(boolean enableDelete){
        this.enableDelete=enableDelete;
    }

    @Description(title = "0未删1已删")
   	@Column(name = "deleted_status", columnDefinition = "TINYINT(1)")
    @ApiModelProperty("0未删1已删")
   	private boolean deletedStatus;

   	public boolean getDeletedStatus() {
   		return this.deletedStatus;
   	}

   	public void setDeletedStatus(boolean deletedStatus) {
   		this.deletedStatus = deletedStatus;
   	}

	@Transient
	private List<RoleCodeDTO> roleList;

	public List<RoleCodeDTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleCodeDTO> roleList) {
		this.roleList = roleList;
	}

	@Transient
	private SpringUserSecurity baseUserLogOnEntity;

	public SpringUserSecurity getBaseUserLogOnEntity() {
		return baseUserLogOnEntity;
	}

	public void setBaseUserLogOnEntity(SpringUserSecurity baseUserLogOnEntity) {
		this.baseUserLogOnEntity = baseUserLogOnEntity;
	}

}
