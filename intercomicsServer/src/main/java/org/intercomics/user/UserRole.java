package org.intercomics.user;

import java.util.Date;

public class UserRole {
	private Long id;
	private String role;
	private Date roleDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getRoleDate() {
		return roleDate;
	}

	public void setRoleDate(Date roleDate) {
		this.roleDate = roleDate;
	}

	@Override
	public String toString() {
		return "AccountRole [id=" + id + ", role=" + role + ", roleDate=" + roleDate + "]";
	}

}
