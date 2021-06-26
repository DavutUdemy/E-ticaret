package com.hazir.Hazirlaniyor.AppUser;

import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.entity.concretes.AppUser;
import com.hazir.Hazirlaniyor.entity.concretes.AppUserRole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(
		properties = {
				"spring.jpa.properties.javax.persistence.validation.mode=none"
		}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepoTest {
	@Autowired
	private AppUserDao appUserDao;


	@Test
	void itShouldSelectUserByEmail() {
		String email = "davud2007@gmail.com";
		// Given
		AppUser user1 = new AppUser ("Davud", "Mamedovi", email, "514010191", AppUserRole.USER);

		// When
		appUserDao.save(user1);

		// Then
		Optional<AppUser> optionalUser = appUserDao.findUserByEmail (email);
		assertThat (optionalUser)
				.isPresent ()
				.hasValueSatisfying (c -> {
					assertThat (c).isEqualToComparingFieldByField (user1);
				});
	}

	@Test
	void itShouldSaveNewUser() {
		String email = "davud2007@gmail.com";
		AppUser user1 = new AppUser ("Davud", "Mamedovi", email, "514010191", AppUserRole.USER);
		appUserDao.save (user1);
		Optional<AppUser> optionalUser = appUserDao.findUserByEmail (email);
		assertThat (optionalUser)
				.isPresent ()
				.hasValueSatisfying (c -> {
					assertThat (c.getFirstName ()).isEqualTo ("Davud");
					assertThat (c.getLastName ()).isEqualTo ("Mamedovi");
					assertThat (c.getEmail ()).isEqualTo ("davud2007@gmail.com");
					assertThat (c.getPassword ()).isEqualTo ("514010191");

					//assertThat(c).isEqualToComparingFieldByField(user1);
				});
	}
	@Test
	void itShouldNotSaveUserWhenFirstNameIsNull() {
		String email = "davud2007@gmail.com";
		AppUser user1 = new AppUser ("Davud", "Mamedovi", null, "514010191", AppUserRole.USER);
		appUserDao.save(user1);
		assertThat(user1.getFirstName()).isNotNull();
	}
	@Test
	void itShouldNotSaveUserWhenLastNameIsNull() {
		String email = "davud2007@gmail.com";
		AppUser user1 = new AppUser ("Davud", "Mamedovi", null, "514010191", AppUserRole.USER);
		appUserDao.save(user1);
		assertThat(user1.getLastName()).isNotNull();
	}
	@Test
	void itShouldNotSaveUserWhenPasswordIsNull() {
		String email = "davud2007@gmail.com";
		AppUser user1 = new AppUser ("Davud", "Mamedovi", null, "514010191", AppUserRole.USER);
		appUserDao.save(user1);
		assertThat(user1.getPassword ()).isNotNull();
	}

	@Test
	void itShouldNotSaveUserWhenEmailIsNull() {
		String email = "davud2007@gmail.com";
		AppUser user1 = new AppUser ("Davud", "Mamedovi", null, "514010191", AppUserRole.USER);
		appUserDao.save(user1);
 		assertThat(user1.getEmail()).isNotNull();
	}
	}