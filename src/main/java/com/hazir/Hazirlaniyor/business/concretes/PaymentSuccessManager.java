package com.hazir.Hazirlaniyor.business.concretes;

import com.hazir.Hazirlaniyor.business.abstracts.Facade;
import com.hazir.Hazirlaniyor.business.abstracts.PaymentSuccessService;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.ChargeParameter;
import com.hazir.Hazirlaniyor.entity.concretes.ChargeRequest;
import com.hazir.Hazirlaniyor.entity.concretes.Contact;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;
import com.hazir.Hazirlaniyor.entity.concretes.SuccessEmail;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public  class PaymentSuccessManager implements PaymentSuccessService {
	private final Facade facade;
	private final StripeManager paymentsService;
	private final CartDao cartDao;


	@Override
	public String charge(Contact contact,ChargeParameter chargeParameter) throws StripeException {
		List<Cart> findCartByUserEmail = this.cartDao.findCartByEmail (contact.getUserEmail());
		ChargeRequest chargeRequest = new ChargeRequest ("Example Charge", ChargeRequest.Currency.USD);
		Charge charge = paymentsService.charge(chargeRequest);
		String emailBody = this.facade.afterPaymentSuccessService.buildEmail (contact.getFirstName (), charge.getId ());
		SuccessEmail successEmail = new SuccessEmail (contact.getUserEmail (),emailBody, contact.getFirstName (), charge.getId ());
		this.facade.afterPaymentSuccessService.sendSuccessEmail(successEmail);
		Shipment shipment = new Shipment (LocalDateTime.now (),findCartByUserEmail,contact.getShipmentStatus(), contact.getFirstName(),contact.getLastName(), contact.getPostalCode(),contact.getFullAdress(),contact.getPhoneNumber(),contact.getUserEmail());
		this.facade.afterPaymentSuccessService.postNewShipment(shipment);
		this.facade.afterPaymentSuccessService.updateStock (contact.getUserEmail ());
		this.facade.afterPaymentSuccessService.deleteCartByEmail (contact.getLastName ());
		return "Success";
	}

}