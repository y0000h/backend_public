package com.grepp.backend5.product.service;

import com.grepp.backend5.product.domain.Product;
import com.grepp.backend5.product.dto.ProductCreateRequest;
import com.grepp.backend5.product.dto.ProductUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product create(ProductCreateRequest request);

    Product getById(UUID productId);

    List<Product> getAll();

    Product update(UUID productId, ProductUpdateRequest request);

    void delete(UUID productId);
}
