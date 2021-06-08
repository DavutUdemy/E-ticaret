package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Shipment;

import java.util.List;

public interface ShipmentService {
	void addNewShipment(Shipment shipment);
	void deleteById(Long Id);
	List<Shipment> getAllShipments();
	List<Shipment> findShipmentsByFirstName(String firstName);
	List<Shipment> getCanceledShipment ();
	Integer cancelShipment(Shipment shipment,Long  Id);


}
