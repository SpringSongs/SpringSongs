package cn.spring.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import cn.spring.annotation.Description;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_user_security", schema = "base_system")
public class SpringUserSecurity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8676593156000474485L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Description(title = "主键")
	@Column(name = "id")
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotBlank(message = "请填写用户主键")
	@Size(max = 45, min = 1)
	@Description(title = "用户主键")
	@Column(name = "user_id")
	private String userId;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@NotBlank(message = "请填写密码")
	@Size(max = 100, min = 1)
	@Description(title = "密码")
	@Column(name = "pwd")
	private String pwd;

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Size(max = 36, min = 0)
	@Description(title = "创建人主键")
	@Column(name = "created_user_id")
	private String createdUserId;

	public String getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	@Size(max = 36, min = 0)
	@Description(title = "创建人")
	@Column(name = "created_by")
	private String createdBy;

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Description(title = "创建时间")
	@Column(name = "created_on")
	private java.util.Date createdOn;

	public java.util.Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	@Size(max = 45, min = 0)
	@Description(title = "创建ip")
	@Column(name = "created_ip")
	private String createdIp;

	public String getCreatedIp() {
		return this.createdIp;
	}

	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}

	@Size(max = 36, min = 0)
	@Description(title = "编辑人主键")
	@Column(name = "updated_user_id")
	private String updatedUserId;

	public String getUpdatedUserId() {
		return this.updatedUserId;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	@Size(max = 45, min = 0)
	@Description(title = "编辑人")
	@Column(name = "updated_by")
	private String updatedBy;

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Description(title = "编辑时间")
	@Column(name = "updated_on")
	private java.util.Date updatedOn;

	public java.util.Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(java.util.Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Size(max = 45, min = 0)
	@Description(title = "编辑ip")
	@Column(name = "updated_ip")
	private String updatedIp;

	public String getUpdatedIp() {
		return this.updatedIp;
	}

	public void setUpdatedIp(String updatedIp) {
		this.updatedIp = updatedIp;
	}

}
