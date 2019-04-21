package com.jaydip.urlshortner.web;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.jaydip.urlshortner.entity.Url;
import com.jaydip.urlshortner.service.IUrlService;

@RestController
@RequestMapping(value = "/url")
public class UrlController {

	@Autowired
	IUrlService urlService;

	@RequestMapping(value = "/short", method = RequestMethod.GET)
	public ModelAndView createShortenedUrl() {
		ModelAndView modelAndView = new ModelAndView();
		Url url = new Url();
		modelAndView.addObject("url", url);
		modelAndView.setViewName("admin/urlshortner");
		return modelAndView;
	}

	@RequestMapping(value = "/short", method = RequestMethod.POST)
	public ModelAndView createShortenedUrl(@Valid Url url, HttpServletRequest request) {
		System.out.println("url ->" + url.getOriginalUrl());
		String shortenedUrl = urlService.shortenUrl(url.getOriginalUrl());
		url.setShortenedUrl(shortenedUrl);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userName", "Welcome ");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/urlshortner");
		modelAndView.addObject("originalUrl", url.getOriginalUrl());
		modelAndView.addObject("shortenedUrl", url.getShortenedUrl());

		return modelAndView;
	}

	@RequestMapping(value = "/gotourl", method = RequestMethod.POST)
	public RedirectView redirectUrl(@Valid Url url, HttpServletRequest request, HttpServletResponse response)
			throws IOException, URISyntaxException, Exception {
		System.out.println("Received shortened url to redirect: " + url.getShortenedUrl());
		String redirectUrlString = urlService.getOriginalUrl(url.getShortenedUrl());
		System.out.println("Original URL: " + redirectUrlString);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(redirectUrlString);
		return redirectView;
	}

}
