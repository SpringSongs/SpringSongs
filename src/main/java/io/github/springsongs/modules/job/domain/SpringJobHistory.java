package io.github.springsongs.modules.job.domain;

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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="定时任务动行历史实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_job_history", schema = "base_system")
public class SpringJobHistory extends SpringBase {
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

	@NotBlank(message = "请填写任务名称")
	@Size(max = 200, min = 1)
	@Description(title = "任务名称")
	@Column(name = "simple_class_name")
	@ApiModelProperty("任务名称")
	private String simpleClassName;

	public String getSimpleClassName() {
		return this.simpleClassName;
	}

	public void setSimpleClassName(String simpleClassName) {
		this.simpleClassName = simpleClassName;
	}

	@NotBlank(message = "请填写组别")
	@Size(max = 45, min = 1)
	@Description(title = "组别")
	@Column(name = "group_code")
	@ApiModelProperty("任务组别")
	private String groupCode;

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	@Description(title ="删除0不删除1删除")
    @Column(name="deleted_status")
	@ApiModelProperty("删除0不删除1删除")
    private boolean deletedStatus;
    public boolean getDeletedStatus(){
        return  this.deletedStatus;
    }
    public void setDeletedStatus(boolean deletedStatus){
        this.deletedStatus=deletedStatus;
    }
}
