package io.github.springsongs.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.springsongs.domain.SpringUser;
import io.github.springsongs.domain.SpringUserSecurity;
import io.github.springsongs.domain.dto.RoleCodeDTO;
import io.github.springsongs.repo.SpringLogOnRepo;
import io.github.springsongs.repo.SpringRoleRepo;
import io.github.springsongs.repo.SpringUserRepo;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private SpringUserRepo userDao;

	@Autowired
	private SpringRoleRepo baseRoleDao;

	@Autowired
	private SpringLogOnRepo baseUserLoginOnDao;

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
