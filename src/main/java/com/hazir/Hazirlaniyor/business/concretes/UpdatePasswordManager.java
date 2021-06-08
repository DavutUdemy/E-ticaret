package com.hazir.Hazirlaniyor.business.concretes;

import com.hazir.Hazirlaniyor.dataAccess.abstracts.ConfirmationTokenDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.UpdatePasswordTokenDao;
import com.hazir.Hazirlaniyor.entity.concretes.ConfirmationToken;
import com.hazir.Hazirlaniyor.entity.concretes.UpdatePasswordToken;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdatePasswordManager {

	private final UpdatePasswordTokenDao updatePasswordTokenDao;

	public void saveConfirmationToken(UpdatePasswordToken token) {
		updatePasswordTokenDao.save(token);
	}

	public Optional<UpdatePasswordToken> getToken(String token) {
		return updatePasswordTokenDao.findByToken(token);
	}


}
