package com.hazir.Hazirlaniyor.Product;

import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.entity.concretes.AppUser;
import com.hazir.Hazirlaniyor.entity.concretes.AppUserRole;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(
		properties = {
				"spring.jpa.properties.javax.persistence.validation.mode=none"
		}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepoService {
	@Autowired
	private ProductDao productDao;
	@Test
	void itShouldSaveNewProduct() {
		Product product = new Product(1L, "Macbook Pro", "https://store.storeimages.cdn-apple.com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Electronic, 24);
		productDao.save(product);
		Optional<Product> optionalProduct= productDao.findProductByProductName(product.getProductName());
		assertThat (optionalProduct)
				.isPresent ()
				.hasValueSatisfying (c -> {
					assertThat (c.getProductName()).isEqualTo ("Macbook Pro");
					assertThat (c.getProductDescription ()).isEqualTo("Macbook Pro M1");
					assertThat (c.getProductPrice ()).isEqualTo (4500);
					assertThat (c.getUnitsInStock()).isEqualTo (24);
					assertThat (c.getProductCategory ()).isEqualTo (ProductCategory.Electronic);
					//assertThat(c).isEqualToComparingFieldByField(user1);
				});
	}
	@Test
	void itShouldSelectProductsByCategory(){
		// Given
		Product product = new Product(1L, "Macbook Pro", "https://store.storeimages.cdn-apple.com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Electronic, 24);

		// When
		productDao.save(product);

		// Then
		List<Product> productList = productDao.findProductByCategory(ProductCategory.Electronic);
		assertThat (productList.equals(productDao.findProductByCategory(ProductCategory.Electronic)));

	}
	@Test
	void itShouldNotSaveProductWhenProductNameIsNull() {
		Product product = new Product(1L, "Macbook Pro", "https://store.storeimages.cdn-apple.com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Electronic, 24);
		productDao.save(product);
		assertThat(product.getProductName()).isNotNull();
	}
	@Test
	void itShouldNotSaveProductWhenProductDescriptionIsNull() {
		Product product = new Product(1L, "Macbook Pro", "https://store.storeimages.cdn-apple.com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Electronic, 24);
		productDao.save(product);
		assertThat(product.getProductDescription ()).isNotNull();
	}
	@Test
	void itShouldNotSaveProductWhenProductImageIsNull() {
		Product product = new Product(1L, "Macbook Pro", "https://store.storeimages.cdn-apple.com/4982/as-images.apple" + ".com/is/mbp-spacegray-select-202011?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1613672894000", "Macbook Pro M1", 4500, ProductCategory.Electronic, 24);
		productDao.save(product);
		assertThat(product.getProductImage()).isNotNull();
	}

}
