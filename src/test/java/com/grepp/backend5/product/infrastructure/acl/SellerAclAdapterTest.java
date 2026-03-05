package com.grepp.backend5.product.infrastructure.acl;

import com.grepp.backend5.product.application.acl.SellerIdentity;
import com.grepp.backend5.product.application.exception.InactiveSellerException;
import com.grepp.backend5.product.application.exception.SellerNotFoundException;
import com.grepp.backend5.product.infrastructure.acl.client.ExternalSellerClient;
import com.grepp.backend5.product.infrastructure.acl.client.ExternalSellerPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerAclAdapterTest {

    @Mock
    private ExternalSellerClient externalSellerClient;

    @InjectMocks
    private SellerAclAdapter sellerAclAdapter;

    @Test
    void loadActiveSellerReturnsIdentity() {
        UUID sellerId = UUID.randomUUID();
        when(externalSellerClient.findSeller(sellerId))
                .thenReturn(Optional.of(new ExternalSellerPayload(sellerId.toString(), "ACTIVE")));

        SellerIdentity identity = sellerAclAdapter.loadActiveSeller(sellerId);

        assertThat(identity.id()).isEqualTo(sellerId);
    }

    @Test
    void loadActiveSellerThrowsWhenSellerMissing() {
        UUID sellerId = UUID.randomUUID();
        when(externalSellerClient.findSeller(sellerId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sellerAclAdapter.loadActiveSeller(sellerId))
                .isInstanceOf(SellerNotFoundException.class)
                .hasMessageContaining(sellerId.toString());
    }

    @Test
    void loadActiveSellerThrowsWhenSellerInactive() {
        UUID sellerId = UUID.randomUUID();
        when(externalSellerClient.findSeller(sellerId))
                .thenReturn(Optional.of(new ExternalSellerPayload(sellerId.toString(), "INACTIVE")));

        assertThatThrownBy(() -> sellerAclAdapter.loadActiveSeller(sellerId))
                .isInstanceOf(InactiveSellerException.class)
                .hasMessageContaining(sellerId.toString());
    }
}
