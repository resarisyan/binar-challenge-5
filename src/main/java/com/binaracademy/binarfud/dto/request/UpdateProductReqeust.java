package com.binaracademy.binarfud.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductReqeust {
    @NotBlank
    private String productName;
    @NotNull
    @Max(1000000)
    private Double price;
    @NotBlank
    private String merchantName;
}
