package io.github.springsongs.modules.sys.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import io.github.springsongs.annotation.Description;
import io.github.springsongs.common.base.SpringBase;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "spring_district", schema = "base_system")
public class SpringDistrict extends SpringBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Description(title = "主键")
	@Column(name = "id")
	private long id;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotBlank(message = "请填写名称")
	@Size(max = 255, min = 1)
	@Description(title = "名称")
	@Column(name = "name")
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Description(title = "级别")
	@Column(name = "level")
	private int level;

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Description(title = "上级主键")
	@Column(name = "parent_id")
	private long parentId;

	public long getParentId() {
		return this.parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	@Description(title = "sort_order")
	@Column(name = "sort_order")
	private short sortOrder;

	public short getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(short sortOrder) {
		this.sortOrder = sortOrder;
	}
}
