package io.github.springsongs.modules.sys.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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

@ApiModel(description="友情链接实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_friend_link", schema = "base_system")
public class SpringFriendLink extends SpringBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	@NotBlank(message = "请填写站点名称")
	@Size(max = 100, min = 1)
	@Description(title = "站点名称")
	@Column(name = "title")
	@ApiModelProperty("站点名称")
	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank(message = "请填写链接")
	@Size(max = 255, min = 1)
	@Description(title = "链接")
	@Column(name = "url")
	@ApiModelProperty("链接")
	private String url;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@NotBlank(message="请填写说明")
    @Description(title ="说明")
    @Column(name="desc",columnDefinition = "mediumtext")
	@ApiModelProperty("说明")
    private String description;
    public String getDescription(){
        return  this.description;
    }

	public void setDescription(String description){
        this.description=description;
    }

	@NotBlank(message = "请填写Logo")
	@Size(max = 255, min = 1)
	@Description(title = "Logo")
	@Column(name = "logo_id")
	@ApiModelProperty("Logo")
	private String logoId;

	public String getLogoId() {
		return this.logoId;
	}

	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	@Description(title = "类型")
	@Column(name = "type",columnDefinition = "smallint")
	@ApiModelProperty("类型")
	private int type;

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Description(title = "0未删1已删")
	@Column(name = "deleted_status", columnDefinition = "TINYINT(1)")
	@ApiModelProperty("0未删1已删")
	private boolean deletedStatus;

	public boolean getDeletedStatus() {
		return this.deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}

}
