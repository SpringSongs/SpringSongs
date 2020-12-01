package io.github.springsongs.modules.job.domain;

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

@ApiModel(description="定时任务实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_job", schema = "base_system")
public class SpringJob extends SpringBase {
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

    @NotBlank(message="请填写组别编码")
    @Pattern(regexp = "^[A-Za-z]+$", message = "组别ID请填写字母")
    @Size(max=45, min=1)
    @Description(title ="组别编码")
    @Column(name="group_code")
    @ApiModelProperty("组别编码")
    private String groupCode;
    public String getGroupCode(){
        return  this.groupCode;
    }
    public void setGroupCode(String groupCode){
        this.groupCode=groupCode;
    }

    @NotBlank(message="请填写组别名称")
    @Size(max=45, min=1)
    @Description(title ="组别名称")
    @Column(name="group_title")
    @ApiModelProperty("组别名称")
    private String groupTitle;
    public String getGroupTitle(){
        return  this.groupTitle;
    }
    public void setGroupTitle(String groupTitle){
        this.groupTitle=groupTitle;
    }

    @NotBlank(message="请填写任务名称")
    @Size(max=45, min=1)
    @Description(title ="任务名称")
    @Column(name="task_title")
    @ApiModelProperty("任务名称")
    private String taskTitle;
    public String getTaskTitle(){
        return  this.taskTitle;
    }
    public void setTaskTitle(String taskTitle){
        this.taskTitle=taskTitle;
    }

    @NotBlank(message="请填写任务类")
    @Size(max=100, min=1)
    @Description(title ="任务类")
    @Column(name="task_class_title")
    @ApiModelProperty("任务类")
    private String taskClassTitle;
    public String getTaskClassTitle(){
        return  this.taskClassTitle;
    }
    public void setTaskClassTitle(String taskClassTitle){
        this.taskClassTitle=taskClassTitle;
    }

    @NotBlank(message="请填写时间表达式")
    @Size(max=45, min=1)
    @Description(title ="时间表达式")
    @Column(name="cron_Expression")
    @ApiModelProperty("时间表达式")
    private String cronExpression;
    public String getCronExpression(){
        return  this.cronExpression;
    }
    public void setCronExpression(String cronExpression){
        this.cronExpression=cronExpression;
    }

    @Description(title ="状态0创建1暂停2恢复3删除")
    @ApiModelProperty("状态0创建1暂停2恢复3删除")
    @Column(name="status")
    private int status;
    public int getStatus(){
        return  this.status;
    }
    public void setStatus(int status){
        this.status=status;
    }

    @NotBlank(message="请填写备注")
    @Size(max=500, min=1)
    @Description(title ="备注")
    @Column(name="remark")
    @ApiModelProperty("备注")
    private String remark;
    public String getRemark(){
        return  this.remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
}
