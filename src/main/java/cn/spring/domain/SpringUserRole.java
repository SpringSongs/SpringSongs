package cn.spring.domain;

import java.io.Serializable;

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
@Table(name = "spring_user_role", schema = "base_system")
public class SpringUserRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3980177225043052166L;
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

	@Size(max = 36, min = 0)
	@Description(title = "用户主键")
	@Column(name = "user_id")
	private String userId;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Size(max = 36, min = 0)
	@Description(title = "角色主键")
	@Column(name = "role_id")
	private String roleId;

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

}