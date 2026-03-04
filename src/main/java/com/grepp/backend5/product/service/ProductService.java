package com.grepp.backend5.product.service;

import com.grepp.backend5.product.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product create(Product request);

    Product getById(UUID productId);

    List<Product> getAll();

    Product update(UUID productId, Product request);

    void delete(UUID productId);
}
