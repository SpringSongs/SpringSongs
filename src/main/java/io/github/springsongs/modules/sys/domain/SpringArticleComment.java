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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description="内容评论实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "spring_article_comment", schema = "base_system")
public class SpringArticleComment  extends SpringBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3847152121362987125L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Description(title = "id")
	@Column(name = "id")
	@ApiModelProperty("主键")
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotBlank(message="请填写评论内容")
    @Size(max=2000, min=1)
    @Description(title ="评论内容")
    @Column(name="content")
	@ApiModelProperty("评论内容")
    private String content;
    public String getContent(){
        return  this.content;
    }
    public void setContent(String content){
        this.content=content;
    }

    @NotBlank(message="请填写主题主键")
    @Size(max=36, min=1)
    @Description(title ="主题主键")
    @Column(name="article_id")
    @ApiModelProperty("主题主键")
    private String articleId;
    public String getArticleId(){
        return  this.articleId;
    }
    public void setArticleId(String articleId){
        this.articleId=articleId;
    }

    @Description(title ="0未审1已审")
    @Column(name="audit_flag")
    @ApiModelProperty("0未审1已审")
    private boolean auditFlag;
    public boolean getAuditFlag(){
        return  this.auditFlag;
    }
    public void setAuditFlag(boolean auditFlag){
        this.auditFlag=auditFlag;
    }

    @Description(title ="排序")
    @Column(name="sort_code")
    @ApiModelProperty("排序")
    private int sortCode;
    public int getSortCode(){
        return  this.sortCode;
    }
    public void setSortCode(int sortCode){
        this.sortCode=sortCode;
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
