package com.hazir.Hazirlaniyor.entity.concretes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Contact {
    @SequenceGenerator(
            name = "contact_sequence",
            sequenceName = "contact_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contact_sequence"
    )
    private Long Id;
    @NotNull(message = "FirstName can not be empty!")
    private String firstName;
	  @NotNull(message = "lastName can not be empty!")
	  private String lastName;
	  @NotNull(message = "PostalCode can not be empty!")
	  private String postalCode;
	  @NotNull(message = "FullAdress can not be empty!")
	  private String fullAdress;
	  @NotNull(message = "Phone Number can not be empty!")
	  private String phoneNumber;
	  @NotNull(message = "Email can not be empty!")
	  private String userEmail;
	  private String shipmentStatus;

}
