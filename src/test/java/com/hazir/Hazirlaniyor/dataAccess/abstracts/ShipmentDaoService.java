package com.hazir.Hazirlaniyor.dataAccess.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(
		properties = {
				"spring.jpa.properties.javax.persistence.validation.mode=none"
		}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ShipmentDaoService {
	@Autowired
	private ShipmentDao shipmentDao;
 	@Test
	void itShouldSaveNewShipment() {
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

		this.shipmentDao.save(shipment);
		System.out.println(shipmentDao.findById ( 123L));
		Optional<Shipment> optionalShipment= shipmentDao.findById (123L);
		assertThat (optionalShipment)
.isPresent ()
				.hasValueSatisfying (c -> {
					assertThat (c.getEmail()).isEqualTo ("jane.doe@example.org");
					assertThat (c.getFirstName ()).isEqualTo("Jane");
					assertThat (c.getPhoneNumber ()).isEqualTo ("4105551212");
					assertThat (c.getFullAdress ()).isEqualTo ("Full Adress");
					//assertThat(c).isEqualToComparingFieldByField(user1);
				});
	}
}
