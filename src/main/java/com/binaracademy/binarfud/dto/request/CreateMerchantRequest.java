package com.binaracademy.binarfud.dto.request;

import com.binaracademy.binarfud.validation.FieldExistence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMerchantRequest {
    @NotBlank
    @FieldExistence(tableName = "merchant", fieldName = "merchant_name", shouldExist = false, message = "Merchant name already exists")
    private String merchantName;

    @NotBlank
    private String merchantLocation;

    @NotNull
    private Boolean open;
}
