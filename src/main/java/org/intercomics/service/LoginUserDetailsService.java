package org.intercomics.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.intercomics.mapper.UserRepository;
import org.intercomics.user.LoginUser;
import org.intercomics.user.UserVO;

//not use
@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserVO user;
		LoginUser loginUser = null;
		try {
			user = userRepository.findByUserId(userId);
			if (user == null) {
				throw new UsernameNotFoundException("UsernameNotFound [" + userId + "]");
			}
			loginUser = createUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUser;

	}

	private LoginUser createUser(UserVO user) {
		LoginUser loginUser = new LoginUser(user);
		if (loginUser.getUserType().equals("1")) {
			loginUser.setRoles(Arrays.asList("ROLE_ADMIN"));
		} else {
			loginUser.setRoles(Arrays.asList("ROLE_USER"));
		}
		return loginUser;
	}

}