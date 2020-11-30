package io.github.springsongs.modules.activiti.domain;

import java.io.Serializable;
import java.util.Date;

public class SpringStartTask  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8271777023908163974L;
	private String startUserName;
	private String title;
	private String router;
	private String businessId;
	private Date submitTime;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

}
