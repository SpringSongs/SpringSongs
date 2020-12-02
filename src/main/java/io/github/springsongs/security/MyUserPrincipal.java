package io.github.springsongs.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.github.springsongs.modules.sys.domain.SpringUser;
import io.github.springsongs.modules.sys.dto.RoleCodeDTO;

public class MyUserPrincipal implements UserDetails {

	private static final Logger logger = LoggerFactory.getLogger(MyUserPrincipal.class);

	private static final long serialVersionUID = 778929886773384012L;

	private SpringUser baseEntityUser;

	public MyUserPrincipal(SpringUser user) {
		this.baseEntityUser = user;
	}

	public SpringUser getBaseEntityUser() {
		return baseEntityUser;
	}

	public void setBaseEntityUser(SpringUser baseEntityUser) {
		this.baseEntityUser = baseEntityUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		List<RoleCodeDTO> roles = baseEntityUser.getRoleList();
		roles.stream().forEach(role -> {
			auths.add(new SimpleGrantedAuthority(role.getCode()));
		});
		return auths;
	}

	@Override
	public String getPassword() {
		logger.info(baseEntityUser.getBaseUserLogOnEntity().getPwd());
		return baseEntityUser.getBaseUserLogOnEntity().getPwd();
	}

	@Override
	public String getUsername() {
		return baseEntityUser.getUserName();
	}

	// 是否需要激活用户
	@Override
	public boolean isAccountNonExpired() {
		boolean isAccountNonExpired = true;
		Date nowDate = new Date();
		return isAccountNonExpired;
	}

	// 帐号是否已经锁定
	@Override
	public boolean isAccountNonLocked() {
		boolean isAccountNonLocked = true;
		Date nowDate = new Date();
		logger.info("isAccountNonLocked={}", isAccountNonLocked);
		return isAccountNonLocked;
	}

	// 是否需要修改密码
	@Override
	public boolean isCredentialsNonExpired() {
		boolean isCredentialsNonExpired = true;
		logger.info("isCredentialsNonExpired={}", isCredentialsNonExpired);
		return isCredentialsNonExpired;
	}

	// 帐号是否启用
	@Override
	public boolean isEnabled() {
		logger.info("isEnabled={}", true);
		return true;
	}
}
