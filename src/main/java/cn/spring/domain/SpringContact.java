package cn.spring.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.spring.annotation.Description;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "spring_contact", schema = "base_system")
public class SpringContact extends SpringBase implements Serializable {
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

	@Email(message = "邮箱格式不正确")
	@NotNull(message = "请填写邮箱")
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

	@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "11位手机号格式不正确")
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
	@Column(name = "deleted_status")
	private boolean deletedStatus;

	public boolean getDeletedStatus() {
		return this.deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}
}