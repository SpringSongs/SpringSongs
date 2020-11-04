package cn.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.vip.vjtools.vjkit.collection.CollectionUtil;

import cn.spring.domain.dto.ResourceRoleDTO;
import cn.spring.service.ISpringResourceService;

@Component
public class UrlFilterInvocationSecurityMetadaSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private ISpringResourceService baseModuleService;

	private AntPathMatcher antPathMatchc = new AntPathMatcher();

	String requestUrl="";
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		requestUrl= ((FilterInvocation) object).getRequestUrl();
		List<ResourceRoleDTO> mpoduleRoleList = baseModuleService.listAllRoleModules();
		List<String> roles = new ArrayList<String>();
		
		mpoduleRoleList.stream().forEach(moduleRoleDto -> {
			if (null==moduleRoleDto.getNavigateUrl()) {
				moduleRoleDto.setNavigateUrl("");
			}
			if (requestUrl.contains(moduleRoleDto.getNavigateUrl())
					&& !StringUtils.isEmpty(moduleRoleDto.getCode())) {
				roles.add(moduleRoleDto.getCode());
			}
//			if (antPathMatchc.match(requestUrl, moduleRoleDto.getNavigateUrl())
//					&& !StringUtils.isEmpty(moduleRoleDto.getCode())) {
//				roles.add(moduleRoleDto.getCode());
//			}	
		});
		
		
		if (!CollectionUtil.isEmpty(roles)) {
			String[] str=new String[roles.size()];
			for (int i = 0; i < roles.toArray().length; i++) {
				str[i]="ROLE_" + roles.get(i);
			}
			return SecurityConfig.createList(str);
		}

		return SecurityConfig.createList("ROLE_LOGIN");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
