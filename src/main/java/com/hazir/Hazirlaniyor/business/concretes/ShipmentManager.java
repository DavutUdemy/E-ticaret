package com.hazir.Hazirlaniyor.business.concretes;

import com.hazir.Hazirlaniyor.business.abstracts.ShipmentService;
import com.hazir.Hazirlaniyor.core.utillities.BadRequest.BadRequestException;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.PaymentSuccessDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ShipmentDao;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
  public class ShipmentManager implements ShipmentService {
 	private final ShipmentDao       shipmentDao;
 	private final ProductDao        productDao;
 	private final CartDao           cartDao;
	private final StripeManager     paymentsService;
 	private final PaymentSuccessDao paymentSuccessDao;
 	private final EmailManager      emailSender;




	@Override
	public void addNewShipment(Shipment shipment) {

		this.shipmentDao.save(shipment);
	}

	@Override
	public void deleteById(Long Id) {
  this.shipmentDao.deleteById (Id);
	}

	@Override
	public List<Shipment> getAllShipments() {
		return this.shipmentDao.findAll ();
	}

	@Override
	public List<Shipment> findShipmentsByFirstName(String firstName) {
		return this.shipmentDao.findShipmentByName (firstName);
	}

	@Override
	public List<Shipment> getCanceledShipment() {
 			return this.shipmentDao.findShipmentByCanceledOrder (false);
		}

	@Override
	public Integer cancelShipment(Shipment shipment,Long Id) {
		LocalDateTime expiresDate = shipment.getPaymentDate ().plusDays (4);

		if(expiresDate.isBefore(LocalDateTime.now())){
			throw new BadRequestException ("You can not cancel your order");
		}
		return this.shipmentDao.cancelOrder(Id);
	}



}
