package com.jaydip.urlshortner.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	public static final List<GrantedAuthority> DEFAULT_ROLES;

	static {
		DEFAULT_ROLES = new ArrayList<GrantedAuthority>(1);
		GrantedAuthority defaultRole = new SimpleGrantedAuthority("ADMIN");
		DEFAULT_ROLES.add(defaultRole);
	}

	private Collection<SimpleGrantedAuthority> authorities;
	private String username;
	private String password;
	private String email;
	private Boolean enabled;

	public UserDetailsImpl() {

	}

	public UserDetailsImpl(String name, String email) {
		this.username = name;
		this.email = email;
	}

	public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
