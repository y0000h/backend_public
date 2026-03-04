package com.grepp.backend5.product.infrastructure.persistence;

import com.grepp.backend5.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {
}
