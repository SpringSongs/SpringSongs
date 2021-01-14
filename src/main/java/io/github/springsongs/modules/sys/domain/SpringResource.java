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
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "菜单资源实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "spring_resource", schema = "base_system")
public class SpringResource extends SpringBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2589910559747502834L;
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

	@NotBlank(message = "请填写编码")
	// @Pattern(regexp = "^[A-Za-z]+$", message = "编码请填写字母")
	@Size(max = 200, min = 0)
	@Description(title = "编码")
	@Column(name = "code")
	@ApiModelProperty("编码")
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
	@ApiModelProperty("名称")
	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Description(title = "是否菜单0不是1是")
	@Column(name = "menu_flag", columnDefinition = "TINYINT(1)")
	@ApiModelProperty("是否菜单0不是1是")
	private boolean menuFlag;

	public boolean getMenuFlag() {
		return this.menuFlag;
	}

	public void setMenuFlag(boolean menuFlag) {
		this.menuFlag = menuFlag;
	}

	@Size(max = 200, min = 0)
	@NotBlank(message = "请填写vue链接")
	@Description(title = "链接")
	@Column(name = "vue_url")
	@ApiModelProperty("链接")
	private String vueUrl;

	public String getVueUrl() {
		return this.vueUrl;
	}

	public void setVueUrl(String vueUrl) {
		this.vueUrl = vueUrl;
	}
	
	@Size(max=45, min=0)
    @Description(title ="Icon")
    @Column(name="vue_icon")
	@ApiModelProperty("Icon")
    private String vueIcon;
    public String getVueIcon(){
        return  this.vueIcon;
    }
    public void setVueIcon(String vueIcon){
        this.vueIcon=vueIcon;
    }
    

	@Size(max = 255, min = 0)
	@Description(title = "链接")
	@Column(name = "angular_url")
	@ApiModelProperty("链接")
	private String angularUrl;

	public String getAngularUrl() {
		return this.angularUrl;
	}

	public void setAngularUrl(String angularUrl) {
		this.angularUrl = angularUrl;
	}

	@Size(max=45, min=0)
    @Description(title ="Icon")
    @Column(name="angular_icon")
	@ApiModelProperty("Icon")
    private String angularIcon;
    public String getAngularIcon(){
        return  this.angularIcon;
    }
    public void setAngularIcon(String angularIcon){
        this.angularIcon=angularIcon;
    }
    
	@Description(title = "是否显示0不显示，1显示")
	@Column(name = "show_status", columnDefinition = "TINYINT(1)")
	@ApiModelProperty("是否显示0不显示，1显示")
	private boolean showStatus;

	public boolean getShowStatus() {
		return this.showStatus;
	}

	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}

	@Description(title = "上级")
	@Column(name = "parent_id")
	@ApiModelProperty("上级")
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
	@ApiModelProperty("上级")
	private String parentName;

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Description(title = "排序")
	@Column(name = "sort_code")
	@ApiModelProperty("排序")
	private int sortCode;

	public int getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(int sortCode) {
		this.sortCode = sortCode;
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
	@ApiModelProperty("允许删除0不允许1允许")
	private boolean enableDelete;

	public boolean getEnableDelete() {
		return this.enableDelete;
	}

	public void setEnableDelete(boolean enableDelete) {
		this.enableDelete = enableDelete;
	}

	@Description(title = "0未删1已删")
	@Column(name = "deleted_status", columnDefinition = "TINYINT(1)")
	@ApiModelProperty("0未删1已删")
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
	@ApiModelProperty("系统主键")
	private String systemId;

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
