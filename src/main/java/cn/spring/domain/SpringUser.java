package cn.spring.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.spring.annotation.Description;
import cn.spring.dto.RoleCodeDto;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "spring_user", schema = "base_system")
public class SpringUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3764521651638018825L;
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Description(title ="id")
    @Column(name="id")
    private String id;
    public String getId(){
        return  this.id;
    }
    public void setId(String id){
        this.id=id;
    }

    @NotBlank(message="请填写邮箱")
    @Size(max=45, min=1)
    @Description(title ="邮箱")
    @Column(name="email")
    private String email;
    public String getEmail(){
        return  this.email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    @NotBlank(message="请填写手机")
    @Size(max=45, min=1)
    @Description(title ="手机")
    @Column(name="mobile")
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
    private String userName;
    public String getUserName(){
        return  this.userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }

    @Size(max=45, min=0)
    @Description(title ="真实姓名")
    @Column(name="true_name")
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
    private String resume;
    public String getResume(){
        return  this.resume;
    }
    public void setResume(String resume){
        this.resume=resume;
    }

    @Description(title ="登录次数")
    @Column(name="login_count")
    private int loginCount;
    public int getLoginCount(){
        return  this.loginCount;
    }
    public void setLoginCount(int loginCount){
        this.loginCount=loginCount;
    }

    @Description(title ="注册时间")
    @Column(name="register_time")
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
    private String regsiterIp;
    public String getRegsiterIp(){
        return  this.regsiterIp;
    }
    public void setRegsiterIp(String regsiterIp){
        this.regsiterIp=regsiterIp;
    }

    @Description(title ="最后一次登录时间")
    @Column(name="last_login_time")
    private java.util.Date lastLoginTime;
    public java.util.Date getLastLoginTime(){
        return  this.lastLoginTime;
    }
    public void setLastLoginTime(java.util.Date lastLoginTime){
        this.lastLoginTime=lastLoginTime;
    }

    @Description(title ="状态")
    @Column(name="status")
    private boolean status;
    public boolean getStatus(){
        return  this.status;
    }
    public void setStatus(boolean status){
        this.status=status;
    }

    @Description(title ="锁定状态")
    @Column(name="lock_status")
    private boolean lockStatus;
    public boolean getLockStatus(){
        return  this.lockStatus;
    }
    public void setLockStatus(boolean lockStatus){
        this.lockStatus=lockStatus;
    }

    @Description(title ="排序")
    @Column(name="sort_order")
    private int sortOrder;
    public int getSortOrder(){
        return  this.sortOrder;
    }
    public void setSortOrder(int sortOrder){
        this.sortOrder=sortOrder;
    }

    @Description(title ="允许编辑0不允许1允许")
    @Column(name="enable_edit")
    private boolean enableEdit;
    public boolean getEnableEdit(){
        return  this.enableEdit;
    }
    public void setEnableEdit(boolean enableEdit){
        this.enableEdit=enableEdit;
    }

    @Description(title ="允许删除0不允许1允许")
    @Column(name="enable_delete")
    private boolean enableDelete;
    public boolean getEnableDelete(){
        return  this.enableDelete;
    }
    public void setEnableDelete(boolean enableDelete){
        this.enableDelete=enableDelete;
    }

    @Description(title ="0未删1已删")
    @Column(name="deleted_flag")
    private boolean deletedFlag;
    public boolean getDeletedFlag(){
        return  this.deletedFlag;
    }
    public void setDeletedFlag(boolean deletedFlag){
        this.deletedFlag=deletedFlag;
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

	@Transient
	private List<RoleCodeDto> roleList;

	public List<RoleCodeDto> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleCodeDto> roleList) {
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
