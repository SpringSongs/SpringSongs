package io.github.springsongs.enumeration;

public enum SchedulerStatus {
	CREATED(0, "已创建"), PAUSED(1, "已暂停"), RESUME(2, "已恢复"), UPDATED(3, "已更新"), DELETED(4, "已删除");

	private int status;
	private String message;

	private SchedulerStatus(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
