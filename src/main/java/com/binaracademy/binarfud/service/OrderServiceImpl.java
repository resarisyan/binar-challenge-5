package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.dto.request.OrderRequest;
import com.binaracademy.binarfud.dto.response.*;
import com.binaracademy.binarfud.entity.*;
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
            List<OrderDetail> orderDetails = new ArrayList<>();
            carts.forEach(cart -> {
                OrderDetail orderDetail = OrderDetail.builder()
                        .order(newOrder)
                        .product(cart.getProduct())
                        .quantity(cart.getQuantity())
                        .totalPrice(cart.getTotalPrice())
                        .build();
                OrderDetail newOrderDetail =  orderDetailRepository.save(orderDetail);
                orderDetails.add(newOrderDetail);
            });
            order.setOrderDetails(orderDetails);
            Double orderTotalPrice = order.getOrderDetails().stream().mapToDouble(OrderDetail::getTotalPrice).sum();
            List<JasperResponse> jasperResponses = order.getOrderDetails().stream().map(orderDetail1 -> JasperResponse.builder()
                    .productName(orderDetail1.getProduct().getProductName())
                    .quantity(orderDetail1.getQuantity())
                    .totalPrice(orderDetail1.getTotalPrice())
                    .price(orderDetail1.getProduct().getPrice())
                    .build()).toList();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("username", order.getUser().getUsername());
            parameters.put("orderTime", order.getOrderTime().toString());
            parameters.put("destinationAddress", order.getDestinationAddress());
            parameters.put("note", order.getNote());
            parameters.put("orderTotalPrice", orderTotalPrice);

            JasperReport jasperReport = JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:invoice.jrxml").getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jasperResponses);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to make order");
            throw new ServiceBusinessException(e.getMessage());
        }
    }

    @Override
    public Page<OrderResponse> getAllOrderWithPagination(String username, Pageable pageable) {
        try {
            log.info("Getting all order");
            User user = userRepository.findFirstByUsername(username).orElseThrow(() -> new DataNotFoundException("User not found"));
            Page<Order> orderPage = Optional.of(orderRepository.findAllByUser(user, pageable))
                    .filter(Page::hasContent)
                    .orElseThrow(() -> new DataNotFoundException("No order found"));
            return  orderPage.map(order -> OrderResponse.builder()
                    .orderTime(order.getOrderTime())
                    .destinationAddress(order.getDestinationAddress())
                    .note(order.getNote())
                    .completed(order.getCompleted())
                    .orderDetails(
                            order.getOrderDetails().stream().map(orderDetail -> OrderDetailResponse.builder()
                                            .product(
                                                    ProductResponse.builder()
                                                            .productName(orderDetail.getProduct().getProductName())
                                                            .price(orderDetail.getProduct().getPrice())
                                                            .build()
                                            )
                                    .quantity(orderDetail.getQuantity())
                                    .totalPrice(orderDetail.getTotalPrice())
                                    .build()).toList()
                    )
                    .build());
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to get all order");
            throw new ServiceBusinessException("Failed to get all order");
        }
    }

}
