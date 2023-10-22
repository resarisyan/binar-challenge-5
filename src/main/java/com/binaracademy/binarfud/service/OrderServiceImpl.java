package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.dto.request.OrderRequest;
import com.binaracademy.binarfud.dto.response.JasperResponse;
import com.binaracademy.binarfud.entity.Cart;
import com.binaracademy.binarfud.entity.Order;
import com.binaracademy.binarfud.entity.OrderDetail;
import com.binaracademy.binarfud.entity.User;
import com.binaracademy.binarfud.exception.DataNotFoundException;
import com.binaracademy.binarfud.exception.ServiceBusinessException;
import com.binaracademy.binarfud.repository.CartRepository;
import com.binaracademy.binarfud.repository.OrderDetailRepository;
import com.binaracademy.binarfud.repository.OrderRepository;
import com.binaracademy.binarfud.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.util.*;


@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private OrderDetailRepository orderDetailRepository;
    private UserRepository userRepository;

    @Override
    public byte[] makeOrder(OrderRequest orderRequest) {
        try{
            User user = userRepository.findFirstByUsername(orderRequest.getUsername()).orElseThrow(() -> new DataNotFoundException("User not found"));
            List<Cart> carts = cartRepository.findByUser(user);
            if (carts.isEmpty()) {
                throw new ServiceBusinessException("Cart is empty");
            }
            Order order = Order.builder()
                    .user(user)
                    .completed(false)
                    .destinationAddress(orderRequest.getDestinationAddress())
                    .note(orderRequest.getNote())
                    .orderTime(new Date())
                    .build();
            Order newOrder = orderRepository.save(order);
            carts.forEach(cart -> {
                OrderDetail orderDetail = OrderDetail.builder()
                        .order(newOrder)
                        .product(cart.getProduct())
                        .quantity(cart.getQuantity())
                        .totalPrice(cart.getTotalPrice())
                        .build();
                orderDetailRepository.save(orderDetail);
            });
            cartRepository.deleteAll(carts);
            Order userOrder = orderRepository.findById(newOrder.getId()).orElseThrow(() -> new DataNotFoundException("Order not found"));
            Double orderTotalPrice = newOrder.getOrderDetails().stream().mapToDouble(OrderDetail::getTotalPrice).sum();
            List<JasperResponse> jasperResponses = userOrder.getOrderDetails().stream().map(orderDetail1 -> JasperResponse.builder()
                    .productName(orderDetail1.getProduct().getProductName())
                    .quantity(orderDetail1.getQuantity())
                    .totalPrice(orderDetail1.getTotalPrice())
                    .build()).toList();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("username", userOrder.getUser().getUsername());
            parameters.put("orderTime", userOrder.getOrderTime());
            parameters.put("destinationAddress", userOrder.getDestinationAddress());
            parameters.put("note", userOrder.getNote());
            parameters.put("orderTotalPrice", orderTotalPrice);

            JasperReport jasperReport = JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:invoice.jrxml").getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jasperResponses);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            log.error("Failed to make order");
            throw new ServiceBusinessException("Failed to make order");
        }
    }

    @Override
    public Page<Order> getAllOrderWithPagination(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

}
