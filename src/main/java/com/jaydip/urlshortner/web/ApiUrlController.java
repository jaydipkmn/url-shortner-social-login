package com.jaydip.urlshortner.web;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.jaydip.urlshortner.entity.Url;
import com.jaydip.urlshortner.service.IApiKeyService;
import com.jaydip.urlshortner.service.IUrlService;

@RestController
@RequestMapping(value = "/api/url")
public class ApiUrlController {

	@Autowired
	IUrlService urlService;
	@Autowired
	private IApiKeyService apiKeyService;

	@RequestMapping(value = "/short", method = RequestMethod.POST)
	public ResponseEntity<Url> createShortenedUrl(
			@RequestParam(value = "api_key", required = true) String apiKey,
			@RequestBody Url url, HttpServletRequest request) {
		if(!apiKeyService.getApiKey(apiKey)) {
			throw new UnauthorizedClientException("Api Key is not valid. " + apiKey);
		}
		String shortenedUrl = urlService.shortenUrl(url.getOriginalUrl());
		url.setShortenedUrl(shortenedUrl);
		return ResponseEntity.ok(url);
	}

	@RequestMapping(value = "/gotourl", method = RequestMethod.POST)
	public RedirectView redirectUrl(
			@RequestParam(value = "api_key", required = true) String apiKey,
			@RequestBody Url url, HttpServletRequest request, HttpServletResponse response)
			throws IOException, URISyntaxException, Exception {
		// TODO perform key authentication via some filter or via some configuration.
		if(!apiKeyService.getApiKey(apiKey)) {
			throw new UnauthorizedClientException("Api Key is not valid. " + apiKey);
		}
		System.out.println("Received shortened url to redirect: " + url.getShortenedUrl());
		String redirectUrlString = urlService.getOriginalUrl(url.getShortenedUrl());
		System.out.println("Original URL: " + redirectUrlString);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(redirectUrlString);
		return redirectView;
	}
}
