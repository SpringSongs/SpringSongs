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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.SpringBase;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "spring_resource", schema = "base_system")
public class SpringResource extends SpringBase   implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2589910559747502834L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Description(title = "id")
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

	@NotBlank(message = "请填写名称")
	@Size(max = 45, min = 1)
	@Description(title = "名称")
	@Column(name = "title")
	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Description(title = "是否菜单0不是1是")
	@Column(name = "menu_flag")
	private boolean menuFlag;

	public boolean getMenuFlag() {
		return this.menuFlag;
	}

	public void setMenuFlag(boolean menuFlag) {
		this.menuFlag = menuFlag;
	}

	@Size(max = 45, min = 0)
	@Description(title = "链接")
	@Column(name = "vue_url")
	private String vueUrl;

	public String getVueUrl() {
		return this.vueUrl;
	}

	public void setVueUrl(String vueUrl) {
		this.vueUrl = vueUrl;
	}

	@Size(max = 45, min = 0)
	@Description(title = "链接")
	@Column(name = "angular_url")
	private String angularUrl;

	public String getAngularUrl() {
		return this.angularUrl;
	}

	public void setAngularUrl(String angularUrl) {
		this.angularUrl = angularUrl;
	}

	@Description(title = "上级")
	@Column(name = "parent_id")
	private String parentId;

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Size(max = 45, min = 0)
	@Description(title = "上级")
	@Column(name = "parent_name")
	private String parentName;

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	@NotBlank(message = "请填写系统主键")
	@Size(max = 36, min = 1)
	@Description(title = "系统主键")
	@Column(name = "system_id")
	private String systemId;

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	
}