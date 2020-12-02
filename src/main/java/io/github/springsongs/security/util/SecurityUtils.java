package io.github.springsongs.security.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import io.github.springsongs.modules.sys.domain.SpringUser;
import io.github.springsongs.security.MyUserPrincipal;
import io.github.springsongs.security.UserSecurityService;


public class SecurityUtils {
	
	public static  List<String> getAuth() {
		List<String> auths = new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<SimpleGrantedAuthority> SimpleGrantedAuthoritys = (List<SimpleGrantedAuthority>) authentication
				.getAuthorities();
		for (SimpleGrantedAuthority simple : SimpleGrantedAuthoritys) {
			auths.add(simple.getAuthority());
		}
		return auths;
	}
	
	@Autowired
	private UserSecurityService userSecurityService;

	public SpringUser getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean caseFlag = false;
		MyUserPrincipal userDetails = null;
		User user = null;
		try {
			user = (User) authentication.getPrincipal();
		} catch (Exception ex) {
			caseFlag = true;
			userDetails = (MyUserPrincipal) authentication.getPrincipal();

		}
		if (!caseFlag) {
			userDetails = (MyUserPrincipal) userSecurityService.loadUserByUsername(user.getUsername());
		}
		return userDetails.getBaseEntityUser();
	}
	
}
