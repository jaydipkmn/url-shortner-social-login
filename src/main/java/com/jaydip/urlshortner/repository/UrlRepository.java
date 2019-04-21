package com.jaydip.urlshortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaydip.urlshortner.entity.Url;

@Repository("urlRepository")
public interface UrlRepository extends JpaRepository<Url, Long> {
	Url findByOriginalUrl(String url);

	Url findByShortenedUrl(String url);
}