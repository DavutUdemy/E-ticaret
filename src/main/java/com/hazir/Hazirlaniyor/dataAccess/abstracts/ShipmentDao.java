package com.hazir.Hazirlaniyor.dataAccess.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Shipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShipmentDao extends JpaRepository<Shipment,Long> {

	@Query("SELECT s FROM Shipment s WHERE s.firstName=?1")
	List<Shipment> findShipmentByName(String firstName);
	@Query("SELECT s FROM Shipment s WHERE s.cancelOrder=?1")
	List<Shipment> findShipmentByCanceledOrder(Boolean cancelOrder);
	@Transactional
	@Modifying

	@Query("UPDATE Shipment a " +
			"SET a.cancelOrder = TRUE WHERE a.Id=?1 AND a.cancelOrder = FALSE ")
	int cancelOrder(Long Id);


}
