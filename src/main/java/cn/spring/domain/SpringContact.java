package cn.spring.domain;

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

import cn.spring.annotation.Description;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "spring_contact", schema = "base_system")
public class SpringContact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5019908224743231132L;
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

	@NotBlank(message = "请填写公司")
	@Size(max = 45, min = 1)
	@Description(title = "公司")
	@Column(name = "company")
	private String company;

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@NotBlank(message = "请填写职称")
	@Size(max = 45, min = 1)
	@Description(title = "职称")
	@Column(name = "title")
	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank(message = "请填写名称")
	@Size(max = 45, min = 1)
	@Description(title = "名称")
	@Column(name = "username")
	private String username;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank(message = "请填写邮箱")
	@Size(max = 45, min = 1)
	@Description(title = "邮箱")
	@Column(name = "email")
	private String email;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Size(max = 45, min = 0)
	@Description(title = "网址")
	@Column(name = "web")
	private String web;

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Size(max = 45, min = 0)
	@Description(title = "传真")
	@Column(name = "fax")
	private String fax;

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Size(max = 45, min = 0)
	@Description(title = "QQ")
	@Column(name = "qq")
	private String qq;

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Size(max = 45, min = 0)
	@Description(title = "微信")
	@Column(name = "webchat")
	private String webchat;

	public String getWebchat() {
		return this.webchat;
	}

	public void setWebchat(String webchat) {
		this.webchat = webchat;
	}

	@NotBlank(message = "请填写手机")
	@Size(max = 45, min = 1)
	@Description(title = "手机")
	@Column(name = "mobile")
	private String mobile;

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Size(max = 45, min = 0)
	@Description(title = "电话")
	@Column(name = "tel")
	private String tel;

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Description(title = "排序")
	@Column(name = "sort_code")
	private int sortCode;

	public int getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(int sortCode) {
		this.sortCode = sortCode;
	}

	@Description(title = "0未删1已删")
	@Column(name = "deleted_flag")
	private boolean deletedFlag;

	public boolean getDeletedFlag() {
		return this.deletedFlag;
	}

	public void setDeletedFlag(boolean deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Size(max = 36, min = 0)
	@Description(title = "创建人主键")
	@Column(name = "created_user_id")
	private String createdUserId;

	public String getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	@Size(max = 36, min = 0)
	@Description(title = "创建人")
	@Column(name = "created_by")
	private String createdBy;

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Description(title = "创建时间")
	@Column(name = "created_on")
	private java.util.Date createdOn;

	public java.util.Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	@Size(max = 45, min = 0)
	@Description(title = "创建ip")
	@Column(name = "created_ip")
	private String createdIp;

	public String getCreatedIp() {
		return this.createdIp;
	}

	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}

	@Size(max = 36, min = 0)
	@Description(title = "编辑人主键")
	@Column(name = "updated_user_id")
	private String updatedUserId;

	public String getUpdatedUserId() {
		return this.updatedUserId;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	@Size(max = 45, min = 0)
	@Description(title = "编辑人")
	@Column(name = "updated_by")
	private String updatedBy;

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Description(title = "编辑时间")
	@Column(name = "updated_on")
	private java.util.Date updatedOn;

	public java.util.Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(java.util.Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Size(max = 45, min = 0)
	@Description(title = "编辑ip")
	@Column(name = "updated_ip")
	private String updatedIp;

	public String getUpdatedIp() {
		return this.updatedIp;
	}

	public void setUpdatedIp(String updatedIp) {
		this.updatedIp = updatedIp;
	}

}