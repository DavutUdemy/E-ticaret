package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.business.concretes.EmailValidatorManager;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.entity.concretes.RegistrationRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class RegistrationDal implements RegistrationService{


    @Override
    public boolean takenEmail(AppUserDao appUserDao, RegistrationRequest request) {
        return appUserDao.findUserByEmail(request.getEmail());
    }

    @Override
    public boolean isValid(EmailValidatorManager emailValidatorManager, RegistrationRequest request) {
        return emailValidatorManager.test(request.getEmail());
    }


}
