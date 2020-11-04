package io.github.springsongs.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AnthencationProder implements AuthenticationProvider {

	@Autowired
	private UserSecurityService userSecurityService;
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		UserDetails userDetails = userSecurityService.loadUserByUsername(userName);
		if (userDetails == null) {
			logger.error("UsernameNotFoundException(\"用户名/密码无效\")");
			throw new UsernameNotFoundException("用户名/密码无效");
		} else if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("密码不正确");
		} else if (!userDetails.isEnabled()) {
			logger.error("DisabledException(\"用户已被禁用\")");
			throw new DisabledException("用户已被禁用");
		} else if (!userDetails.isAccountNonExpired()) {
			logger.error("AccountExpiredException(\"账号已过期\")");
			throw new AccountExpiredException("账号已过期");
		} else if (!userDetails.isAccountNonLocked()) {
			logger.error("LockedException(\"账号已被锁定\")");
			throw new LockedException("账号已被锁定");
		} else if (!userDetails.isCredentialsNonExpired()) {
			logger.error("CredentialsExpiredException(\"凭证已过期\")");
			throw new CredentialsExpiredException("凭证已过期");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
