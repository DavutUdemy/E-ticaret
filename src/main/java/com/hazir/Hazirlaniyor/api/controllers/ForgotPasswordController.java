package com.hazir.Hazirlaniyor.api.controllers;

import com.hazir.Hazirlaniyor.business.abstracts.RegistrationService;
import com.hazir.Hazirlaniyor.business.concretes.ForgotPasswordManager;
import com.hazir.Hazirlaniyor.entity.concretes.ForgotPassword;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/forgot")
public class ForgotPasswordController {
private final ForgotPasswordManager forgotPasswordManager;

	@PostMapping(path = "confirm")
	public String updatePassword(@RequestParam("token") String token, @RequestBody ForgotPassword forgotPassword) {
		return forgotPasswordManager.updatePassword (forgotPassword,token);
	}
	@GetMapping(path = "{email}")
  public String requestForResetingPassword(@PathVariable("email")String email){
		return this.forgotPasswordManager.requestForResetingPassword (email);

	}

}
