package com.grepp.backend5.product.application.query.usecase;

import com.grepp.backend5.product.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductQueryUseCase {

    Product getById(UUID productId);

    List<Product> getAll();
}
