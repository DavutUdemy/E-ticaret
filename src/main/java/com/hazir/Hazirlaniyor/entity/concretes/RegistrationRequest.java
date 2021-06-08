package com.hazir.Hazirlaniyor.entity.concretes;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class  RegistrationRequest {
	  @NotNull(message = "FirstName can not be null!")
    private final String firstName;
	  @NotNull(message = "LastName can not be null!")
    private final String lastName;
	  @NotNull(message = "Email can not be null!")
	  private final String email;
	  @NotNull(message = "Password can not be null!")
	  private final String password;
}
