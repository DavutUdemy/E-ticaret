package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.core.utillities.BadRequest.BadRequestException;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.entity.concretes.AppUser;
import com.hazir.Hazirlaniyor.entity.concretes.Product;

import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ProductCheckerDal implements ProductCheckerService{
	private final ProductDao productDao;

	public ProductCheckerDal(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public void existProduct(String productName) {
		Optional<Product> findProductByEmail = this.productDao.existsProductByProductName (productName);
		if(!findProductByEmail.isPresent ()){
			//this.productDao.deleteByProductName(productName);
			throw new BadRequestException("Sorry We don't sell this product");
		}


		System.out.println ("Working");

	}
}
