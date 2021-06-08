package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.business.concretes.ForgotPasswordManager;
import com.hazir.Hazirlaniyor.business.concretes.PaymentEmailManager;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.UpdatePasswordTokenDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Facade {
	public ShipmentService   shipmentService;
	public PaymentEmailManager  paymentEmailManager;
	public ValidatorService validatorService;
	public AfterPaymentSuccessService afterPaymentSuccessService;
	public CartDao cartDao;
	public ProductDao productDao;
	public AppUserDao appUserDao;
	public UpdatePasswordTokenDao updatePasswordTokenDao;
	public ProductCheckerService productCheckerService;
 @Autowired
	public Facade(ShipmentService shipmentService, PaymentEmailManager paymentEmailManager, AfterPaymentSuccessService afterPaymentSuccessService, CartDao cartDao, ProductDao productDao,AppUserDao appUserDao, UpdatePasswordTokenDao updatePasswordTokenDao,ValidatorService validatorService, ProductCheckerService productCheckerDal){
		this.shipmentService = shipmentService;
		this.paymentEmailManager = paymentEmailManager;
	  this.cartDao = cartDao;
	  this.productDao = productDao;
	  this.afterPaymentSuccessService = new AfterPaymentSuccessDal (shipmentService, paymentEmailManager, this.cartDao, this.productDao);
	  this.appUserDao = appUserDao;
	  this.updatePasswordTokenDao = updatePasswordTokenDao;
	  this.validatorService = new ValidatorDal (appUserDao,updatePasswordTokenDao);
	  this.productCheckerService = new ProductCheckerDal (productDao);




	}

}
