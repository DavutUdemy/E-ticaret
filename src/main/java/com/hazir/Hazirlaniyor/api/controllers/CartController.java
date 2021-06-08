package com.hazir.Hazirlaniyor.api.controllers;

import com.hazir.Hazirlaniyor.business.concretes.CartManager;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/v1/Cart")
@AllArgsConstructor

public class CartController {
    private final CartManager cartManager;
    @GetMapping
    public List<Cart> getAllCarts(){
        return this.cartManager.getAllCarts();
    }
    @GetMapping(path = "{email}")
    public List<Cart>findCartByUserEmail(@PathVariable("email")String email){
        return this.cartManager.findCartByUserEmail(email);
    }
    @DeleteMapping(path ="{id}")
    public void deleteCartById(@PathVariable("id")Long Id){
          this.cartManager.deleteCartById(Id);
    }
    @PostMapping
    public void addProductToCart(@RequestBody Cart cart){
          this.cartManager.addProductToCart(cart);
    }

}
