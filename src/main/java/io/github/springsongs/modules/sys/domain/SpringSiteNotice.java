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

@ApiModel(description = "站内公告实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_site_notice", schema = "base_system")
public class SpringSiteNotice  extends SpringBase  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4283176229278289055L;
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Description(title ="主键")
    @Column(name="id")
	@ApiModelProperty("主键")
    private String id;
    public String getId(){
        return  this.id;
    }
    public void setId(String id){
        this.id=id;
    }

    @NotBlank(message="请填写标题")
    @Size(max=45, min=1)
    @Description(title ="标题")
    @Column(name="title")
    @ApiModelProperty("标题")
    private String title;
    public String getTitle(){
        return  this.title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    @NotBlank(message="请填写内容")
    @Description(title ="内容")
    @Column(name = "content",columnDefinition = "text")
    @ApiModelProperty("内容")
    private String content;
    public String getContent(){
        return  this.content;
    }
    public void setContent(String content){
        this.content=content;
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
