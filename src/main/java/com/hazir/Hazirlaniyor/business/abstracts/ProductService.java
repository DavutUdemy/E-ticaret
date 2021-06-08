package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import java.util.List;

public interface ProductService {
  List<Product> getAllProducts();
  List<Product> findProductsByCategory(ProductCategory productCategory);
  void AddProduct(Product product);
  void DeleteProduct(Long Id);
}
