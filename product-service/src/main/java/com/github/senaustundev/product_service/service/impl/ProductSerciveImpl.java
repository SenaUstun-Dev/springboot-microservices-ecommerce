package com.github.senaustundev.product_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.senaustundev.product_service.dto.ProductRequest;
import com.github.senaustundev.product_service.dto.ProductResponse;
import com.github.senaustundev.product_service.model.Product;
import com.github.senaustundev.product_service.repository.ProductRepository;
import com.github.senaustundev.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSerciveImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created with id {}", product.getId());
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("Fetched {} products", products.size());
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()))
                .toList();
    }
}
