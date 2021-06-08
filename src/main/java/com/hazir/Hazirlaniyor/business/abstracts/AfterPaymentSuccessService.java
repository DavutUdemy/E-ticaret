package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;
import com.hazir.Hazirlaniyor.entity.concretes.SuccessEmail;

public interface AfterPaymentSuccessService {
	public void updateStock(String email);
	public void sendSuccessEmail(SuccessEmail successEmail);
	String buildEmail(String firstName,String chargeId);
	public void postNewShipment(Shipment shipment);
	public void deleteCartByEmail(String email);

}
