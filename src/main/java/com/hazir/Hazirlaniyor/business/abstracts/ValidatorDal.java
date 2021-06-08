package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.business.concretes.ConfirmationTokenManager;
import com.hazir.Hazirlaniyor.business.concretes.UpdatePasswordManager;
import com.hazir.Hazirlaniyor.core.utillities.BadRequest.BadRequestException;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.UpdatePasswordTokenDao;
import com.hazir.Hazirlaniyor.entity.concretes.AppUser;
import com.hazir.Hazirlaniyor.entity.concretes.ConfirmationToken;
import com.hazir.Hazirlaniyor.entity.concretes.UpdatePasswordToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class ValidatorDal implements ValidatorService {
	private final AppUserDao appUserDao;
	private final UpdatePasswordTokenDao updatePasswordTokenDao;

	@Override
	public void validateRepeatPassword(String password, String repeatPassword) {
		if(password.equals (repeatPassword)){
			System.out.println ("Gecerli Parola");
		}
		else{
			throw new BadRequestException ("Repeated password does not equal for password");
		}

	}

	@Override
	public void existsEmail(String email) {
		Optional<AppUser> findUserByEmail = this.appUserDao.getUserByEmail(email);
		if(!findUserByEmail.isPresent ()){
			throw new BadRequestException ( "There is not a user with this email please try again");
		}
			System.out.println ("Working");

	}
@Override
	public Optional<UpdatePasswordToken> getToken(String token) {
	if(updatePasswordTokenDao.findByToken(token).isPresent ()){
		throw new BadRequestException ("There is not a user with this email please try again");
	}
		return updatePasswordTokenDao.findByToken(token);
	}

	@Override
	public void checkIfConfirmationTokenValid(UpdatePasswordManager updatePasswordManager, String token) {
		UpdatePasswordToken updatePasswordToken = updatePasswordManager.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

		LocalDateTime expiredAt = updatePasswordToken.getExpiresAt();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new BadRequestException ("token expired");
		}
	}
}
