package com.hazir.Hazirlaniyor.business.concretes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hazir.Hazirlaniyor.core.utillities.results.DataResult;
import com.hazir.Hazirlaniyor.core.utillities.results.Result;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductManager.class})
@ExtendWith(SpringExtension.class)
public class ProductManagerTest {
	@MockBean
	private ProductDao productDao;
	@Captor
	private ArgumentCaptor<Product> productArgumentCaptor;

	@Autowired
	private ProductManager productManager;

	@Test
	public void testGetAllProducts() {
		when (this.productDao.findAll ()).thenReturn (new ArrayList<Product> ());
		DataResult<List<Product>> actualAllProducts = this.productManager.getAllProducts ();
		assertTrue (actualAllProducts.getData ().isEmpty ());
		assertTrue (actualAllProducts.isSuccess ());
		assertEquals ("Data listelendi", actualAllProducts.getMessage ());
		verify (this.productDao).findAll ();
	}

	@Test
	public void testFindProductsByCategory() {
		when (this.productDao.findProductByCategory ((ProductCategory) any ())).thenReturn (new ArrayList<Product> ());
		DataResult<List<Product>> actualFindProductsByCategoryResult = this.productManager
				.findProductsByCategory (ProductCategory.Electronic);
		assertTrue (actualFindProductsByCategoryResult.getData ().isEmpty ());
		assertTrue (actualFindProductsByCategoryResult.isSuccess ());
		assertEquals ("Data Listelendi", actualFindProductsByCategoryResult.getMessage ());
		verify (this.productDao).findProductByCategory ((ProductCategory) any ());
		assertTrue (this.productManager
				.getAllProducts () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testAddProduct() {
		Product product = new Product(
				1L,
				"Macbook Pro",
				"https://store.storeimages.cdn-apple.com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000",
				"Macbook Pro M1",
				4500,
				ProductCategory.Electronic,
				24
		);
		given(productDao.findProductByProductName(product.getProductName())).willReturn(Optional.empty());
		productManager.addProduct(product);

		then(productDao).should().save(productArgumentCaptor.capture());
		Product productArgumentCaptorValue = productArgumentCaptor.getValue();
		assertThat(productArgumentCaptorValue).isEqualTo(product);

	}



	@Test
	public void testDeleteProductById() {
		doNothing ().when (this.productDao).deleteById ((Long) any ());
		Result actualDeleteProductByIdResult = this.productManager.deleteProductById (123L);
		assertEquals ("Urun basarili bir sekilde silindi", actualDeleteProductByIdResult.getMessage ());
		assertTrue (actualDeleteProductByIdResult.isSuccess ());
		verify (this.productDao).deleteById ((Long) any ());
		assertTrue (this.productManager
				.getAllProducts () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}

	@Test
	public void testFindProductByName() {
		Product product = new Product ();
		product.setProductPrice (1);
		product.setUnitsInStock (1);
		product.setId (123L);
		product.setProductName ("Product Name");
		product.setProductCategory (ProductCategory.Electronic);
		product.setProductDescription ("Product Description");
		product.setProductImage ("Product Image");
		Optional<Product> ofResult = Optional.<Product>of (product);
		when (this.productDao.findProductByProductName (anyString ())).thenReturn (ofResult);
		Optional<Product> actualFindProductByNameResult = this.productManager.findProductByName ("Product Name");
		assertSame (ofResult, actualFindProductByNameResult);
		assertTrue (actualFindProductByNameResult.isPresent ());
		verify (this.productDao).findProductByProductName (anyString ());
		assertTrue (this.productManager
				.getAllProducts () instanceof com.hazir.Hazirlaniyor.core.utillities.results.SuccessDataResult);
	}
}

