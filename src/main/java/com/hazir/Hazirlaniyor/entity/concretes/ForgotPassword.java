package com.hazir.Hazirlaniyor.entity.concretes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class ForgotPassword {
	@SequenceGenerator(
			name = "forgot_sequence",
			sequenceName = "forgot_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "forgot_sequence"
	)
	private Long Id;
	@NotNull(message = "Email can not be empty!")
	@Email
	private String email;
	@NotNull(message = "Password can not be empty!")
	private String password;
	@NotNull(message = "Repeated password can not be empty!")
	private String repeatedPassword;

}
