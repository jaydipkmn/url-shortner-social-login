package com.jaydip.urlshortner.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

	private static final long serialVersionUID = 1L;
	public static final List<GrantedAuthority> DEFAULT_ROLES;

	static {
		DEFAULT_ROLES = new ArrayList<GrantedAuthority>(1);
		GrantedAuthority defaultRole = new SimpleGrantedAuthority("ROLE_USER");
		DEFAULT_ROLES.add(defaultRole);
	}

	private String firstName;

	public CustomUserDetails(String username, String password) {
		super(username, password, DEFAULT_ROLES);
	}

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
