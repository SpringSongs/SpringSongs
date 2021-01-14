package io.github.springsongs.modules.sys.domain;

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

/**
 * System
 * 
 * 
 * 修改纪录
 * 
 * Sun Dec 16 21:55:57 CST 2018 @version V1.0 I love you 创建。
 * 
 * 版本：1.0
 * 
 * @author I love you
 * @date Sun Dec 16 21:55:57 CST 2018
 */
@ApiModel(description = "子系统实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_system", schema = "base_system")
public class SpringSystem extends SpringBase  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6600557120317516906L;
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

    @NotBlank(message="请填写编码")
    @Pattern(regexp = "^[A-Za-z]+$", message = "编码请填写字母")
    @Size(max=45, min=1)
    @Description(title ="编码")
    @Column(name="code")
    @ApiModelProperty("编码")
    private String code;
    public String getCode(){
        return  this.code;
    }
    public void setCode(String code){
        this.code=code;
    }

    @NotBlank(message="请填写系统名称")
    @Size(max=45, min=1)
    @Description(title ="系统名称")
    @Column(name="title")
    @ApiModelProperty("系统名称")
    private String title;
    public String getTitle(){
        return  this.title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    @Size(max=255, min=0)
    @Description(title ="系统说明")
    @Column(name="description")
    @ApiModelProperty("系统说明")
    private String description;
    public String getDescription(){
        return  this.description;
    }
    public void setDescription(String description){
        this.description=description;
    }

    @Description(title ="允许删除0不允许1允许")
    @Column(name="enable_delete", columnDefinition = "TINYINT(1)")
    @ApiModelProperty("允许删除0不允许1允许")
    private boolean enableDelete;
    public boolean getEnableDelete(){
        return  this.enableDelete;
    }
    public void setEnableDelete(boolean enableDelete){
        this.enableDelete=enableDelete;
    }

    @Description(title ="允许编辑0不允许1允许")
    @Column(name="enable_edit", columnDefinition = "TINYINT(1)")
    @ApiModelProperty("允许编辑0不允许1允许")
    private boolean enableEdit;
    public boolean getEnableEdit(){
        return  this.enableEdit;
    }
    public void setEnableEdit(boolean enableEdit){
        this.enableEdit=enableEdit;
    }

    @Description(title ="0未删1已删")
    @Column(name="deleted_status", columnDefinition = "TINYINT(1)")
    @ApiModelProperty("0未删1已删")
    private boolean deletedStatus;
    public boolean getDeletedStatus(){
        return  this.deletedStatus;
    }
    public void setDeletedStatus(boolean deletedStatus){
        this.deletedStatus=deletedStatus;
    }

}
