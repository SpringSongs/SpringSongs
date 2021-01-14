package io.github.springsongs.modules.activiti.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="工作流模型分类实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_act_category", schema = "springsongs")
public class SpringActCategory extends SpringBase {
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

	@Pattern(regexp = "^[A-Za-z]+$", message = "组别ID请填写字母")
	@NotBlank(message = "请填写类目编码")
	@Size(max = 45, min = 1)
	@Description(title = "类目编码")
	@Column(name = "category_code")
	@ApiModelProperty("类目编码")
	private String categoryCode;

	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@NotBlank(message = "请填写类目名称")
	@Size(max = 45, min = 1)
	@Description(title = "类目名称")
	@Column(name = "category_title")
	@ApiModelProperty("类目名称")
	private String categoryTitle;

	public String getCategoryTitle() {
		return this.categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	@Description(title = "删除0不允许1允许")
	@Column(name = "deleted_status", columnDefinition = "TINYINT(1)")
	@ApiModelProperty("删除0不允许1允许")
	private boolean deletedStatus;

	public boolean getDeletedStatus() {
		return this.deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}
}
