package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.dto.request.CreateProductRequest;
import com.binaracademy.binarfud.dto.request.UpdateProductReqeust;
import com.binaracademy.binarfud.dto.response.ProductResponse;
import com.binaracademy.binarfud.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductResponse addNewProduct(CreateProductRequest createProductRequest);
    void updateProduct(String productName, UpdateProductReqeust updateProductReqeust);
    void deleteProduct(String productName);
    ProductResponse getProductDetail(String selectedProductName);
    Page<ProductResponse> getProductsWithPagination(Pageable pageable);
}
