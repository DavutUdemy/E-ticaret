package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.ForgotPassword;
import com.hazir.Hazirlaniyor.entity.concretes.SuccessEmail;

public interface ForgotPasswordService {
	public String requestForResetingPassword(String email);
	public void sendSuccessEmail(String email);
	public String updatePassword(ForgotPassword forgotPassword,String token);
	String buildEmail(String Link);

}
