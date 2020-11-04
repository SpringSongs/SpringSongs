package io.github.springsongs.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.springsongs.annotation.Description;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "spring_dictionary", schema = "base_system")
public class SpringDictionary extends SpringBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1099376742369558398L;
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

	@NotBlank(message = "请填写编码")
	@Size(max = 45, min = 1)
	@Description(title = "编码")
	@Column(name = "code")
	private String code;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotBlank(message = "请填写标题")
	@Size(max = 45, min = 1)
	@Description(title = "标题")
	@Column(name = "title")
	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank(message = "请填写说明")
	@Size(max = 200, min = 1)
	@Description(title = "说明")
	@Column(name = "description")
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description(title = "排序")
	@Column(name = "sort_code")
	private int sortCode;

	public int getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(int sortCode) {
		this.sortCode = sortCode;
	}

	@Description(title = "允许编辑0不允许1允许")
	@Column(name = "enable_edit")
	private boolean enableEdit;

	public boolean getEnableEdit() {
		return this.enableEdit;
	}

	public void setEnableEdit(boolean enableEdit) {
		this.enableEdit = enableEdit;
	}

	@Description(title = "允许删除0不允许1允许")
	@Column(name = "enable_delete")
	private boolean enableDelete;

	public boolean getEnableDelete() {
		return this.enableDelete;
	}

	public void setEnableDelete(boolean enableDelete) {
		this.enableDelete = enableDelete;
	}

	@Description(title = "0未删1已删")
	@Column(name = "deleted_status")
	private boolean deletedStatus;

	public boolean getDeletedStatus() {
		return this.deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}
}