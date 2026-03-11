package com.github.senaustundev.product_service.service;

import java.util.List;

import com.github.senaustundev.product_service.dto.ProductRequest;
import com.github.senaustundev.product_service.dto.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}