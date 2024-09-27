package com.appsdeveloperblog.estore.productservice.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity, String> {
    ProductLookupEntity findByProductId(String productId);

    ProductLookupEntity findByProductIdOrTitle(String productId, String title);

}
