package com.hazir.Hazirlaniyor.business.concretes;

import com.hazir.Hazirlaniyor.business.abstracts.CartService;
import com.hazir.Hazirlaniyor.business.abstracts.Facade;
import com.hazir.Hazirlaniyor.business.abstracts.ProductCheckerDal;
import com.hazir.Hazirlaniyor.core.utillities.BadRequest.BadRequestException;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartManager implements CartService {
    private final CartDao    cartDao;
    private final Facade mFacade;



	@Override
    public List<Cart> getAllCarts() {
        return this.cartDao.findAll();
    }

    @Override
    public List<Cart> findCartByUserEmail(String email) {
        return this.cartDao.findCartByEmail (email);
    }


    @Override
    public void addProductToCart(Cart cart) {

		    this.mFacade.productCheckerService.existProduct (cart.getProductName());
        this.cartDao.save(cart);
    }

    @Override
    public void deleteCartById(Long Id) {
      this.cartDao.deleteById(Id);
    }


}