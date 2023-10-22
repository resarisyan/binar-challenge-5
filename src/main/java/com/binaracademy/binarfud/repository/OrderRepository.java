package com.binaracademy.binarfud.repository;

import com.binaracademy.binarfud.entity.Order;
import com.binaracademy.binarfud.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAll(Pageable pageable);

    //Join table user dan order native query
    Order findFirstByUser(User user);

}
