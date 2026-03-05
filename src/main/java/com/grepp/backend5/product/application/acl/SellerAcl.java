package com.grepp.backend5.product.application.acl;

import java.util.UUID;

public interface SellerAcl {

    SellerIdentity loadActiveSeller(UUID sellerId);
}
