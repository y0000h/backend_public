package com.grepp.backend5.product.application.command.usecase;

import com.grepp.backend5.product.domain.model.Product;
import com.grepp.backend5.product.presentation.dto.request.CreateProductRequest;
import com.grepp.backend5.product.presentation.dto.request.UpdateProductRequest;

import java.util.UUID;

public interface ProductCommandUseCase {

    Product create(CreateProductRequest request, UUID actorId);

    Product update(UUID productId, UpdateProductRequest request, UUID actorId);

    void delete(UUID productId);
}
