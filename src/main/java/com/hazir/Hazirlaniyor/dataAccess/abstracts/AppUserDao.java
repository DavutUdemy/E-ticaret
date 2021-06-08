package com.hazir.Hazirlaniyor.dataAccess.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserDao extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
    @Query("SELECT s FROM AppUser s WHERE s.email=?1")
 boolean findUserByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
	@Transactional
	@Modifying
	@Query("UPDATE AppUser a " +
			"SET a.password = ?1 WHERE a.email = ?2")
	int updatePassword(String password,String email);
  @Query("SELECT  s from  AppUser s WHERE  s.email=?1")
	boolean existsEmail(String email);
	@Query("SELECT s FROM AppUser s WHERE s.email=?1")
	Optional<AppUser> getUserByEmail(String email);

}
