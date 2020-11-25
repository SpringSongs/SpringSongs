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
@Table(name = "spring_act_use_task", schema = "base_system")
public class SpringActUseTask extends SpringBase {
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

	@NotBlank(message = "请填写流程Key")
	@Size(max = 200, min = 1)
	@Description(title = "流程Key")
	@Column(name = "proc_def_key")
	private String procDefKey;

	public String getProcDefKey() {
		return this.procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	@NotBlank(message = "请填写流程名称")
	@Size(max = 45, min = 1)
	@Description(title = "流程名称")
	@Column(name = "proc_def_name")
	private String procDefName;

	public String getProcDefName() {
		return this.procDefName;
	}

	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}

	@Size(max = 45, min = 0)
	@Description(title = "任务组Key")
	@Column(name = "task_def_key")
	private String taskDefKey;

	public String getTaskDefKey() {
		return this.taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	@Size(max = 45, min = 0)
	@Description(title = "任务名称")
	@Column(name = "task_name")
	private String taskName;

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Size(max = 45, min = 0)
	@Description(title = "1.assignee.受理人(唯一) 2.candidateUser候选人(多个) 3.candidateGroup候选组（多个）")
	@Column(name = "task_ype")
	private String taskYpe;

	public String getTaskYpe() {
		return this.taskYpe;
	}

	public void setTaskYpe(String taskYpe) {
		this.taskYpe = taskYpe;
	}

	@Size(max = 45, min = 0)
	@Description(title = "人或候选人或组的名称ID")
	@Column(name = "candidate_name")
	private String candidateName;

	public String getCandidateName() {
		return this.candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	@Size(max = 45, min = 0)
	@Description(title = "人或候选人或组的名称")
	@Column(name = "candidate_ids")
	private String candidateIds;

	public String getCandidateIds() {
		return this.candidateIds;
	}

	public void setCandidateIds(String candidateIds) {
		this.candidateIds = candidateIds;
	}
}
