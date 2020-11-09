package io.github.springsongs.modules.activiti.domain;

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

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_act_category", schema = "base_system")
public class SpringActCategory extends SpringBase {
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

	@NotBlank(message = "请填写类目编码")
	@Size(max = 45, min = 1)
	@Description(title = "类目编码")
	@Column(name = "category_code")
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
	private String categoryTitle;

	public String getCategoryTitle() {
		return this.categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	
	@Description(title ="允许删除0不允许1允许")
    @Column(name="deleted_status")
    private boolean deletedStatus;
    public boolean getDeletedStatus(){
        return  this.deletedStatus;
    }
    public void setDeletedStatus(boolean deletedStatus){
        this.deletedStatus=deletedStatus;
    }
}
