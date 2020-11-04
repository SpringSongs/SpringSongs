package io.github.springsongs.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.springsongs.dao.SpringLogOnDao;
import io.github.springsongs.dao.SpringRoleDao;
import io.github.springsongs.dao.SpringUserDao;
import io.github.springsongs.domain.SpringUser;
import io.github.springsongs.domain.SpringUserSecurity;
import io.github.springsongs.domain.dto.RoleCodeDTO;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private SpringUserDao userDao;

	@Autowired
	private SpringRoleDao baseRoleDao;

	@Autowired
	private SpringLogOnDao baseUserLoginOnDao;

	@Override
	@Transactional(readOnly = true)  
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SpringUser user = userDao.getByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名没有注册");
		}
		List<RoleCodeDTO> baseRoleLists = baseRoleDao.getRolesByUserId(user.getId());
		user.setRoleList(baseRoleLists);
		SpringUserSecurity baseUserLogOnEntity = baseUserLoginOnDao.findByUserId(user.getId());
		user.setBaseUserLogOnEntity(baseUserLogOnEntity);
		return new MyUserPrincipal(user);
	}

}
