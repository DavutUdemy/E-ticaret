package com.hazir.Hazirlaniyor.dataAccess.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartDao extends JpaRepository<Cart,Long> {
    @Query("SELECT s FROM Cart s WHERE s.email=?1")
    List<Cart> findCartByEmail(String email);
    @Transactional
    @Modifying
    @Query("DELETE FROM Cart u where u.email = ?1")
    int deleteByemail(String email);
    //JPA




}
