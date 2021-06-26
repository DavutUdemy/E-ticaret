package com.hazir.Hazirlaniyor.Cart;

import com.hazir.Hazirlaniyor.business.abstracts.AfterPaymentSuccessService;
import com.hazir.Hazirlaniyor.business.abstracts.Facade;
import com.hazir.Hazirlaniyor.business.abstracts.ProductCheckerDal;
import com.hazir.Hazirlaniyor.business.abstracts.ProductCheckerService;
import com.hazir.Hazirlaniyor.business.abstracts.RegistrationService;
import com.hazir.Hazirlaniyor.business.abstracts.ShipmentService;
import com.hazir.Hazirlaniyor.business.abstracts.ValidatorService;
import com.hazir.Hazirlaniyor.business.concretes.CartManager;
import com.hazir.Hazirlaniyor.business.concretes.PaymentEmailManager;
import com.hazir.Hazirlaniyor.core.utillities.results.DataResult;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.UpdatePasswordTokenDao;
import com.hazir.Hazirlaniyor.entity.concretes.AppUser;
import com.hazir.Hazirlaniyor.entity.concretes.AppUserRole;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CartDaoService {
	@Mock
	private  ProductDao productDao;
	@Captor
	private ArgumentCaptor<Cart> cartArgumentCaptor;
	private CartManager          cartManager;
	@Mock
	private AutoCloseable        autoCloseable;
	@Mock
	private CartDao              cartDao;
	@Mock
	private ProductCheckerDal    productManager;
	@Mock
 	private Facade               facade;
	@Mock
	public  ShipmentService      shipmentService;
	@Mock
	public   PaymentEmailManager paymentEmailManager;
	@Mock
	public   ValidatorService validatorService;
	@Mock
	public   AfterPaymentSuccessService afterPaymentSuccessService;
	@Mock
	public   AppUserDao appUserDao;
	@Mock
	public   UpdatePasswordTokenDao  updatePasswordTokenDao;
	@Mock
	public   ProductCheckerService  productCheckerService;
	@Mock
	public   RegistrationService     registrationService;




	@BeforeEach
	void setUp() {
		AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
		facade = new Facade (shipmentService,paymentEmailManager, afterPaymentSuccessService, cartDao,
			productDao, appUserDao,
		updatePasswordTokenDao, validatorService, productCheckerService, registrationService);
		cartManager= new CartManager(cartDao,facade);
	}
	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	@Test
	void getAllCarts(){
		DataResult<List<Cart>> getAllCarts = cartManager.getAllCarts();
		verify(cartDao).findAll();
		assertThat(cartDao.findAll().size()).isEqualTo(getAllCarts.getData().size ());
	}
	@Test
	void itShouldSaveToCart(){
		String productImage = "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-space-gray-select-201810?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1603332211000";
		Cart cart = new Cart("Macbook Air",productImage,"Macbook Air M1",1300, ProductCategory.Electronic,3,0,"nailmemmedova12@gmail.com");
		given(productDao.checkIfProductExists(cart.getProductName ())).willReturn(true);
   cartManager.addProductToCart(cart);
	  then(cartDao).should().save(cartArgumentCaptor.capture());
	 Cart cartArgumentCaptorValue = cartArgumentCaptor.getValue();
		  assertThat(cartArgumentCaptorValue).isEqualTo(cart);
	}
	@ParameterizedTest
	@CsvSource("2")
	void itShouldDeleteCartById(String input){
			this.productDao.deleteById(Long.parseLong(input));
			verify(productDao).deleteById(Long.parseLong(input));
	}



}
