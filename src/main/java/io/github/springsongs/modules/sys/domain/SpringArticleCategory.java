package io.github.springsongs.modules.sys.domain;

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
@ApiModel(description="内容分类实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_article_category", schema = "base_system")
public class SpringArticleCategory extends SpringBase {
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
	@Description(title = "上级主键")
	@Column(name = "parent_id")
	@ApiModelProperty("上级主键")
	private String parentId;

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Size(max = 45, min = 0)
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

	@Size(max = 45, min = 0)
	@Description(title = "标题")
	@Column(name = "title")
	@ApiModelProperty("标题")
	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Size(max = 45, min = 0)
	@Description(title = "关键词")
	@Column(name = "keywords")
	@ApiModelProperty("关键词")
	private String keywords;

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Size(max = 45, min = 0)
	@Description(title = "描述")
	@Column(name = "description")
	@ApiModelProperty("描述")
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Size(max = 45, min = 0)
	@Description(title = "排序")
	@Column(name = "sort_order")
	@ApiModelProperty("排序")
	private String sortOrder;

	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Description(title = "逻辑删除")
	@Column(name = "deleted_status")
	@ApiModelProperty("逻辑删除")
	private boolean deletedStatus;

	public boolean getDeletedStatus() {
		return this.deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}

	@Description(title = "版本号")
	@Column(name = "version")
	@ApiModelProperty("版本号")
	private int version;

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
