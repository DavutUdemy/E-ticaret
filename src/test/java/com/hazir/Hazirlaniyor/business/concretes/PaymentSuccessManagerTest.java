package com.hazir.Hazirlaniyor.business.concretes;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hazir.Hazirlaniyor.business.abstracts.AfterPaymentSuccessDal;
import com.hazir.Hazirlaniyor.business.abstracts.Facade;
import com.hazir.Hazirlaniyor.business.abstracts.ProductCheckerService;
import com.hazir.Hazirlaniyor.business.abstracts.RegistrationDal;
import com.hazir.Hazirlaniyor.business.abstracts.ValidatorDal;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.PaymentSuccessDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ShipmentDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.UpdatePasswordTokenDao;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.ChargeParameter;
import com.hazir.Hazirlaniyor.entity.concretes.ChargeRequest;
import com.hazir.Hazirlaniyor.entity.concretes.Contact;
import com.stripe.exception.StripeException;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.ConcurrentModel;

public class PaymentSuccessManagerTest {
	@Test
	public void testCharge() throws StripeException {
		CartDao cartDao = mock (CartDao.class);
		when (cartDao.findCartByEmail (anyString ())).thenReturn (new ArrayList<Cart> ());
		ShipmentDao shipmentDao = mock (ShipmentDao.class);
		ProductDao productDao = mock (ProductDao.class);
		CartDao cartDao1 = mock (CartDao.class);
		StripeManager paymentsService = new StripeManager();
		PaymentSuccessDao paymentSuccessDao = mock (PaymentSuccessDao.class);
		ShipmentManager shipmentService = new ShipmentManager (shipmentDao, productDao, cartDao1, paymentsService,
				paymentSuccessDao, new EmailManager (null));

		PaymentEmailManager paymentEmailManager = new PaymentEmailManager (new JavaMailSenderImpl ());
		ShipmentManager shipmentService1 = new ShipmentManager (mock (ShipmentDao.class), mock (ProductDao.class),
				mock (CartDao.class), null, mock (PaymentSuccessDao.class), null);

		AfterPaymentSuccessDal afterPaymentSuccessService = new AfterPaymentSuccessDal (shipmentService1,
				new PaymentEmailManager (null), mock (CartDao.class), mock (ProductDao.class));

		CartDao cartDao2 = mock (CartDao.class);
		ProductDao productDao1 = mock (ProductDao.class);
		AppUserDao appUserDao = mock (AppUserDao.class);
		UpdatePasswordTokenDao updatePasswordTokenDao = mock (UpdatePasswordTokenDao.class);
		ValidatorDal validatorService = new ValidatorDal (mock (AppUserDao.class), mock (UpdatePasswordTokenDao.class));

		ProductCheckerService productCheckerDal = mock (ProductCheckerService.class);
		Facade facade = new Facade (shipmentService, paymentEmailManager, afterPaymentSuccessService, cartDao2, productDao1,
				appUserDao, updatePasswordTokenDao, validatorService, productCheckerDal, new RegistrationDal ());

		PaymentSuccessManager paymentSuccessManager = new PaymentSuccessManager (facade, new StripeManager (), cartDao);
		Contact contact = new Contact ();
		ChargeRequest mChargeRequest = new ChargeRequest ("The characteristics of someone or something",
				ChargeRequest.Currency.USD);

		paymentSuccessManager.charge (contact,
				new ChargeParameter ("jane.doe@example.org", mChargeRequest, new ConcurrentModel ()));
		verify (cartDao).findCartByEmail (anyString ());
	}
}

