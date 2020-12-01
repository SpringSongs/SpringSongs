package io.github.springsongs.modules.activiti.editor;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.InputStream;

@Api(tags = "流程json管理")
@RestController
@RequestMapping("service")
public class StencilsetRestResource {

	/**
	 * 获取流程json文件
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取流程json", notes = "获取流程json")
	@RequestMapping(value = "/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getStencilset() {
		InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
		try {
			return IOUtils.toString(stencilsetStream, "utf-8");
		} catch (Exception e) {
			throw new ActivitiException("Error while loading stencil set", e);
		}
	}
}
