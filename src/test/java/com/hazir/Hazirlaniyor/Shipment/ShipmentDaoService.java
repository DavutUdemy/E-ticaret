package com.hazir.Hazirlaniyor.Shipment;

import com.hazir.Hazirlaniyor.business.concretes.EmailManager;
import com.hazir.Hazirlaniyor.business.concretes.ProductManager;
import com.hazir.Hazirlaniyor.business.concretes.ShipmentManager;
import com.hazir.Hazirlaniyor.business.concretes.StripeManager;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.PaymentSuccessDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ShipmentDao;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ShipmentDaoService {
	private ShipmentManager shipmentManager;
	@Captor
	private ArgumentCaptor<Shipment> shipmentArgumentCaptor;
	@Mock
	private ShipmentDao shipmentDao;
	@Mock
	private ProductDao productDao;
	@Mock
	private CartDao cartDao;
	@Mock
	private StripeManager paymentsService;
	@Mock
	private PaymentSuccessDao paymentSuccessDao;
	@Mock
	private  EmailManager emailSender;
	@Mock
	private AutoCloseable  autoCloseable;
	@BeforeEach
	void setUp() {
		AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
		shipmentManager= new ShipmentManager(shipmentDao,productDao,cartDao,paymentsService, paymentSuccessDao,emailSender);
	}
	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
}
