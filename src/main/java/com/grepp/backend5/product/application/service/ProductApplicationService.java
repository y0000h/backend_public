package com.grepp.backend5.product.application.service;

import com.grepp.backend5.product.application.exception.ProductNotFoundException;
import com.grepp.backend5.product.application.input.CreateProductInput;
import com.grepp.backend5.product.application.input.UpdateProductInput;
import com.grepp.backend5.product.application.usecase.ProductUseCase;
import com.grepp.backend5.product.domain.model.Product;
import com.grepp.backend5.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductApplicationService implements ProductUseCase {

    private final ProductRepository productRepository;

    public ProductApplicationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product create(CreateProductInput input) {
        Product product = Product.create(
                input.sellerId(),
                input.name(),
                input.description(),
                input.price(),
                input.stock(),
                input.status(),
                input.actorId()
        );
        return productRepository.save(product);
    }

    @Override
    public Product getById(UUID productId) {
        return findByIdOrThrow(productId);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product update(UUID productId, UpdateProductInput input) {
        Product product = findByIdOrThrow(productId);
        product.update(
                input.name(),
                input.description(),
                input.price(),
                input.stock(),
                input.status(),
                input.actorId()
        );
        return product;
    }

    @Override
    @Transactional
    public void delete(UUID productId) {
        Product product = findByIdOrThrow(productId);
        productRepository.delete(product);
    }

    private Product findByIdOrThrow(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
