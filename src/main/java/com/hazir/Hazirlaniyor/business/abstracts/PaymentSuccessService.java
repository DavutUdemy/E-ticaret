package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.ChargeParameter;
import com.hazir.Hazirlaniyor.entity.concretes.ChargeRequest;
import com.hazir.Hazirlaniyor.entity.concretes.Contact;
import com.stripe.exception.StripeException;

import org.springframework.ui.Model;

public interface PaymentSuccessService {
    String charge(Contact contact,ChargeParameter chargeParameter) throws StripeException;

}
