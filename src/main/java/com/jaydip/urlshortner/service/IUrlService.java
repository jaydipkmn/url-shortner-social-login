package com.jaydip.urlshortner.service;

public interface IUrlService {

	String shortenUrl(String originalUrl);
	
	String getOriginalUrl(String shortenedUrl);
}
