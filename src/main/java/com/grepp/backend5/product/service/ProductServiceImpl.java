package com.grepp.backend5.product.service;

import com.grepp.backend5.product.domain.Product;
import com.grepp.backend5.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product create(Product request) {
        Product product = Product.create(
                request.getSellerId(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                request.getStatus(),
                request.getRegId()
        );
        return productRepository.save(product);
    }

    @Override
    public Product getById(UUID productId) {
        Product product = findByIdOrThrow(productId);
        return product;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product update(UUID productId, Product request) {
        Product product = findByIdOrThrow(productId);
        product.update(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                request.getStatus(),
                request.getModifyId()
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
}
