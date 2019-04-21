package com.jaydip.urlshortner.service;

public interface IApiKeyService {

	 String createApiKey(String userName );

	boolean getApiKey(String apiKey);
}
