package com.jaydip.urlshortner.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaydip.urlshortner.entity.Url;
import com.jaydip.urlshortner.repository.UrlRepository;

@Service("urlService")
public class UrlServiceImpl implements IUrlService {

	private static final String DOMAIN = "http://jaydip.com/";
	private static final int KEY_LENGTH = 6;
	private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private Random random = new Random();

	@Autowired
	UrlRepository urlRepository;

	@Override
	public String shortenUrl(String originalUrl) {
		Url url = urlRepository.findByOriginalUrl(originalUrl);
		if (url != null) {
			return DOMAIN + url.getShortenedUrl();
		}
		String shortenedUrl = getKey();
		url = new Url();
		url.setOriginalUrl(originalUrl);
		url.setShortenedUrl(shortenedUrl);
		urlRepository.save(url);

		return DOMAIN + shortenedUrl;
	}

	private String getKey() {
		String key = "";
		while (true) {
			key = "";
			for (int i = 0; i <= KEY_LENGTH; i++) {
				key += CHARS.charAt(random.nextInt(CHARS.length()));
			}
			break;
		}
		return key;
	}

	@Override
	public String getOriginalUrl(String shortenedUrl) {
		shortenedUrl = shortenedUrl.startsWith(DOMAIN) ? shortenedUrl.substring(DOMAIN.length()) : shortenedUrl;
		Url url = urlRepository.findByShortenedUrl(shortenedUrl);
		return url.getOriginalUrl();
	}

}
