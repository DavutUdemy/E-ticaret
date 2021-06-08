package com.hazir.Hazirlaniyor.entity.concretes;

import lombok.Data;

@Data
public class SuccessEmail {
	private final String userEmail;
	private final String emailBody;
	private final String firstName;
	private final String chargeId;

	public SuccessEmail(String userEmail, String emailBody, String firstName, String chargeId) {
		this.userEmail = userEmail;
		this.emailBody = emailBody;
		this.firstName = firstName;
		this.chargeId = chargeId;
	}
}
