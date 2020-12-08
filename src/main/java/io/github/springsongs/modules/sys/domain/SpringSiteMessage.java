package io.github.springsongs.modules.sys.domain;

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

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "站内消息实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_site_message", schema = "base_system")
public class SpringSiteMessage extends SpringBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4700734935278169891L;
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

	@NotBlank(message="请填写来自自用户Id")
    @Size(max=36, min=1)
    @Description(title ="来自自用户Id")
    @Column(name="from_user_id")
    private String fromUserId;
    public String getFromUserId(){
        return  this.fromUserId;
    }
    public void setFromUserId(String fromUserId){
        this.fromUserId=fromUserId;
    }

    @NotBlank(message="请填写来自用户名称")
    @Size(max=45, min=1)
    @Description(title ="来自用户名称")
    @Column(name="from_user_name")
    private String fromUserName;
    public String getFromUserName(){
        return  this.fromUserName;
    }
    public void setFromUserName(String fromUserName){
        this.fromUserName=fromUserName;
    }

    @NotBlank(message="请填写收消息用户Id")
    @Size(max=36, min=1)
    @Description(title ="收消息用户Id")
    @Column(name="to_user_id")
    private String toUserId;
    public String getToUserId(){
        return  this.toUserId;
    }
    public void setToUserId(String toUserId){
        this.toUserId=toUserId;
    }

    @NotBlank(message="请填写用户名")
    @Size(max=45, min=1)
    @Description(title ="用户名")
    @Column(name="to_user_name")
    private String toUserName;
    public String getToUserName(){
        return  this.toUserName;
    }
    public void setToUserName(String toUserName){
        this.toUserName=toUserName;
    }

    @NotBlank(message="请填写标题")
    @Size(max=255, min=1)
    @Description(title ="标题")
    @Column(name="title")
    private String title;
    public String getTitle(){
        return  this.title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    @NotBlank(message="请填写消息内容")
	@Size(max = 500, min = 0)
	@Description(title = "消息内容")
	@Column(name = "content")
	@ApiModelProperty("消息内容")
	private String content;

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Description(title = "状态0未读1已读")
	@Column(name = "status")
	@ApiModelProperty("状态0未读1已读")
	private short status;

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
	@Description(title ="0未删1已删")
    @Column(name="deleted_status")
	@ApiModelProperty("0未删1已删")
    private boolean deletedStatus;
    public boolean getDeletedStatus(){
        return  this.deletedStatus;
    }
    public void setDeletedStatus(boolean deletedStatus){
        this.deletedStatus=deletedStatus;
    }
}
