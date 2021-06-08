package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();
    List<Cart> findCartByUserEmail(String email);
    void addProductToCart(Cart cart);
    void deleteCartById(Long Id);

 }
