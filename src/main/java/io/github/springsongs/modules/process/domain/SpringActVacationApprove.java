package io.github.springsongs.modules.process.domain;

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

@ApiModel(description = "请假单审批实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_act_vacation_approve", schema = "base_system")
public class SpringActVacationApprove extends SpringBase {
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

	
	@Size(max = 45, min = 1)
	@Description(title = "流程实例id")
	@Column(name = "process_instance_id")
	@ApiModelProperty("流程实例id")
	private String processInstanceId;

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	
	@Size(max = 45, min = 1)
	@Description(title = "Activti任务id")
	@Column(name = "task_id")
	@ApiModelProperty("Activti任务id")
	private String taskId;

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@NotBlank(message="请假单id")
	@Size(max = 36, min = 1)
	@Description(title = "请假单id")
	@Column(name = "vacation_Id")
	@ApiModelProperty("请假单id")
	private String vacationId;
	
	public String getVacationId() {
		return vacationId;
	}

	public void setVacationId(String vacationId) {
		this.vacationId = vacationId;
	}
	
	@Size(max = 36, min = 1)
	@Description(title = "审批人用户id")
	@Column(name = "user_id")
	@ApiModelProperty("审批人用户id")
	private String userId;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Description(title = "审批结果（3：不通过；2：通过）")
	@Column(name = "result")
	@ApiModelProperty("审批结果（3：不通过；2：通过）")
	private short result;

	public short getResult() {
		return this.result;
	}

	public void setResult(short result) {
		this.result = result;
	}

	@NotBlank(message = "请填写备注")
	@Size(max = 200, min = 1)
	@Description(title = "备注")
	@Column(name = "remark")
	@ApiModelProperty("备注")
	private String remark;

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	@Size(max = 45, min = 1)
	@Description(title = "审批人姓名")
	@Column(name = "true_name")
	@ApiModelProperty("审批人姓名")
	private String trueName;

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
}
