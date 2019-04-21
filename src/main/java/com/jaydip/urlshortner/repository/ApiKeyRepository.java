package com.jaydip.urlshortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaydip.urlshortner.entity.ApiKeys;

@Repository("apiKeyRepository")
public interface ApiKeyRepository extends JpaRepository<ApiKeys, Long> {
	ApiKeys findByUserId(int userId);
	ApiKeys findByApiKey(String apiKey);
}