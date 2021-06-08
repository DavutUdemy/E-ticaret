package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.ConfirmationToken;
import com.hazir.Hazirlaniyor.entity.concretes.UpdatePasswordToken;

import java.util.Optional;

public interface UpdatePasswordTokenService {
	public void saveConfirmationToken(UpdatePasswordToken updatePasswordToken);

	public Optional<UpdatePasswordToken> getToken(String token);
}
