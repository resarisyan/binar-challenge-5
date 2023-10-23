package com.binaracademy.binarfud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JasperResponse {
    String productName;
    Double price;
    Integer quantity;
    Double totalPrice;
}
