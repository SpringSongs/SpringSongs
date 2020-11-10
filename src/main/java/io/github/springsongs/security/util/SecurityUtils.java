package io.github.springsongs.security.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtils {
	
	public static  List<String> getAuth() {
		List<String> auths = new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<SimpleGrantedAuthority> SimpleGrantedAuthoritys = (List<SimpleGrantedAuthority>) authentication
				.getAuthorities();
		for (SimpleGrantedAuthority simple : SimpleGrantedAuthoritys) {
			auths.add(simple.getAuthority().replace("ROLE_", ""));
		}
		return auths;
	}
	
}
