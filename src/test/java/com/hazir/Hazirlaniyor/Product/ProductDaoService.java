package com.hazir.Hazirlaniyor.Product;

import com.hazir.Hazirlaniyor.business.concretes.AppUserManager;
import com.hazir.Hazirlaniyor.business.concretes.ProductManager;
import com.hazir.Hazirlaniyor.core.utillities.results.DataResult;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.entity.concretes.AppUser;
import com.hazir.Hazirlaniyor.entity.concretes.AppUserRole;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.in;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ProductDaoService {
	@Captor
	private ArgumentCaptor<Product> productArgumentCaptor;
	private ProductManager productManager;
	@Mock
	private AutoCloseable  autoCloseable;
	@Mock
	private ProductDao productDao;
	@BeforeEach
	void setUp() {
		AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
		productManager= new ProductManager(productDao);
	}
	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	@ParameterizedTest
	@CsvSource("2")
	void deleteProductById(String input){
		this.productDao.deleteById(Long.parseLong(input));
		verify(productDao).deleteById(Long.parseLong(input));

	}
	@Test
	void itShouldNotSaveUserWhenProductNameIsExists(){
		Product product = new Product(1L, "Macbook Pro", "https://store.storeimages.cdn-apple.com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Electronic, 24);
		Product product2 = new Product(1L, "Macbook Pro", "https://store.storeimages.cdn-apple" + ".com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Electronic, 24);
		given(productDao.findProductByProductName(product.getProductName())).willReturn(Optional.of(product2));
		assertThatThrownBy(() -> productManager.addProduct(product))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining(String.format("There is a product with this name please try again!"));



		// Then
		then(productDao).should(never()).save(any());


	}
	@Test
	void itShouldSaveNewProduct() {
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
	void getAllProducts(){
		DataResult<List<Product>> getAllProducts = productManager.getAllProducts();
		verify(productDao).findAll();
		assertThat(productDao.findAll().size()).isEqualTo(getAllProducts.getData ().size ());
	}
	@Test
	@DisplayName("Should be same with dao")
	void itShouldSelectProductsByCategory(){
		DataResult<List<Product>> findProductsByCategory = productManager.findProductsByCategory(ProductCategory.Electronic);
		verify(productDao).findProductByCategory(ProductCategory.Electronic);
		assertThat(productDao.findProductByCategory(ProductCategory.Electronic).size()).isEqualTo(findProductsByCategory.getData ().size ());

	}


}
