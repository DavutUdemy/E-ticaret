package com.hazir.Hazirlaniyor.business.concretes;

import com.hazir.Hazirlaniyor.dataAccess.abstracts.ConfirmationTokenDao;
import com.hazir.Hazirlaniyor.entity.concretes.ConfirmationToken;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenManager {

    private final ConfirmationTokenDao confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
