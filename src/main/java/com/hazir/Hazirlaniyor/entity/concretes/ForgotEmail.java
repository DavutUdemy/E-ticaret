package com.hazir.Hazirlaniyor.entity.concretes;


import lombok.Data;

@Data
public class ForgotEmail {
	private final String email;
	private final  String emailBody;


	public ForgotEmail(String email, String emailBody) {
		this.email = email;
		this.emailBody = emailBody;
	}
}