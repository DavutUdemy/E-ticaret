package com.hazir.Hazirlaniyor.dataAccess.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.ConfirmationToken;
import com.hazir.Hazirlaniyor.entity.concretes.UpdatePasswordToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UpdatePasswordTokenDao extends JpaRepository<UpdatePasswordToken, Long> {

	Optional<UpdatePasswordToken> findByToken(String token);


}
