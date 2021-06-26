package com.hazir.Hazirlaniyor.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hazir.Hazirlaniyor.core.utillities.results.DataResult;
import com.hazir.Hazirlaniyor.core.utillities.results.ErrorResult;
import com.hazir.Hazirlaniyor.core.utillities.results.Result;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.PaymentSuccessDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ShipmentDao;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmailManager.class, ShipmentManager.class, StripeManager.class})
@ExtendWith(SpringExtension.class)
public class ShipmentManagerTest {
	@MockBean
	private CartDao cartDao;

	@MockBean
	private EmailManager emailManager;

	@MockBean
	private PaymentSuccessDao paymentSuccessDao;

	@MockBean
	private ProductDao productDao;

	@MockBean
	private ShipmentDao shipmentDao;

	@Autowired
	private ShipmentManager shipmentManager;

	@MockBean
	private StripeManager stripeManager;

	@Test
	public void testAddNewShipment() {
		Shipment shipment = new Shipment ();
		shipment.setLastName ("Doe");
		shipment.setPostalCode ("Postal Code");
		shipment.setEmail ("jane.doe@example.org");
		shipment.setFullAdress ("Full Adress");
		shipment.setId (123L);
		shipment.setShipmentStatus ("Shipment Status");
		shipment.setPhoneNumber ("4105551212");
		shipment.setPaymentDate (LocalDateTime.of (1, 1, 1, 1, 1));
		shipment.setCart (new ArrayList<Cart> ());
		shipment.setFirstName ("Jane");
		shipment.setCancelOrder (true);
		when (this.shipmentDao.save ((Shipment) any ())).thenReturn (shipment);
		Result actualAddNewShipmentResult = this.shipmentManager.addNewShipment (new Shipment ());
		assertEquals ("basarili bir sekilde eklendi", actualAddNewShipmentResult.getMessage ());
		assertTrue (actualAddNewShipmentResult.isSuccess ());
		verify (this.shipmentDao).save ((Shipment) any ());
		assertTrue (this.shipmentManager
				.getAllShipments () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testDeleteById() {
		doNothing ().when (this.shipmentDao).deleteById ((Long) any ());
		Result actualDeleteByIdResult = this.shipmentManager.deleteById (123L);
		assertEquals ("basarili bir sekilde silindi", actualDeleteByIdResult.getMessage ());
		assertTrue (actualDeleteByIdResult.isSuccess ());
		verify (this.shipmentDao).deleteById ((Long) any ());
		assertTrue (this.shipmentManager
				.getAllShipments () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testGetAllShipments() {
		when (this.shipmentDao.findAll ()).thenReturn (new ArrayList<Shipment> ());
		DataResult<List<Shipment>> actualAllShipments = this.shipmentManager.getAllShipments ();
		assertTrue (actualAllShipments.getData ().isEmpty ());
		assertTrue (actualAllShipments.isSuccess ());
		assertEquals ("Data listelendi", actualAllShipments.getMessage ());
		verify (this.shipmentDao).findAll ();
		assertTrue (this.shipmentManager
				.getCanceledShipment () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testFindShipmentsByFirstName() {
		when (this.shipmentDao.findShipmentByName (anyString ())).thenReturn (new ArrayList<Shipment> ());
		DataResult<List<Shipment>> actualFindShipmentsByFirstNameResult = this.shipmentManager
				.findShipmentsByFirstName ("Jane");
		assertTrue (actualFindShipmentsByFirstNameResult.getData ().isEmpty ());
		assertTrue (actualFindShipmentsByFirstNameResult.isSuccess ());
		assertEquals ("Data listelendi", actualFindShipmentsByFirstNameResult.getMessage ());
		verify (this.shipmentDao).findShipmentByName (anyString ());
		assertTrue (this.shipmentManager
				.getAllShipments () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testGetCanceledShipment() {
		when (this.shipmentDao.findShipmentByCanceledOrder ((Boolean) any ())).thenReturn (new ArrayList<Shipment> ());
		DataResult<List<Shipment>> actualCanceledShipment = this.shipmentManager.getCanceledShipment ();
		assertTrue (actualCanceledShipment.getData ().isEmpty ());
		assertTrue (actualCanceledShipment.isSuccess ());
		assertEquals ("Data listelendi", actualCanceledShipment.getMessage ());
		verify (this.shipmentDao).findShipmentByCanceledOrder ((Boolean) any ());
		assertTrue (this.shipmentManager
				.getAllShipments () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testShowError() {
		ErrorResult actualShowErrorResult = this.shipmentManager.showError ("Msg");
		assertEquals ("Msg", actualShowErrorResult.getMessage ());
		assertFalse (actualShowErrorResult.isSuccess ());
	}



	@Test
	public void testCancelShipment() {
		when (this.shipmentDao.cancelOrder ((Long) any ())).thenReturn (1);
		Shipment shipment = mock (Shipment.class);
		when (shipment.getPaymentDate ()).thenReturn (LocalDateTime.of (1, 1, 1, 1, 1));
		Result actualCancelShipmentResult = this.shipmentManager.cancelShipment (shipment, 123L);
		assertEquals ("Data listelendi", actualCancelShipmentResult.getMessage ());
		assertTrue (actualCancelShipmentResult.isSuccess ());
		verify (this.shipmentDao).cancelOrder ((Long) any ());
		verify (shipment).getPaymentDate ();
		assertTrue (this.shipmentManager
				.getAllShipments () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}
}

