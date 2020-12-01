package io.github.springsongs.modules.activiti.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="工作流路由")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_act_process_router", schema = "base_system")
public class SpringActProcessRouter extends SpringBase {
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

	@Pattern(regexp = "^[A-Za-z]+$", message = "页面路由请填写字母")
	@Size(max = 200, min = 1)
	@Description(title = "页面路由")
	@Column(name = "router")
	@ApiModelProperty("页面路由")
	private String router;

	public String getRouter() {
		return this.router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	@Pattern(regexp = "^[A-Za-z]+$", message = "流程Key请填写字母")
	@Size(max = 45, min = 1)
	@Description(title = "流程Key")
	@Column(name = "proc_def_key")
	@ApiModelProperty("流程Key")
	private String procDefKey;

	public String getProcDefKey() {
		return this.procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
}
