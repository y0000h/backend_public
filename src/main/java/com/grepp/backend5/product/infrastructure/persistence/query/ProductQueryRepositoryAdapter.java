package com.grepp.backend5.product.infrastructure.persistence.query;

import com.grepp.backend5.product.domain.model.Product;
import com.grepp.backend5.product.domain.repository.query.ProductQueryRepository;
import com.grepp.backend5.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductQueryRepositoryAdapter implements ProductQueryRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductQueryRepositoryAdapter(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return productJpaRepository.findById(productId);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }
}
