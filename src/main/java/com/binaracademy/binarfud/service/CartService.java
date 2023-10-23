package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.dto.request.CreateCartRequest;
import com.binaracademy.binarfud.dto.response.CartResponse;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartResponse addNewCart(CreateCartRequest cart);
}
