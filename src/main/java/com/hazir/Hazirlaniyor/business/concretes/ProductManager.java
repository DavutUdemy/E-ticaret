package com.hazir.Hazirlaniyor.business.concretes;

import com.hazir.Hazirlaniyor.business.abstracts.ProductService;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductManager implements ProductService {
    private final ProductDao productDao;

    @Override
    public List<Product> getAllProducts() {
        return this.productDao.findAll();
    }

    @Override
    public List<Product> findProductsByCategory(ProductCategory productCategory) {
        return this.productDao.findProductByCategory(productCategory);
    }

    @Override
    public void AddProduct(Product product) {
     this.productDao.save(product);
    }

    @Override
    public void DeleteProduct(Long Id) {
  this.productDao.deleteById(Id);
    }
}
