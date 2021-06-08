package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.business.concretes.EmailValidatorManager;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.entity.concretes.RegistrationRequest;

public interface ProductCheckerService {
	public void existProduct(String productName);
 }
