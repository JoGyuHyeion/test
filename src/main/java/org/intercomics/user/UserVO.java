package org.intercomics.user;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("UserVO")
public class UserVO {

	private String uid;
	private String userId;
	private String password;
	private String userType;
	private Date regDate = new Date();
	private int episodeCount;

	List<UserRole> roles;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public int getEpisodeCount() {
		return episodeCount;
	}

	public void setEpisodeCount(int episodeCount) {
		this.episodeCount = episodeCount;
	}

	@Override
	public String toString() {
		return "UserVO [uid=" + uid + ", userId=" + userId + ", password=" + password + ", userType=" + userType
				+ ", regDate=" + regDate + ", episodeCount=" + episodeCount + ", roles=" + roles + "]";
	}

}
