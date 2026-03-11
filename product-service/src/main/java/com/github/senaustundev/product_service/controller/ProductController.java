package com.github.senaustundev.product_service.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.senaustundev.product_service.dto.ProductRequest;
import com.github.senaustundev.product_service.dto.ProductResponse;
import com.github.senaustundev.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        URI location = URI.create("/api/products/" + response.id());
        return ResponseEntity.created(location).body(response);
        // same as above
        // return
        // ResponseEntity.status(HttpStatus.CREATED).location(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

}
