package com.jaydip.urlshortner.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jaydip.urlshortner.entity.User;
import com.jaydip.urlshortner.service.IApiKeyService;
import com.jaydip.urlshortner.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private IApiKeyService apiKeyService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home(Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

	@RequestMapping(value = "/apireg", method = RequestMethod.GET)
	public ModelAndView apiKeyRegistration(Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/apikey");
		return modelAndView;
	}

	@RequestMapping(value = "/apireg", method = RequestMethod.POST)
	public ModelAndView getAPiKey(Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String apiKey = "";
		if (auth != null) {
			apiKey = apiKeyService.createApiKey(auth.getName());
		}
		modelAndView.addObject("apiKey", apiKey);
		modelAndView.addObject("apiKeyInstruction",
				"Use this key in your application by passing it with the api_key=API_KEY parameter.");

		modelAndView.setViewName("admin/apikey");

		return modelAndView;
	}

}
