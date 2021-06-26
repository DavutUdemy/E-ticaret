package com.hazir.Hazirlaniyor.AppUser;

import com.hazir.Hazirlaniyor.business.abstracts.Facade;
import com.hazir.Hazirlaniyor.business.concretes.AppUserManager;
import com.hazir.Hazirlaniyor.business.concretes.ConfirmationTokenManager;
import com.hazir.Hazirlaniyor.business.concretes.EmailValidatorManager;
import com.hazir.Hazirlaniyor.core.utillities.results.DataResult;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.entity.concretes.AppUser;
import com.hazir.Hazirlaniyor.entity.concretes.AppUserRole;
import com.hazir.Hazirlaniyor.entity.concretes.ForgotPassword;

import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.postgresql.hostchooser.HostRequirement.any;

public class AppUserDaoService {
	@Captor
	private ArgumentCaptor<AppUser> userArgumentCaptor;
	private AppUserManager appUserManager;
	@Mock
	private EmailValidatorManager emailValidator;
	@Mock
	private AppUserDao appUserRepository;
	@Mock
	private AutoCloseable  autoCloseable;
  @Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Mock
	private ConfirmationTokenManager confirmationTokenService;
  @Mock
	private Facade facade;
	@BeforeEach
	void setUp() {
		AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
		appUserManager= new AppUserManager(appUserRepository,bCryptPasswordEncoder, confirmationTokenService,facade,emailValidator);
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

	  @Test
	  void itShouldSaveNewUser() {
		String email = "zeynepbalioglu@gmail.com";
		  AppUser appUser = new AppUser (
				  "Davud",
				  "Mamedovi",
				  email,
				  "514010191",
				  AppUserRole.USER
		  );
		  given(appUserRepository.getUserByEmail(email)).willReturn(Optional.empty());
		  given(emailValidator.test(email)).willReturn(true);
		  appUserManager.signUpUser(appUser);

		  then(appUserRepository).should().save(userArgumentCaptor.capture());
		  AppUser userArgumentCaptorValue = userArgumentCaptor.getValue();
		  assertThat(userArgumentCaptorValue).isEqualTo(appUser);


	  }
	@Test
	void itShouldNotSaveUserWhenEmailExists() {
		String email = "davud2007@gmail.com";
		AppUser user1 = new AppUser ("Davud", "Mamedovi", email, "514010191", AppUserRole.USER);
		AppUser user2 = new AppUser ("Ali", "Zatina", email, "514010191", AppUserRole.USER);

		// ... an existing customer is retuned
		given(appUserRepository.findByEmail(email))
				.willReturn(Optional.of(user2));

		//... Valid phone number
		given(emailValidator.test(email)).willReturn(true);

		// When
		//appUserManager.signUpUser(user1);
		assertThatThrownBy(() -> appUserManager.signUpUser(user1))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining(String.format("There is a user with this email please try again"));



		// Then
		then(appUserRepository).should(never()).save(any());
	}

	@Test
	void itShouldNotSaveNewUserWhenEmailIsInvalid() {
		String email = "catsacademyedu@gmail.com";
		AppUser user1 = new AppUser ("Davud", "Mamedovi", email, "514010191", AppUserRole.USER);
		AppUser user2 = new AppUser ("Meryem", "Mamedovi", email, "123456969", AppUserRole.USER);
		given(emailValidator.test(email)).willReturn(false);
		assertThatThrownBy(() -> appUserManager.signUpUser(user1))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("Email" + email + " is not valid");
		then(appUserRepository).shouldHaveNoInteractions();




	}

	  @Test
	  void getAllUsers(){

		DataResult<List<AppUser>> allUsers = appUserManager.getAllUsers ();
		verify(appUserRepository).findAll();
		assertThat(appUserRepository.findAll().size()).isEqualTo(allUsers.getData ().size ());

	}

}
