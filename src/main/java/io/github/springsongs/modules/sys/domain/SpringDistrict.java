package io.github.springsongs.modules.sys.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "行政区域实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_district", schema = "base_system")
public class SpringDistrict extends SpringBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Description(title = "主键")
	@Column(name = "id")
	@ApiModelProperty("主键")
	private long id;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotBlank(message = "请填写名称")
	@Size(max = 255, min = 1)
	@Description(title = "名称")
	@Column(name = "name")
	@ApiModelProperty("请填写名称")
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Description(title = "级别")
	@Column(name = "level")
	@ApiModelProperty("级别")
	private int level;

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Description(title = "上级主键")
	@Column(name = "parent_id")
	@ApiModelProperty("上级主键")
	private long parentId;

	public long getParentId() {
		return this.parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	 @Size(max=45, min=0)
     @Description(title ="上级名称")
     @Column(name="parent_name")
     private String parentName;
     public String getParentName(){
         return  this.parentName;
     }
     public void setParentName(String parentName){
         this.parentName=parentName;
     }
     
	@Description(title = "sort_order")
	@Column(name = "sort_order")
	@ApiModelProperty("排序")
	private short sortOrder;

	public short getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(short sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Description(title ="0未删1已删")
    @Column(name="deleted_status")
	@ApiModelProperty("0未删1已删")
    private boolean deletedStatus;
    public boolean getDeletedStatus(){
        return  this.deletedStatus;
    }
    public void setDeletedStatus(boolean deletedStatus){
        this.deletedStatus=deletedStatus;
    }
}
