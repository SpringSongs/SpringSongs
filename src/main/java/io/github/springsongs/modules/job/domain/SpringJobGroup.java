package io.github.springsongs.modules.job.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="定时任务组实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_job_group", schema = "base_system")
public class SpringJobGroup extends SpringBase implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8887559131573275414L;
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

    @Pattern(regexp = "^[A-Za-z]+$", message = "组别ID请填写字母")
    @Description(title ="组别ID")
    @Column(name="code")
    @ApiModelProperty("组别ID")
    private String code;
    public String getCode(){
        return  this.code;
    }
    public void setCode(String code){
        this.code=code;
    }
    
    
    @NotBlank(message="请填写任务组")
    @Size(max=45, min=1)
    @Description(title ="任务组")
    @Column(name="title")
    @ApiModelProperty("任务组")
    private String title;
    public String getTitle(){
        return  this.title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    @Description(title ="删除0不允许1允许")
    @Column(name="deleted_status")
    @ApiModelProperty("删除0不允许1允许")
    private boolean deletedStatus;
    public boolean getDeletedStatus(){
        return  this.deletedStatus;
    }
    public void setDeletedStatus(boolean deletedStatus){
        this.deletedStatus=deletedStatus;
    }


}
