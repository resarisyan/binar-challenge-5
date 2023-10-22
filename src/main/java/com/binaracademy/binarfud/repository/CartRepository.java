package com.binaracademy.binarfud.repository;

import com.binaracademy.binarfud.entity.Cart;
import com.binaracademy.binarfud.entity.Product;
import com.binaracademy.binarfud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findByProductAndUser(Product product, User user);
    List<Cart> findByUser(User user);
    void deleteByUser(User user);
}
