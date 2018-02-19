package org.intercomics.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUserDetails extends User {
	private static final long serialVersionUID = 1L;

	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public LoginUserDetails(UserVO user) {
		// 일반적으로는 AuthorityUtils.createAuthorityList 에 다수의 룰을 넣고
		// account 테이블과 분리되어 별도의 권한테이블을 join해서 가져와야하지만
		// 예제가 길어지는걸 방지하기위해 이렇게 만들었다.
		super(user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserType()));
		uid = user.getUid();
	}

	// 이 부분은 나중에 OAuth에서도 쓰이는 부분입니다.!!
	// 기본적으로 USER : RoleType.USER.toString() 를 주고 계정에 추가 권한을 줍니다.
	public static List<GrantedAuthority> getAuthorities(List<UserRole> roles) {
		List<GrantedAuthority> list = new ArrayList<>(1);

		list.add(new SimpleGrantedAuthority(RoleType.USER.toString()));

		if (roles != null) {
			roles.stream().forEach((UserRole role) -> {
				list.add(new SimpleGrantedAuthority(role.getRole()));
			});
		}

		return list;
	}
}