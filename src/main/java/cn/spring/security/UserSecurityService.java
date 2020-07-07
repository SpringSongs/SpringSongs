package cn.spring.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.spring.dao.SpringLogOnDao;
import cn.spring.dao.SpringUserDao;
import cn.spring.dao.SpringRoleDao;
import cn.spring.domain.SpringUser;
import cn.spring.domain.SpringUserSecurity;
import cn.spring.dto.RoleCodeDto;

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
		List<RoleCodeDto> baseRoleLists = baseRoleDao.getRolesByUserId(user.getId());
		user.setRoleList(baseRoleLists);
		SpringUserSecurity baseUserLogOnEntity = baseUserLoginOnDao.findByUserId(user.getId());
		user.setBaseUserLogOnEntity(baseUserLogOnEntity);
		return new MyUserPrincipal(user);
	}

}
