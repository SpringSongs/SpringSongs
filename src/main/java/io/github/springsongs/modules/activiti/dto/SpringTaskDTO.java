package io.github.springsongs.modules.activiti.dto;

import java.util.Date;

public class SpringTaskDTO {

	private String applicant;
	private String taskId;
	private String taskName;
	private String title;
	private String pdName;
	private String version;
	private Long time;
	private String processInstanceId;
	private String status;
	private String nodeKey;
	private String processDefKey;
	private String startUserName;
	private String startTitle;
	private Date submitTime;
	private String auditStr;

	public String getAuditStr() {
		return auditStr;
	}

	public void setAuditStr(String auditStr) {
		this.auditStr = auditStr;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getStartTitle() {
		return startTitle;
	}

	public void setStartTitle(String startTitle) {
		this.startTitle = startTitle;
	}

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	private String router;
	private String businessId;

	public String getProcessDefKey() {
		return processDefKey;
	}

	public void setProcessDefKey(String processDefKey) {
		this.processDefKey = processDefKey;
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPdName() {
		return pdName;
	}

	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
