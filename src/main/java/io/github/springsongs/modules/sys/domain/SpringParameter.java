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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "系统参数实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "spring_parameter", schema = "base_system")
public class SpringParameter extends SpringBase   implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2981187760046204683L;
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

    @NotBlank(message="请填写名称")
    @Size(max=45, min=1)
    @Description(title ="名称")
    @Column(name="k")
    @ApiModelProperty("名称")
    private String k;
    public String getK(){
        return  this.k;
    }
    public void setK(String k){
        this.k=k;
    }

    @NotBlank(message="请填写值")
    @Size(max=45, min=1)
    @Description(title ="值")
    @Column(name="v")
    @ApiModelProperty("值")
    private String v;
    public String getV(){
        return  this.v;
    }
    public void setV(String v){
        this.v=v;
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

    @Description(title ="允许编辑0不允许1允许")
    @Column(name="enable_edit")
    @ApiModelProperty("允许编辑0不允许1允许")
    private boolean enableEdit;
    public boolean getEnableEdit(){
        return  this.enableEdit;
    }
    public void setEnableEdit(boolean enableEdit){
        this.enableEdit=enableEdit;
    }

    @Description(title ="允许删除0不允许1允许")
    @Column(name="enable_delete")
    @ApiModelProperty("允许删除0不允许1允许")
    private boolean enableDelete;
    public boolean getEnableDelete(){
        return  this.enableDelete;
    }
    public void setEnableDelete(boolean enableDelete){
        this.enableDelete=enableDelete;
    }

    @Description(title = "0未删1已删")
   	@Column(name = "deleted_status")
    @ApiModelProperty("0未删1已删")
   	private boolean deletedStatus;

   	public boolean getDeletedStatus() {
   		return this.deletedStatus;
   	}

   	public void setDeletedStatus(boolean deletedStatus) {
   		this.deletedStatus = deletedStatus;
   	}

}
