package com.grepp.backend5.product.application.command.service;

import com.grepp.backend5.product.application.command.usecase.ProductCommandUseCase;
import com.grepp.backend5.product.application.exception.ProductNotFoundException;
import com.grepp.backend5.product.domain.model.Product;
import com.grepp.backend5.product.domain.repository.command.ProductCommandRepository;
import com.grepp.backend5.product.presentation.dto.request.CreateProductRequest;
import com.grepp.backend5.product.presentation.dto.request.UpdateProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductCommandService implements ProductCommandUseCase {

    private final ProductCommandRepository productCommandRepository;

    public ProductCommandService(ProductCommandRepository productCommandRepository) {
        this.productCommandRepository = productCommandRepository;
    }

    @Override
    @Transactional
    public Product create(CreateProductRequest request, UUID actorId) {
        Product product = Product.create(
                request.sellerId(),
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.status(),
                actorId
        );
        return productCommandRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(UUID productId, UpdateProductRequest request, UUID actorId) {
        Product product = findByIdOrThrow(productId);
        product.update(
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.status(),
                actorId
        );
        return product;
    }

    @Override
    @Transactional
    public void delete(UUID productId) {
        Product product = findByIdOrThrow(productId);
        productCommandRepository.delete(product);
    }

    private Product findByIdOrThrow(UUID productId) {
        return productCommandRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
