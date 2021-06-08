package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.business.concretes.ConfirmationTokenManager;
import com.hazir.Hazirlaniyor.business.concretes.UpdatePasswordManager;
import com.hazir.Hazirlaniyor.entity.concretes.ConfirmationToken;
import com.hazir.Hazirlaniyor.entity.concretes.UpdatePasswordToken;

import java.util.Optional;

public interface ValidatorService {
	void validateRepeatPassword(String password,String repeatPassword);
	void existsEmail(String email);
	public Optional<UpdatePasswordToken> getToken(String token);
	void checkIfConfirmationTokenValid(UpdatePasswordManager updatePasswordManager, String token);

}
