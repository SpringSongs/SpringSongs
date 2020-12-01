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

@ApiModel(description="附件分类实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "spring_attachment_category", schema = "base_system")
public class SpringAttachmentCategory  extends SpringBase   implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3525193876705191584L;
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

	@NotBlank(message = "请填写文件夾名称")
	@Size(max = 45, min = 1)
	@Description(title = "文件夾名称")
	@Column(name = "title")
	@ApiModelProperty("主键")
	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank(message = "请填写说明")
	@Size(max = 45, min = 1)
	@Description(title = "说明")
	@Column(name = "description")
	@ApiModelProperty("说明")
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Size(max = 45, min = 0)
	@Description(title = "分类名称")
	@Column(name = "dictionary_code")
	@ApiModelProperty("分类名称")
	private String dictionaryCode;

	public String getDictionaryCode() {
		return this.dictionaryCode;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}

	@Size(max = 45, min = 0)
	@Description(title = "分类名称")
	@Column(name = "dictionary_name")
	@ApiModelProperty("分类名称")
	private String dictionaryName;

	public String getDictionaryName() {
		return this.dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
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
