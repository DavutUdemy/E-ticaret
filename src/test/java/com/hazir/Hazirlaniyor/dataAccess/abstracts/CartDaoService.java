package com.hazir.Hazirlaniyor.dataAccess.abstracts;

import com.hazir.Hazirlaniyor.business.concretes.CartManager;
import com.hazir.Hazirlaniyor.core.utillities.results.DataResult;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
		properties = {
				"spring.jpa.properties.javax.persistence.validation.mode=none"
		}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartDaoService {
	@Autowired
	private CartDao cartDao;

	@Test
	void itShouldSaveProductToCart(){
		String productImage = "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-space-gray-select-201810?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1603332211000";
		Cart cart = new Cart(4L,"Macbook Air",productImage,"Macbook Air M1",1300,
				ProductCategory.Electronic,3,0,"nailmemmedova12@gmail.com");
		cartDao.save(cart);
		Optional<Cart> optionalCart = cartDao.findById(4L);
		assertThat(optionalCart)
				.isPresent()
				.hasValueSatisfying(c -> {
					assertThat(c).isEqualToComparingFieldByField(cart);
				});
	}
	@Test
	void itShouldNotSaveProductWhenProductNameIsNull() {
		String productImage = "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-space-gray-select-201810?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1603332211000";
		Cart cart = new Cart(4L,"Macbook Air",productImage,"Macbook Air M1",1300, ProductCategory.Electronic,3,0,"nailmemmedova12@gmail.com");
		cartDao.save(cart);
		AssertionsForClassTypes.assertThat(cart.getProductName()).isNotNull();
	}
	@Test
	void itShouldNotSaveProductWhenProductDescriptionIsNull() {
		String productImage = "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-space-gray-select-201810?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1603332211000";
		Cart cart = new Cart(4L,"Macbook Air",productImage,"Macbook Air M1",1300, ProductCategory.Electronic,3,0,"nailmemmedova12@gmail.com");
		cartDao.save(cart);
		AssertionsForClassTypes.assertThat(cart.getProductDescription ()).isNotNull();
	}
	@Test
	void itShouldNotSaveToCartWhenProductImageIsNull() {
		String productImage = "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-space-gray-select-201810?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1603332211000";
		Cart cart = new Cart(4L,"Macbook Air",productImage,"Macbook Air M1",1300, ProductCategory.Electronic,3,0,"nailmemmedova12@gmail.com");
		cartDao.save(cart);
		AssertionsForClassTypes.assertThat(cart.getProductImage()).isNotNull();
	}

@Test
 void itShouldSelectCartByEmail() {

}

}
