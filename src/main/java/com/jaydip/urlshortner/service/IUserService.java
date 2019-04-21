package com.jaydip.urlshortner.service;

import com.jaydip.urlshortner.entity.User;

public interface IUserService {

	public User findUserByEmail(String email);

	public User saveUser(User user);

}