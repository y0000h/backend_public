package com.grepp.backend5.product.application.port.in;

import com.grepp.backend5.product.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductUseCase {

    Product create(Product request);

    Product getById(UUID productId);

    List<Product> getAll();

    Product update(UUID productId, Product request);

    void delete(UUID productId);
}
