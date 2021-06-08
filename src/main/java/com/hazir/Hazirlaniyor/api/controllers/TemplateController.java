package com.hazir.Hazirlaniyor.api.controllers;

import com.hazir.Hazirlaniyor.business.concretes.RegistrationManager;
import com.hazir.Hazirlaniyor.entity.concretes.RegistrationRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TemplateController {
	private final RegistrationManager mRegistrationManager;
	@GetMapping("login")
	public String getLoginView(){
		return "login";
	}
	@GetMapping("index")
	public String indexHtml(){
		return "index";
	}
	@PostMapping("processForm")
	public String  processForm(@ModelAttribute(value = "registrationRequest")RegistrationRequest registrationRequest){
		mRegistrationManager.register (registrationRequest);
		return "processForm";
	}
}
