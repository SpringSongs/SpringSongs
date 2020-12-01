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

@ApiModel(description = "登录日志实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_login_log", schema = "base_system")
public class SpringLoginLog extends SpringBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1038894013512290042L;
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

	@Size(max = 45, min = 0)
	@Description(title = "description")
	@Column(name = "description")
	@ApiModelProperty("说明")
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
