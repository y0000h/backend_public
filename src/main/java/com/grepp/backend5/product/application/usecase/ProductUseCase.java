package com.grepp.backend5.product.application.usecase;

import com.grepp.backend5.product.application.input.CreateProductInput;
import com.grepp.backend5.product.application.input.UpdateProductInput;
import com.grepp.backend5.product.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductUseCase {

    Product create(CreateProductInput input);

    Product getById(UUID productId);

    List<Product> getAll();

    Product update(UUID productId, UpdateProductInput input);

    void delete(UUID productId);
}
