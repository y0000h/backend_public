package com.grepp.backend5.product.adapter.out.persistence;

import com.grepp.backend5.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {
}
