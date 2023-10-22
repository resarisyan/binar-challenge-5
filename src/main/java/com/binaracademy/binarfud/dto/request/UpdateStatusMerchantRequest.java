package com.binaracademy.binarfud.dto.request;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusMerchantRequest {
    @NotNull
    private Boolean open;
}
