package com.grepp.backend5.product.infrastructure.acl.client;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class StubExternalSellerClient implements ExternalSellerClient {

    @Override
    public Optional<ExternalSellerPayload> findSeller(UUID sellerId) {
        // Local stub for ACL integration; replace with real HTTP/RPC adapter later.
        return Optional.of(new ExternalSellerPayload(sellerId.toString(), "ACTIVE"));
    }
}
