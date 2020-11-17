package io.github.springsongs.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;

import io.github.springsongs.modules.sys.domain.SpringResource;

public class SpringResourceDTO extends SpringResource {

	private List<SpringResourceDTO> children=new ArrayList<>();

	public List<SpringResourceDTO> getChildren() {
		return children;
	}

	public void setChildren(List<SpringResourceDTO> children) {
		this.children = children;
	}


}
