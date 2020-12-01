package io.github.springsongs.modules.sys.domain;

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

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户与角色映射实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_user_role", schema = "base_system")
public class SpringUserRole extends SpringBase  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3980177225043052166L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Description(title = "主键")
	@Column(name = "id")
	@ApiModelProperty("主键")
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
	@ApiModelProperty("用户主键")
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
	@ApiModelProperty("角色主键")
	private String roleId;

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


}