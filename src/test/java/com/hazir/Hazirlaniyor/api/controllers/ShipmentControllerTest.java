package com.hazir.Hazirlaniyor.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ShipmentDao;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShipmentControllerTest {
	@Autowired
	private ShipmentDao shipmentDao;
	@Autowired
	private MockMvc     mockMvc;
	@Test
	void itShouldGetShipmentByName() throws Exception {
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
		shipment.setCancelOrder (false);
		this.shipmentDao.save(shipment);
		ResultActions userRegResultActions = mockMvc.perform (get ("/api/v1/shipment" + "/Jane")
				.contentType (MediaType.APPLICATION_JSON));
		userRegResultActions.andExpect (status ().isOk ())
				.andExpect (jsonPath ("$.data[0].firstName").value ("Jane"))
				.andExpect (jsonPath ("$.data[0].lastName").value ("Doe"))
				.andExpect (jsonPath ("$.data[0].postalCode").value ("Postal Code")).andExpect (jsonPath (
				"$.data[0].fullAdress").value ("Full Adress"));

	}
	@Test
	void itShouldAddNewShipment() throws Exception {
		Shipment shipment = new Shipment ();
		shipment.setId (4L);
		shipment.setLastName ("Doe");
		shipment.setPostalCode ("Postal Code");
		shipment.setEmail ("jane.doe@example.org");
		shipment.setFullAdress ("Full Adress");
		shipment.setId (123L);
		shipment.setShipmentStatus ("Shipment Status");
		shipment.setPhoneNumber ("4105551212");
		shipment.setPaymentDate (null);
		shipment.setCart (new ArrayList<Cart> ());
		shipment.setFirstName ("Jane");
		shipment.setCancelOrder (false);
		ResultActions shipmentRegResultActions = mockMvc.perform (post ("/api/v1/shipment" +
				"/addNewShipment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Objects.requireNonNull(asJsonString(shipment))));
		shipmentRegResultActions.andExpect (status ().isOk ());
		assertThat(shipmentDao.findById(shipment.getId()))
				.isPresent()
				.hasValueSatisfying(p -> assertThat(p).isEqualToComparingFieldByField(shipment));




	}
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			System.out.println(jsonContent);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
