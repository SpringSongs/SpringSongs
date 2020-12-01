package io.github.springsongs.modules.sys.domain;

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

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户密码实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_user_security", schema = "base_system")
public class SpringUserSecurity  extends SpringBase  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8676593156000474485L;
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

	@NotBlank(message = "请填写用户主键")
	@Size(max = 45, min = 1)
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

	@NotBlank(message = "请填写密码")
	@Size(max = 100, min = 1)
	@Description(title = "密码")
	@Column(name = "pwd")
	@ApiModelProperty("密码")
	private String pwd;

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
}
