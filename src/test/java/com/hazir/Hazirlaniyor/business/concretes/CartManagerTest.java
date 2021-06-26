package com.hazir.Hazirlaniyor.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hazir.Hazirlaniyor.business.abstracts.AfterPaymentSuccessDal;
import com.hazir.Hazirlaniyor.business.abstracts.Facade;
import com.hazir.Hazirlaniyor.business.abstracts.ProductCheckerService;
import com.hazir.Hazirlaniyor.business.abstracts.RegistrationDal;
import com.hazir.Hazirlaniyor.business.abstracts.ShipmentService;
import com.hazir.Hazirlaniyor.business.abstracts.ValidatorDal;
import com.hazir.Hazirlaniyor.core.utillities.results.DataResult;
import com.hazir.Hazirlaniyor.core.utillities.results.Result;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.AppUserDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.PaymentSuccessDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ShipmentDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.UpdatePasswordTokenDao;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {CartManager.class, Facade.class})
@ExtendWith(SpringExtension.class)
public class CartManagerTest {
	@MockBean
	private CartDao cartDao;

	@Autowired
	private CartManager cartManager;

	@MockBean
	private Facade facade;
//TDD

	@Test
	public void testGetAllCarts() {
		when (this.cartDao.findAll ()).thenReturn (new ArrayList<Cart> ());
		DataResult<List<Cart>> actualAllCarts = this.cartManager.getAllCarts ();
		assertTrue (actualAllCarts.getData ().isEmpty ());
		assertTrue (actualAllCarts.isSuccess ());
		assertEquals ("Data listelendi", actualAllCarts.getMessage ());
		verify (this.cartDao).findAll ();
	}

	@Test
	public void testFindCartByUserEmail() {
		when (this.cartDao.findCartByEmail (anyString ())).thenReturn (new ArrayList<Cart> ());
		DataResult<List<Cart>> actualFindCartByUserEmailResult = this.cartManager
				.findCartByUserEmail ("jane.doe@example.org");
		assertTrue (actualFindCartByUserEmailResult.getData ().isEmpty ());
		assertTrue (actualFindCartByUserEmailResult.isSuccess ());
		assertEquals ("Data Listelendi", actualFindCartByUserEmailResult.getMessage ());
		verify (this.cartDao).findCartByEmail (anyString ());
		assertTrue (
				this.cartManager.getAllCarts () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testAddProductToCart() {
		Cart cart = new Cart ();
		cart.setEmail ("nailmemmedova12@gmail.com");
		cart.setProductPrice (4500);
		cart.setQuantity (1);
		cart.setId (123L);
		cart.setProductName ("Macbook Pro");
		cart.setProductCategory (ProductCategory.Electronic);
		cart.setProductDescription ("Macbook Pro M1");
		cart.setProductImage ("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000");
		cart.setTotalPrice (1);
		CartDao cartDao = mock (CartDao.class);
		when (cartDao.save ((Cart) any ())).thenReturn (cart);

		Product product = new Product ();
		product.setProductPrice (1);
		product.setUnitsInStock (1);
		product.setId (123L);
		product.setProductName ("Product Name");
		product.setProductCategory (ProductCategory.Electronic);
		product.setProductDescription ("Product Description");
		product.setProductImage ("Product Image");
		ProductDao productDao = mock (ProductDao.class);
		when (productDao.existsProductByProductName (anyString ())).thenReturn (Optional.<Product>of (product));
		ShipmentDao shipmentDao = mock (ShipmentDao.class);
		ProductDao productDao1 = mock (ProductDao.class);
		CartDao cartDao1 = mock (CartDao.class);
		StripeManager paymentsService = new StripeManager ();
		PaymentSuccessDao paymentSuccessDao = mock (PaymentSuccessDao.class);
		ShipmentManager shipmentService = new ShipmentManager (shipmentDao, productDao1, cartDao1, paymentsService,
				paymentSuccessDao, new EmailManager (null));

		PaymentEmailManager paymentEmailManager = new PaymentEmailManager (new JavaMailSenderImpl ());
		ShipmentManager shipmentService1 = new ShipmentManager (mock (ShipmentDao.class), mock (ProductDao.class),
				mock (CartDao.class), null, mock (PaymentSuccessDao.class), null);

		AfterPaymentSuccessDal afterPaymentSuccessService = new AfterPaymentSuccessDal (shipmentService1,
				new PaymentEmailManager (null), mock (CartDao.class), mock (ProductDao.class));

		CartDao cartDao2 = mock (CartDao.class);
		AppUserDao appUserDao = mock (AppUserDao.class);
		UpdatePasswordTokenDao updatePasswordTokenDao = mock (UpdatePasswordTokenDao.class);
		ValidatorDal validatorService = new ValidatorDal (mock (AppUserDao.class), mock (UpdatePasswordTokenDao.class));

		ProductCheckerService productCheckerDal = mock (ProductCheckerService.class);
		CartManager cartManager = new CartManager (cartDao,
				new Facade (shipmentService, paymentEmailManager, afterPaymentSuccessService, cartDao2, productDao, appUserDao,
						updatePasswordTokenDao, validatorService, productCheckerDal, new RegistrationDal ()));
		Result actualAddProductToCartResult = cartManager.addProductToCart (cart);
		assertEquals ("Ürün eklendi", actualAddProductToCartResult.getMessage ());
		assertTrue (actualAddProductToCartResult.isSuccess ());
		verify (cartDao).save ((Cart) any ());
		verify (productDao).existsProductByProductName (anyString ());
		assertTrue (cartManager.getAllCarts () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}



	@Test
	public void testDeleteCartById() {
		doNothing ().when (this.cartDao).deleteById ((Long) any ());
		Result actualDeleteCartByIdResult = this.cartManager.deleteCartById (123L);
		assertEquals ("Ürün Silindi", actualDeleteCartByIdResult.getMessage ());
		assertTrue (actualDeleteCartByIdResult.isSuccess ());
		verify (this.cartDao).deleteById ((Long) any ());
		assertTrue (
				this.cartManager.getAllCarts () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}
}

