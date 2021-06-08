package com.hazir.Hazirlaniyor.dataAccess.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.PaymentSuccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSuccessDao  extends JpaRepository<PaymentSuccess,Long> {

}
//Clean Architecture,Clean