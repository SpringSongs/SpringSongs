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

@ApiModel(description="附件实体")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "spring_attachment", schema = "base_system")
public class SpringAttachment  extends SpringBase  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2399908685198541143L;
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

	@NotBlank(message = "请填写文件夾主键")
	@Size(max = 36, min = 1)
	@Description(title = "文件夾主键")
	@Column(name = "folder_id")
	@ApiModelProperty("文件夾主键")
	private String folderId;

	public String getFolderId() {
		return this.folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	@NotBlank(message = "请填写文件夹名称")
	@Size(max = 45, min = 1)
	@Description(title = "文件夹名称")
	@Column(name = "folder_name")
	@ApiModelProperty("文件夹名称")
	private String folderName;

	public String getFolderName() {
		return this.folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
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
		this.description=description;
	}
	
	@NotBlank(message = "请填写文件路径")
	@Size(max = 300, min = 1)
	@Description(title = "文件路径")
	@Column(name = "path")
	@ApiModelProperty("文件路径")
	private String path;

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
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
