package com.hazir.Hazirlaniyor.entity.concretes;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.ui.Model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;


@Entity
@Table

public class PaymentSuccess {
	@SequenceGenerator(
			name = "success_sequence",
			sequenceName = "success_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "success_sequence"
	)
	@Id
	private Long   Id;
	private String status;
	private String chargeId;
	private String balance_transaction;
	@NotNull(message = "Email can not be null")
	private String email;
	@NotNull(message = " FirstName can not be null")
	private String firstName;
	@NotNull(message = "lastName can not be null")
	private String lastName;
	@NotNull(message = "Phone Number can not be null")
	private String phoneNumber;


	public PaymentSuccess() {
	}

	public PaymentSuccess(Long id, String status, String chargeId, String balance_transaction, String email, String firstName, String lastName, String phoneNumber) {
		Id = id;
		this.status = status;
		this.chargeId = chargeId;
		this.balance_transaction = balance_transaction;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getBalance_transaction() {
		return balance_transaction;
	}

	public void setBalance_transaction(String balance_transaction) {
		this.balance_transaction = balance_transaction;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}