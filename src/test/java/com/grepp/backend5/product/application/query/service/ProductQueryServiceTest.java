package com.grepp.backend5.product.application.query.service;

import com.grepp.backend5.product.application.exception.ProductNotFoundException;
import com.grepp.backend5.product.domain.model.Product;
import com.grepp.backend5.product.domain.repository.query.ProductQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {

    @Mock
    private ProductQueryRepository productQueryRepository;

    @InjectMocks
    private ProductQueryService productQueryService;

    @Test
    void getByIdReturnsProduct() {
        UUID productId = UUID.randomUUID();
        Product product = Product.create(
                UUID.randomUUID(),
                "Product",
                null,
                new BigDecimal("100.00"),
                1,
                "ACTIVE",
                UUID.randomUUID()
        );
        product.setId(productId);

        when(productQueryRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productQueryService.getById(productId);

        assertThat(result.getId()).isEqualTo(productId);
    }

    @Test
    void getByIdThrowsWhenProductDoesNotExist() {
        UUID productId = UUID.randomUUID();
        when(productQueryRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productQueryService.getById(productId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(productId.toString());
    }

    @Test
    void getAllReturnsProducts() {
        Product p1 = Product.create(
                UUID.randomUUID(),
                "Product1",
                null,
                new BigDecimal("100.00"),
                1,
                "ACTIVE",
                UUID.randomUUID()
        );
        Product p2 = Product.create(
                UUID.randomUUID(),
                "Product2",
                null,
                new BigDecimal("200.00"),
                2,
                "ACTIVE",
                UUID.randomUUID()
        );

        when(productQueryRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Product> result = productQueryService.getAll();

        assertThat(result).hasSize(2);
        verify(productQueryRepository).findAll();
    }
}
