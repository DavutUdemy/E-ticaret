package com.hazir.Hazirlaniyor.api.controllers;

import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private MockMvc    mockMvc;

	@Test
	void itShouldRetrieveProductByProductName() throws Exception {
		Product product = new Product (1L, "Macbook Pro", "https://store.storeimages.cdn-apple.com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Electronic, 24);
		this.productDao.save (product);
		ResultActions userRegResultActions = mockMvc.perform (get ("/api/v1/products/productname" + "/Macbook Pro")
				.contentType (MediaType.APPLICATION_JSON));
		userRegResultActions.andExpect (status ().isOk ())
				.andExpect (jsonPath ("productName").value ("Macbook Pro"))
				.andExpect (jsonPath ("productPrice").value (4500))
				.andExpect (jsonPath ("productCategory").value ("Electronic")).andExpect (jsonPath (
				"unitsInStock").value (24));

	}

	@Test
	void itShouldGetAllProducts() throws Exception {
		ResultActions userRegResultActions = mockMvc.perform (get ("/api/v1/products").contentType (MediaType.APPLICATION_JSON));
		userRegResultActions.andExpect (status ().isOk ());
	}

	@Test
	void itShouldRetrieveProductByProductCategory() throws Exception {
		Product product = new Product (1L, "Macbook Pro", "https://store.storeimages.cdn-apple" +
				".com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Book, 24);
		this.productDao.save (product);
		ResultActions userRegResultActions = mockMvc.perform (get ("/api/v1/products/Book")
				.contentType (MediaType.APPLICATION_JSON));
		userRegResultActions.andExpect (status ().isOk ());

	}
}