package com.hazir.Hazirlaniyor.entity.concretes;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UpdatePasswordToken {

	@SequenceGenerator(
			name = "UpdatePasswordToken_sequence",
			sequenceName = "UpdatePasswordToken_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "UpdatePasswordToken_sequence"
	)
	private Long id;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime expiresAt;


	public UpdatePasswordToken(Long id, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
		this.id = id;
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}

	public UpdatePasswordToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
}
