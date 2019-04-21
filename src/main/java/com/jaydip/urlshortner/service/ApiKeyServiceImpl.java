package com.jaydip.urlshortner.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaydip.urlshortner.entity.ApiKeys;
import com.jaydip.urlshortner.entity.User;
import com.jaydip.urlshortner.repository.ApiKeyRepository;

@Service("apiKeyService")
public class ApiKeyServiceImpl implements IApiKeyService {

	@Autowired
	private UserService userService;

	@Autowired
	ApiKeyRepository apiKeyRepository;

	@Override
	public String createApiKey(String userName) {
		User user = userService.findUserByEmail(userName);
		if (user != null) {
			ApiKeys apiKey = apiKeyRepository.findByUserId(user.getId());
			if (null == apiKey) {
				apiKey = new ApiKeys();
				apiKey.setUserId(user.getId());
				apiKey.setApiKey(UUID.randomUUID().toString());
				apiKeyRepository.save(apiKey);
			}
			return apiKey.getApiKey();
		}
		return null;
	}

	@Override
	public boolean getApiKey(String apiKey) {
		ApiKeys apiKeys = apiKeyRepository.findByApiKey(apiKey);
		return apiKeys != null;
	}
}
