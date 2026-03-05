package com.grepp.backend5.product.application.command.service;

import com.grepp.backend5.product.application.exception.ProductNotFoundException;
import com.grepp.backend5.product.domain.model.Product;
import com.grepp.backend5.product.domain.repository.command.ProductCommandRepository;
import com.grepp.backend5.product.presentation.dto.request.CreateProductRequest;
import com.grepp.backend5.product.presentation.dto.request.UpdateProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCommandServiceTest {

    @Mock
    private ProductCommandRepository productCommandRepository;

    @InjectMocks
    private ProductCommandService productCommandService;

    @Test
    void createSetsActorIdToRegIdAndModifyId() {
        UUID actorId = UUID.randomUUID();

        CreateProductRequest request = new CreateProductRequest(
                UUID.randomUUID(),
                "Macbook Pro 14",
                "M3 chip",
                new BigDecimal("2590000.00"),
                10,
                "ACTIVE"
        );

        when(productCommandRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product created = productCommandService.create(request, actorId);

        assertThat(created.getRegId()).isEqualTo(actorId);
        assertThat(created.getModifyId()).isEqualTo(actorId);
        verify(productCommandRepository).save(any(Product.class));
    }

    @Test
    void updateChangesProductAndModifierId() {
        UUID productId = UUID.randomUUID();
        UUID actorId = UUID.randomUUID();

        Product existing = Product.create(
                UUID.randomUUID(),
                "Old Product",
                "Old",
                new BigDecimal("100.00"),
                1,
                "ACTIVE",
                UUID.randomUUID()
        );
        existing.setId(productId);

        UpdateProductRequest request = new UpdateProductRequest(
                "New Product",
                "New",
                new BigDecimal("300.00"),
                5,
                "ACTIVE"
        );

        when(productCommandRepository.findById(productId)).thenReturn(Optional.of(existing));

        Product updated = productCommandService.update(productId, request, actorId);

        assertThat(updated.getName()).isEqualTo("New Product");
        assertThat(updated.getDescription()).isEqualTo("New");
        assertThat(updated.getPrice()).isEqualByComparingTo("300.00");
        assertThat(updated.getStock()).isEqualTo(5);
        assertThat(updated.getModifyId()).isEqualTo(actorId);
    }

    @Test
    void deleteRemovesProductWhenExists() {
        UUID productId = UUID.randomUUID();
        Product existing = Product.create(
                UUID.randomUUID(),
                "Product",
                null,
                new BigDecimal("100.00"),
                1,
                "ACTIVE",
                UUID.randomUUID()
        );
        existing.setId(productId);

        when(productCommandRepository.findById(productId)).thenReturn(Optional.of(existing));

        productCommandService.delete(productId);

        verify(productCommandRepository).delete(existing);
    }

    @Test
    void updateThrowsWhenProductDoesNotExist() {
        UUID productId = UUID.randomUUID();
        UUID actorId = UUID.randomUUID();
        UpdateProductRequest request = new UpdateProductRequest(
                "New Product",
                "New",
                new BigDecimal("300.00"),
                5,
                "ACTIVE"
        );

        when(productCommandRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productCommandService.update(productId, request, actorId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(productId.toString());
    }
}
