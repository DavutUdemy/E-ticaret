package com.hazir.Hazirlaniyor.business.concretes;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidatorManager implements Predicate<String> {
    @Override
    public boolean test(String s) {
//        TODO: Regex to validate email
        return true;
    }
}
