package io.github.springsongs.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.modules.sys.domain.SpringUser;
import io.github.springsongs.modules.sys.domain.SpringUserSecurity;
import io.github.springsongs.modules.sys.dto.RoleCodeDTO;
import io.github.springsongs.modules.sys.repo.SpringLogOnRepo;
import io.github.springsongs.modules.sys.repo.SpringRoleRepo;
import io.github.springsongs.modules.sys.repo.SpringUserRepo;

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
			throw new UsernameNotFoundException(ResultCode.USER_NOT_FOUND.getMessage());
		}
		List<RoleCodeDTO> baseRoleLists = baseRoleDao.getRolesByUserId(user.getId());
		user.setRoleList(baseRoleLists);
		SpringUserSecurity baseUserLogOnEntity = baseUserLoginOnDao.findByUserId(user.getId());
		user.setBaseUserLogOnEntity(baseUserLogOnEntity);
		return new MyUserPrincipal(user);
	}

}
