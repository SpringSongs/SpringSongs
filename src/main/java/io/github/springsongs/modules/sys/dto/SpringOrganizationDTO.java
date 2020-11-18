package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import io.github.springsongs.modules.sys.domain.SpringOrganization;

public class SpringOrganizationDTO extends SpringOrganization{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4354528935779911864L;
	@Transient
	private List<SpringOrganizationDTO> children=new ArrayList<>();

	public List<SpringOrganizationDTO> getChildren() {
		return children;
	}

	public void setChildren(List<SpringOrganizationDTO> children) {
		this.children = children;
	}
	
	
	
}
