package io.github.springsongs.security;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import io.github.springsongs.util.Constant;

@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		Iterator<ConfigAttribute> it = configAttributes.iterator();
		AntPathRequestMatcher matcher;
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		while (it.hasNext()) {

			if (authentication == null) {
				throw new AccessDeniedException(Constant.URL_ACCESS_DECISION);
			}

			ConfigAttribute ca = it.next();
			if ("ROLE_LOGIN".equalsIgnoreCase(ca.getAttribute())) {
				if (authentication instanceof AnonymousAuthenticationToken) {
					//matcher = new AntPathRequestMatcher(request.getRequestURI(), "/Login");
					if (request.getRequestURI().contains("Login")) {
					//if (matcher.matches(request)) {
						return;
					}
				} else {
					throw new AccessDeniedException(Constant.URL_ACCESS_DECISION);
				}
				throw new AccessDeniedException(Constant.URL_ACCESS_DECISION);
			}
			Collection<? extends GrantedAuthority> authorties = authentication.getAuthorities();
			for (GrantedAuthority item : authorties) {
				if (item.getAuthority().equalsIgnoreCase(ca.getAttribute())) {
					return;
				}
			}
		}
		throw new AccessDeniedException(Constant.URL_ACCESS_DECISION);
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
