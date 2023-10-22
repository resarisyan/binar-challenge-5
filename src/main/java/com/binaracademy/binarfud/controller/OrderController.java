package com.binaracademy.binarfud.controller;

import com.binaracademy.binarfud.dto.request.OrderRequest;
import com.binaracademy.binarfud.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    OrderService orderService;
    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity makeOrder(@RequestBody @Valid OrderRequest orderRequest) {
        byte[] order = orderService.makeOrder(orderRequest);
        return ResponseEntity.ok().body(order);
    }
}
