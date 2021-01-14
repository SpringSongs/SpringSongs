package io.github.springsongs.modules.sys.domain;

import java.io.Serializable;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "角色实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "spring_role", schema = "base_system")
public class SpringRole extends SpringBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5034432520134702710L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Description(title = "id")
	@Column(name = "id")
	@ApiModelProperty("主键")
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotBlank(message = "请填写名称")
	@Pattern(regexp = "^[A-Z_]+$", message = "名称请填写ROLE下划线与大字母组合")
	@Size(max = 45, min = 1)
	@Description(title = "名称")
	@Column(name = "title")
	@ApiModelProperty("名称")
	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank(message = "请填写说明")
	@Size(max = 45, min = 1)
	@Description(title = "说明")
	@Column(name = "description")
	@ApiModelProperty("说明")
	private String description;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(title = "允许编辑0不允许1允许")
	@Column(name = "enable_edit", columnDefinition = "TINYINT(1)")
	@ApiModelProperty("允许编辑0不允许1允许")
	private boolean enableEdit;

	public boolean getEnableEdit() {
		return this.enableEdit;
	}

	public void setEnableEdit(boolean enableEdit) {
		this.enableEdit = enableEdit;
	}

	@Description(title = "允许删除0不允许1允许")
	@Column(name = "enable_delete", columnDefinition = "TINYINT(1)")
	private boolean enableDelete;

	public boolean getEnableDelete() {
		return this.enableDelete;
	}

	public void setEnableDelete(boolean enableDelete) {
		this.enableDelete = enableDelete;
	}

	@Description(title = "0未删1已删")
   	@Column(name = "deleted_status", columnDefinition = "TINYINT(1)")
   	private boolean deletedStatus;

   	public boolean getDeletedStatus() {
   		return this.deletedStatus;
   	}

   	public void setDeletedStatus(boolean deletedStatus) {
   		this.deletedStatus = deletedStatus;
   	}



}
