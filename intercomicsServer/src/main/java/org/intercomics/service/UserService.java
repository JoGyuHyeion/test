package org.intercomics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.intercomics.mapper.UserRepository;
import org.intercomics.user.UserVO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean create(String userId, String password) throws Exception {

		if (null != getUser(userId)) {
			return false;
		}

		UserVO vo = new UserVO();
		vo.setUserId(userId);
		vo.setPassword(new BCryptPasswordEncoder().encode(password));
		vo.setUserType("2");
		userRepository.newUser(vo);

		return true;
	}

	public UserVO getUser(String userId) throws Exception {

		return userRepository.findByUserId(userId);
	}

	public List<UserVO> findAll() throws Exception {
		return userRepository.findAll();
	}

}