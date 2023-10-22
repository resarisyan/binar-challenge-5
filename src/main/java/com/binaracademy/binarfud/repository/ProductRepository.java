package com.binaracademy.binarfud.repository;

import com.binaracademy.binarfud.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    Optional<Product> findByProductName(String productName);

    Page<Product> findAll(Pageable pageable);
}
