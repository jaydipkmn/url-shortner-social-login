package com.jaydip.urlshortner.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaydip.urlshortner.entity.Role;
import com.jaydip.urlshortner.entity.User;
import com.jaydip.urlshortner.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
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
   
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				grantedAuthorities);
	}
}
