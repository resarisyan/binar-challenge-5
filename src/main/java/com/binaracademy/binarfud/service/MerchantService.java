package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.dto.request.CreateMerchantRequest;
import com.binaracademy.binarfud.dto.request.UpdateStatusMerchantRequest;
import com.binaracademy.binarfud.dto.response.MerchantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MerchantService {
    MerchantResponse addNewMerchant(CreateMerchantRequest merchantRequest);
    void updateStatusMerchant(String merchantName, UpdateStatusMerchantRequest updateStatusMerchantRequest);
    Page<MerchantResponse> getAllMerchantByOpen(Boolean open, Pageable pageable);
}
