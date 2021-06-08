package com.hazir.Hazirlaniyor.entity.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class ChargeRequest {

	public enum Currency {
		GEL, USD;
	}

	private String   description;
	private int      amount;
	private Currency currency;
	private String   stripeEmail;
	private String   stripeToken;

	public ChargeRequest(String description, Currency currency) {
		this.description = description;
		this.currency = currency;
	}
}