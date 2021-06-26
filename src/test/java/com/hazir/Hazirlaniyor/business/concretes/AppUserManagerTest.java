package com.hazir.Hazirlaniyor.business.concretes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hazir.Hazirlaniyor.business.abstracts.Facade;
import com.hazir.Hazirlaniyor.core.utillities.results.DataResult;
import com.hazir.Hazirlaniyor.core.utillities.results.Result;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.entity.concretes.AppUser;
import com.hazir.Hazirlaniyor.entity.concretes.AppUserRole;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Facade.class, EmailValidatorManager.class, AppUserManager.class,
		ConfirmationTokenManager.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
public class AppUserManagerTest {
	@MockBean
	private EmailValidatorManager emailValidatorManager;

	@MockBean
	private AppUserDao appUserDao;

	@Autowired
	private AppUserManager appUserManager;

	@MockBean
	private BCryptPasswordEncoder   bCryptPasswordEncoder;
	@Captor
	private ArgumentCaptor<AppUser> userArgumentCaptor;


	@MockBean
	private ConfirmationTokenManager confirmationTokenManager;


	@MockBean
	private Facade                facade;



	@Test
	public void testSignUpUser() {
		when (this.emailValidatorManager.test (anyString ())).thenReturn (true);
		doNothing ().when (this.confirmationTokenManager)
				.saveConfirmationToken ((com.hazir.Hazirlaniyor.entity.concretes.ConfirmationToken) any ());
		when (this.bCryptPasswordEncoder.encode ((CharSequence) any ())).thenReturn ("foo");

		AppUser appUser = new AppUser ();
		appUser.setLastName ("Doe");
		appUser.setEmail ("jane.doe@example.org");
		appUser.setPassword ("iloveyou");
		appUser.setAppUserRole (AppUserRole.USER);
		appUser.setId (123L);
		appUser.setEnabled (true);
		appUser.setLocked (true);
		appUser.setFirstName ("Jane");
		when (this.appUserDao.save ((AppUser) any ())).thenReturn (appUser);
		when (this.appUserDao.findByEmail (anyString ())).thenReturn (Optional.<AppUser>empty ());
		AppUser appUser1 = mock (AppUser.class);
		doNothing ().when (appUser1).setPassword (anyString ());
		when (appUser1.getPassword ()).thenReturn ("foo");
		when (appUser1.getEmail ()).thenReturn ("foo");
		this.appUserManager.signUpUser (appUser1);
		verify (this.emailValidatorManager).test (anyString ());
		verify (this.confirmationTokenManager)
				.saveConfirmationToken ((com.hazir.Hazirlaniyor.entity.concretes.ConfirmationToken) any ());
		verify (this.bCryptPasswordEncoder).encode ((CharSequence) any ());
		verify (this.appUserDao).findByEmail (anyString ());
		verify (this.appUserDao).save ((AppUser) any ());
		verify (appUser1, times (2)).getEmail ();
		verify (appUser1).getPassword ();
		verify (appUser1).setPassword (anyString ());
		assertTrue (
				this.appUserManager.getAllUsers () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testUpdatePassword() {
		when (this.bCryptPasswordEncoder.encode ((CharSequence) any ())).thenReturn ("foo");
		when (this.appUserDao.updatePassword (anyString (), anyString ())).thenReturn (1);
		assertEquals (1, this.appUserManager.updatePassword ("iloveyou", "jane.doe@example.org"));
		verify (this.bCryptPasswordEncoder).encode ((CharSequence) any ());
		verify (this.appUserDao).updatePassword (anyString (), anyString ());
		assertTrue (
				this.appUserManager.getAllUsers () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testEnableAppUser() {
		when (this.appUserDao.enableAppUser (anyString ())).thenReturn (1);
		assertEquals (1, this.appUserManager.enableAppUser ("jane.doe@example.org"));
		verify (this.appUserDao).enableAppUser (anyString ());
		assertTrue (
				this.appUserManager.getAllUsers () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testCheckIfEmailExists() {
		when (this.appUserDao.existsEmail (anyString ())).thenReturn (true);
		assertTrue (this.appUserManager.checkIfEmailExists ("jane.doe@example.org"));
		verify (this.appUserDao).existsEmail (anyString ());
		assertTrue (
				this.appUserManager.getAllUsers () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testGetAllUsers() {
		when (this.appUserDao.findAll ()).thenReturn (new ArrayList<AppUser> ());
		DataResult<List<AppUser>> actualAllUsers = this.appUserManager.getAllUsers ();
		assertTrue (actualAllUsers.getData ().isEmpty ());
		assertTrue (actualAllUsers.isSuccess ());
		assertEquals ("Data listelendi", actualAllUsers.getMessage ());
		verify (this.appUserDao).findAll ();
	}
}

