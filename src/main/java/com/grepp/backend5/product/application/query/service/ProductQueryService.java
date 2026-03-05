package com.grepp.backend5.product.application.query.service;

import com.grepp.backend5.product.application.exception.ProductNotFoundException;
import com.grepp.backend5.product.application.query.usecase.ProductQueryUseCase;
import com.grepp.backend5.product.domain.model.Product;
import com.grepp.backend5.product.domain.repository.query.ProductQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductQueryService implements ProductQueryUseCase {

    private final ProductQueryRepository productQueryRepository;

    public ProductQueryService(ProductQueryRepository productQueryRepository) {
        this.productQueryRepository = productQueryRepository;
    }

    @Override
    public Product getById(UUID productId) {
        return productQueryRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public List<Product> getAll() {
        return productQueryRepository.findAll();
    }
}
