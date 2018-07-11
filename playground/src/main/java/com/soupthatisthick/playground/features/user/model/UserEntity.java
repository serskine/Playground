package com.soupthatisthick.playground.features.user.model;

import com.soupthatisthick.playground.features.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

public class UserEntity extends BaseEntity {

	@Column(name="username", nullable = false)
	private String username;

	@Column(name="password", nullable = false)
	private String password;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
