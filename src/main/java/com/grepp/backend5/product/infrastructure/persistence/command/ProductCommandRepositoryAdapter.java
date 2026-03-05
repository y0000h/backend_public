package com.grepp.backend5.product.infrastructure.persistence.command;

import com.grepp.backend5.product.domain.model.Product;
import com.grepp.backend5.product.domain.repository.command.ProductCommandRepository;
import com.grepp.backend5.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductCommandRepositoryAdapter implements ProductCommandRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductCommandRepositoryAdapter(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return productJpaRepository.findById(productId);
    }

    @Override
    public void delete(Product product) {
        productJpaRepository.delete(product);
    }
}
