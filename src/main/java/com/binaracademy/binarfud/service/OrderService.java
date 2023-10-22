package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.dto.request.OrderRequest;
import com.binaracademy.binarfud.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    byte[] makeOrder(OrderRequest orderRequest);
    Page<Order> getAllOrderWithPagination(Pageable pageable);
}
